package org.dgfoundation.amp.mondrian;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.dgfoundation.amp.Util;
import org.dgfoundation.amp.ar.AmpARFilter;
import org.dgfoundation.amp.algo.BooleanWrapper;
import org.dgfoundation.amp.ar.viewfetcher.SQLUtils;
import org.dgfoundation.amp.mondrian.currencies.CalculateExchangeRatesEtlJob;
import org.dgfoundation.amp.mondrian.jobs.Fingerprint;
import org.dgfoundation.amp.mondrian.monet.MonetConnection;
import org.dgfoundation.amp.onepager.translation.TranslatorUtil;
import org.digijava.kernel.persistence.PersistenceManager;
import org.digijava.kernel.util.SiteUtils;
import org.digijava.module.aim.dbentity.AmpFiscalCalendar;
import org.digijava.module.aim.helper.fiscalcalendar.ICalendarWorker;
import org.digijava.module.aim.util.DbUtil;
import org.digijava.module.aim.util.time.StopWatch;

import clover.com.google.common.base.Joiner;

import com.google.common.base.Predicate;

import static org.dgfoundation.amp.mondrian.MondrianTablesRepository.FACT_TABLE;


/**
 * the entry point for doing ETL for Mondrian in AMP
 * Please see https://wiki.dgfoundation.org/display/AMPDOC/AMP+2.10+ETL+process
 * @author Dolghier Constantin
 *
 */
public class MondrianETL {
		
	public final static String MONDRIAN_EXCHANGE_RATES_TABLE = "mondrian_exchange_rates";
	public final static String MONDRIAN_DATE_TABLE = "mondrian_dates";
	
	/**
	 * the dummy id to insert into the Cartesian product calculated by ETL.
	 * <strong>Do not change this unless you want to face Constantin's wrath</strong>
	 */
	public final static Long MONDRIAN_DUMMY_ID_FOR_ETL = 999999999l;
	
	/**
	 * the number to add to pledge ids in tables joined with activity tables
	 */
	public final static Long PLEDGE_ID_ADDER = 800000000l;
	
	//public final static ReadWriteLockHolder MONDRIAN_LOCK = new ReadWriteLockHolder("Mondrian reports/etl lock");
	
	/**
	 * ETL lock - used for serializing ETL runs
	 */
	private final static Object ETL_LOCK = new Object();
	
	/**
	 * FULL ETL lock, shared with reports - used to prevent a report from running while a FULL ETL is performed and the database is in unusable shape
	 */
	public final static ReaderWriterLock FULL_ETL_LOCK = new ReaderWriterLock();
		
	/**
	 * the postgres connection
	 */
	protected final java.sql.Connection conn;
	
	protected final MonetConnection monetConn;
	
	protected EtlConfiguration etlConfig;
	
	/**
	 * only access it in steps BEFORE "etlConfig" is available. Superseded by etlConfig.fullEtl once the instance has been constructed
	 */
	protected boolean forceFullEtl;
	
	protected final static Fingerprint ETL_TIME_FINGERPRINT = new Fingerprint("etl_event_id", new ArrayList<String>(), "-1");
	
	/**
	 * fingerprint for triggering FULL ETL once a currency has been (re)(un)defined
	 */
	protected final static Fingerprint CURRENCIES_FINGERPRINT = new Fingerprint("amp_currency", Arrays.asList(Fingerprint.buildTableHashingQuery("amp_currency", "amp_currency_id")));
	
	/**
	 * fingerprint for triggering FULL ETL once a locale has been added/removed
	 */
	protected final static Fingerprint LOCALES_FINGERPRINT = new Fingerprint("locales", Arrays.asList("select code from DG_SITE_TRANS_LANG_MAP where site_id = 3 order by code"));
	
	/**
	 * fingerprint for triggering FULL ETL once a calendar has been added/removed
	 */
	protected final static Fingerprint CALENDARS_FINGERPRINT = new Fingerprint("calendars", Arrays.asList(Fingerprint.buildTableHashingQuery("amp_fiscal_calendar", "amp_fiscal_cal_id")));
	
	protected static Logger logger = Logger.getLogger(MondrianETL.class);
	
	protected LinkedHashSet<String> locales;
	
	/**
	 * the amp_etl_changelog::event_id of the currently-running ETL process (e.g. the end of the EEW)
	 */
	protected long currentEtlEventId;
	
	/**
	 * amp_etl_log::etl_id of the currently-running ETL
	 */
	protected long currentEtlId;
	
	/**
	 * the amp_etl_changelog::event_id when the previous ETL was started (e.g. the beginning of the EEW)
	 */
	protected long previousEtlEventId;
		
	// the following fields are local copies of the would-be-built EtlResult instance
	private boolean cacheInvalidated = false;
	private long nrAffectedDates;
		
	private List<String> reasonsForFull = new ArrayList<>();
	
	/**
	 * constructs an instance, does an initial assessment (scans for IDs). The {@link #execute()} method should be run as closely to the constructor as possible (to avoid race conditions)
	 * @param conn
	 * @param activities
	 * @param activitiesToRemove
	 */
	public MondrianETL(java.sql.Connection postgresConn, MonetConnection monetConn, boolean forceFullEtl) {
		logger.warn("Mondrian ETL started");
		
		this.conn = postgresConn;
		this.monetConn = monetConn;
		this.forceFullEtl = forceFullEtl | !monetConn.tableExists(Fingerprint.FINGERPRINT_TABLE);
		this.locales = new LinkedHashSet<>(TranslatorUtil.getLocaleCache(SiteUtils.getDefaultSite()));
	}
	
	/**
	 * scans the EEW and analyzes fingerprints in order to decide the kind of ETL to do
	 * @param fullEtlJobs
	 * @return
	 * @throws SQLException
	 */
	protected EtlConfiguration calculateEtlConfiguration(List<ExceptionRunnable<? extends Exception>> fullEtlJobs) throws SQLException{
		boolean etlFromScratch = shouldMakeFullEtl(fullEtlJobs);
		Set<Long> pledges = etlFromScratch ? 
				new HashSet<>(SQLUtils.fetchLongs(conn, "SELECT DISTINCT(id) FROM amp_funding_pledges")) :
					collectEtlEntities("pledge");
		Set<Long> components = collectEtlEntities("component");
		Set<Long> activities = etlFromScratch ? getAllValidatedAndLatestIds() : collectEtlEntities("activity");
		int steps = 1;
		while (iterateEtlEntities(activities, pledges, components)) {
			steps ++;
		}
		pledges.remove(0l);
		activities.remove(0l);
		components.remove(0l);
		if (etlFromScratch)
			activities.add(999999999l);
		logger.info(String.format("needed %d iterations to collect all the ETLable entities: %d activities, %d pledges, %d components", steps, activities.size(), pledges.size(), components.size()));
		
		//Set<Long> activityIds = calculateAffectedActivities(etlFromScratch, pledgeIds);
		Set<Long> dateCodes = etlFromScratch ? null : collectEtlEntities("exchange_rate");
		return new EtlConfiguration(activities, pledges, dateCodes, etlFromScratch);
		//this.pledgesToRedo = new HashSet<>();
		//if (pledgesToRedo != null)
		//	this.pledgesToRedo.addAll(pledgesToRedo);
	}
	
	/**
	 * collect ETL entities from the EEW
	 * @param entityType
	 * @return
	 */
	private Set<Long> collectEtlEntities(String entityType) {
		String query = String.format("SELECT DISTINCT(entity_id) FROM amp_etl_changelog WHERE (event_id > " + previousEtlEventId + ") AND (event_id < " + currentEtlEventId + ") AND (entity_name='%s')", entityType);
		return new TreeSet<>(SQLUtils.fetchLongs(conn, query));
	}
	
	/**
	 * does an iteration of BFS on (activity, pledge, component) IDs
	 * @param activities
	 * @param pledges
	 * @param components
	 * @return true iff anything changed
	 */
	private boolean iterateEtlEntities(Set<Long> activities, Set<Long> pledges, Set<Long> components) {
		int oldNrActivies = activities.size();
		int oldNrPledges = pledges.size();
		int oldNrComponents = components.size();

		String componentIdCondition = "amp_component_id IN (" + Util.toCSStringForIN(components) + ")";
		activities.addAll(SQLUtils.fetchLongs(conn, "SELECT DISTINCT(activity_id) FROM amp_component_funding WHERE " + componentIdCondition));
		activities.addAll(SQLUtils.fetchLongs(conn, "SELECT DISTINCT(amp_activity_id) FROM amp_activity_components WHERE " + componentIdCondition));

		String pledgeIdsCondition = "afd.pledge_id IN (" + Util.toCSStringForIN(pledges) + ")";
		activities.addAll(SQLUtils.fetchLongs(conn, "select distinct(af.amp_activity_id) from amp_funding_detail afd JOIN amp_funding af on afd.amp_funding_id = af.amp_funding_id WHERE " + pledgeIdsCondition));
		pledges.addAll(SQLUtils.fetchLongs(conn, "SELECT distinct(pledge_id) FROM amp_funding_detail afd JOIN amp_funding af ON afd.amp_funding_id = af.amp_funding_id WHERE af.amp_activity_id IN (" + Util.toCSStringForIN(activities) + ")"));

		
		components.addAll(SQLUtils.fetchLongs(conn, "SELECT DISTINCT(amp_component_id) FROM amp_component_funding WHERE activity_id IN (" + Util.toCSStringForIN(activities) + ")"));
		components.addAll(SQLUtils.fetchLongs(conn, "SELECT DISTINCT(amp_component_id) FROM amp_activity_components WHERE amp_activity_id IN (" + Util.toCSStringForIN(activities) + ")"));
		
		return oldNrActivies != activities.size() || oldNrPledges != pledges.size() || oldNrComponents != components.size();
	}	
	
	protected Set<Long> getAllValidatedAndLatestIds() {
		Set<Long> latestIds = new TreeSet<Long>(SQLUtils.<Long>fetchAsList(conn, "SELECT amp_activity_id FROM amp_activity", 1));
		Set<Long> latestValidatedIds = new TreeSet<Long>(SQLUtils.<Long>fetchAsList(conn,
				"SELECT aag.amp_activity_group_id, max(aav.amp_activity_id) FROM amp_activity_version aav, amp_activity_group aag WHERE aag.amp_activity_group_id = aav.amp_activity_group_id AND (aav.deleted IS NULL OR aav.deleted = false) AND aav.approval_status IN (" + Util.toCSString(AmpARFilter.validatedActivityStatus) + ") GROUP BY aag.amp_activity_group_id", 2));
		
//		logger.warn("last activity version ids: " + latestIds.toString());
//		logger.warn("last validated activity version ids: " + latestIds.toString());
		Set<Long> res = new TreeSet<Long>();
		res.addAll(latestIds);
		res.addAll(latestValidatedIds);
		return res;
	}
	
	protected final static String MONDRIAN_ETL = "Mondrian-etl";
	
	/**
	 * cleanup for ease-of-debugging - triggered at FULL ETL in order to enforce clean state
	 * cleans up: 
	 * 	1. all Mondrian tables Monet-side and PSQL-side
	 * 	2. all tables with names starting with "etl_" Monet-side and PSQL-side
	 */
	protected void cleanupTables() {
		logger.warn("Full etl requested, cleaning up preexisting tables");
		for(MondrianTableDescription mtd:MondrianTableDescription.ALL_TABLES) {
			List<String> suffixes = new ArrayList<String>(Arrays.asList(""));
			for(String locale:locales)
				suffixes.add("_" + locale);
			for(String suffix:suffixes) {
				String t = mtd.tableName + suffix;
				//logger.info("Full ETL: dropping table " + t);
				monetConn.dropTable(t);
				SQLUtils.executeQuery(conn, "DROP TABLE IF EXISTS " + t);
			}
		}
		Set<String> etlTablesMonet = monetConn.getTablesWithNameMatching("etl_");
		logger.info("Full ETL: dropping monet tables " + etlTablesMonet);
		for(String t:etlTablesMonet) 
		 if (!t.equals(Fingerprint.FINGERPRINT_TABLE)){
			 //logger.info("Full ETL: dropping monet table " + t);
			 monetConn.dropTable(t);
		}
		
		Set<String> etlTablesPostgres = SQLUtils.getTablesWithNameMatching(conn, "etl_");
		logger.info("Full ETL: dropping pg tables " + etlTablesPostgres);
		for(String t:etlTablesPostgres) {
			//logger.info("Full ETL: dropping pg table " + t);
			SQLUtils.executeQuery(conn, "DROP TABLE IF EXISTS " + t);
		}
		monetConn.dropTable(FACT_TABLE.tableName);
		SQLUtils.executeQuery(conn, "DROP TABLE IF EXISTS " + FACT_TABLE.tableName);
	}
	
	/**
	 * the ETL main function
	 */
private EtlResult execute() throws Exception {
	boolean fullEtlLockAcquired = false;
	try {
		long start = System.currentTimeMillis();

		StopWatch.reset(MONDRIAN_ETL);
		StopWatch.next(MONDRIAN_ETL, true, "start");
				
		if (!monetConn.tableExists(Fingerprint.FINGERPRINT_TABLE))
			Fingerprint.redoFingerprintTable(monetConn);
		
		previousEtlEventId = Long.valueOf(
				monetConn.tableExists(Fingerprint.FINGERPRINT_TABLE) ? 
					ETL_TIME_FINGERPRINT.readOrReturnDefaultFingerprint(monetConn) : ETL_TIME_FINGERPRINT.defaultValue);
		
		List<ExceptionRunnable<? extends Exception>> fullEtlJobs = new ArrayList<>(); // will collect all "FULL ETL" pieces of code to run under the FULL ETL lock
				
		// log the ETL start and get its ids
		SQLUtils.executeQuery(conn, "INSERT INTO amp_etl_log (duration) VALUES (null)");
		currentEtlId = SQLUtils.getLong(conn, "SELECT max(etl_id) FROM amp_etl_log");
		SQLUtils.executeQuery(conn, "INSERT INTO amp_etl_changelog(entity_name, entity_id) VALUES ('etl', " + currentEtlId + ")");
		currentEtlEventId = SQLUtils.getLong(conn, "SELECT max(event_id) FROM amp_etl_changelog WHERE entity_name = 'etl'");

		boolean fullEtlRequestedDB = !collectEtlEntities("full_etl_request").isEmpty(); // was full_etl requested?
		if (fullEtlRequestedDB) {
			forceFullEtl |= fullEtlRequestedDB;
			reasonsForFull.add("DB_requested");
		}
				
		if (forceFullEtl) {
			Fingerprint.redoFingerprintTable(monetConn);
			fullEtlJobs.add(new ExceptionRunnable<Exception>(){
				@Override public void run() throws Exception {
					cleanupTables();
				}
			});
		}
												
		etlConfig = calculateEtlConfiguration(fullEtlJobs);
				
		logger.info("running ETL, the configuration is: " + etlConfig.toString());
		boolean redoTrnDimensions = !collectEtlEntities("translation").isEmpty();
				
		boolean workToDo = etlConfig.fullEtl || redoTrnDimensions 
				|| !etlConfig.activityIds.isEmpty() || !etlConfig.pledgeIds.isEmpty() || !etlConfig.dateCodes.isEmpty(); // is there anything to do?
		
		if ((!etlConfig.fullEtl) && (!fullEtlJobs.isEmpty()))
			throw new RuntimeException("jss u r st00p33d");
		
		if (workToDo) {
			if (etlConfig.fullEtl) {
				// doing full ETL
				FULL_ETL_LOCK.writeLock();
				fullEtlLockAcquired = true;
				logger.info("doing full ETL");
				for(ExceptionRunnable<?> job:fullEtlJobs)
					job.run();
				
				for (final MondrianTableDescription mtd:MondrianTablesRepository.MONDRIAN_ACTIVITY_DEPENDENT_DIMENSIONS) {
					boolean tableExists = monetConn.tableExists(mtd.tableName);
					mtd.fingerprint.runIfFingerprintChangedOr(conn, monetConn, !tableExists, stepSkipped, new ExceptionRunnable<SQLException>() {
						@Override public void run() throws SQLException {
							generateStarTable(mtd);
						}
					});
				}
			
				recreateFactTable(); // since fact table manipulation is incremental in nature (even if doing a FULL ETL) -> insure a clean state of it
				generateMondrianDateTable();
			}
					
			if (redoTrnDimensions && !etlConfig.fullEtl) {
				// some trn-translation changed, but not requested FULL ETL -> fully redo the trn-backed dimensions
				FULL_ETL_LOCK.writeLock();
				logger.info("redoing trn-backed dimensions, as some translations have changed and doing just an incremental ETL");
				for(MondrianTableDescription table:MondrianTablesRepository.TRN_BACKED_DIMENSIONS)
					generateStarTable(table);
				FULL_ETL_LOCK.writeUnlock();
			}

			generateActivitiesEntries(); // update/generate all the per-activity tables
		
			new CalculateExchangeRatesEtlJob(null, conn, monetConn, etlConfig).work();
			StopWatch.next(MONDRIAN_ETL, true, "generateExchangeRates");
					
			if (etlConfig.fullEtl) {
				checkMondrianSanity();
				StopWatch.next(MONDRIAN_ETL, true, "checkMondrianSanity");
			}
			cacheInvalidated = etlConfig.fullEtl || workToDo;
					
			nrAffectedDates = etlConfig.dateCodes == null ? -1 : etlConfig.dateCodes.size();
			StopWatch.reset(MONDRIAN_ETL);
							
			logger.error("done generating ETL");
		}
				
		ETL_TIME_FINGERPRINT.serializeFingerprint(monetConn, Long.toString(currentEtlEventId));

		SQLUtils.flush(conn);
		SQLUtils.flush(monetConn.conn);

		long end = System.currentTimeMillis();
		double secs = (end - start) / 1000.0;
		EtlResult res = new EtlResult(currentEtlEventId, secs, cacheInvalidated,  
						etlConfig.activityIds.size() + etlConfig.pledgeIds.size(), nrAffectedDates, reasonsForFull);
		serializeEtLResult(res);
		return res;
	}
	finally {
		if (fullEtlLockAcquired)
			FULL_ETL_LOCK.writeUnlock();
	}
}
			
	protected void serializeEtLResult(EtlResult res) {
		SQLUtils.executeQuery(conn, 
			String.format(Locale.US, "UPDATE amp_etl_log SET duration = %.2f, cache_invalidated=%b, nr_affected_entries=%d, nr_affected_dates=%d, full_etl_reason=%s WHERE etl_id = %d",
				res.duration, res.cacheInvalidated, res.nrAffectedEntities, res.nrAffectedDates,
				SQLUtils.stringifyObject(Joiner.on(';').join(res.fullEtlReasons)),
				currentEtlId));
		SQLUtils.flush(conn);
	}
	
	/**
	 * quite expensive, so only run it in case of full etl (which is expensive anyway)
	 */
	protected void checkMondrianSanity() {
		String query = "SELECT DISTINCT(mft.date_code) FROM mondrian_fact_table mft WHERE NOT EXISTS (SELECT exchange_rate FROM mondrian_exchange_rates mer WHERE mer.day_code = mft.date_code)";
		List<Long> days = SQLUtils.fetchLongs(monetConn.conn, query);
		if (!days.isEmpty()) {
			logger.error("after having run the ETL, some days do not have a corresponding exchange rate entry: " + days.toString());
			throw new RuntimeException("MONDRIAN ETL BUG: some days have missing exchange rate entries and will not exist in the generated reports: " + days);
		}
		
		query = "SELECT DISTINCT(mft.date_code) FROM mondrian_fact_table mft WHERE NOT EXISTS (SELECT full_date FROM " + MONDRIAN_DATE_TABLE + " mdt WHERE mdt.day_code = mft.date_code)";
		days = SQLUtils.fetchLongs(monetConn.conn, query);
		if (!days.isEmpty()) {
			logger.error("after having run the ETL, some days do not have a corresponding DATE entry: " + days.toString());
			throw new RuntimeException("MONDRIAN ETL BUG: some days have missing date entries and will not exist in the generated reports: " + days);
		};		
	}
	
//	protected String buildMftQuery() throws SQLException {
//		ResultSet dayCodes = SQLUtils.rawRunQuery(monetConn.conn, "SELECT DISTINCT(date_code), transaction_date FROM mondrian_fact_table", null);
//		StringBuilder mftQuery = new StringBuilder("(SELECT mft.date_code, mft.transaction_date FROM (values");
//		boolean isFirst = true;
//		while (dayCodes.next()) {
//			if (!isFirst)
//				mftQuery.append(",");
//			mftQuery.append(String.format("(%d, '%s')", dayCodes.getLong(1), dayCodes.getString(2)));
//			isFirst = false;
//		}
//		mftQuery.append(") as mft(date_code, transaction_date)) mft");
//		return mftQuery.toString();
//	}
	
	/**
	 * callback for "hash equals, not redoing part of ETL"
	 */
	protected Predicate<Fingerprint> stepSkipped = new Predicate<Fingerprint>() {
		@Override public boolean apply(Fingerprint obj) {
			//logger.info(String.format("skipping calculating %s, as hash not changed", obj.keyName));
			return true;
		}
	};
	
	/**
	 * makes a snapshot of the views which back Mondrian Dimension Tables which are not linked with specific activities. 
	 * Returns true of any of them changed, e.g. a full ETL is needed
	 * @param fullEtlRequested
	 * @return
	 * @throws SQLException
	 */
	protected boolean shouldMakeFullEtl(final List<ExceptionRunnable<? extends Exception>> fullEtlJobs) throws SQLException {
		final BooleanWrapper res = new BooleanWrapper(forceFullEtl);
		LOCALES_FINGERPRINT.runIfFingerprintChangedOr(conn, monetConn, false, stepSkipped, new ExceptionRunnable<SQLException>() {
			@Override public void run() throws SQLException {
				res.or(true);
				if (LOCALES_FINGERPRINT.hasChangeBeenDetected()) {
					reasonsForFull.add("locales_change");
					logger.error("locales changed, forcing a full ETL");
				}
			}
		});
		if (previousEtlEventId <= 0) {
			res.or(true);
			reasonsForFull.add("previousETL_never");
		}
		
		pumpTableIfChanged(res, fullEtlJobs, "amp_currency", "amp_currency_id");
		pumpTableIfChanged(res, fullEtlJobs, "amp_category_class", "id");
		pumpTableIfChanged(res, fullEtlJobs, "amp_category_value", "id");

		CALENDARS_FINGERPRINT.runIfFingerprintChangedOr(conn, monetConn, false, stepSkipped, new ExceptionRunnable<SQLException>() {
			@Override public void run() throws SQLException {
				res.or(true);
			}
		});
		
		// if any of the dimension tables changed -> redo the whole thing
		for (final MondrianTableDescription mondrianTable:MondrianTablesRepository.MONDRIAN_DIMENSION_TABLES)
			mondrianTable.fingerprint.runIfFingerprintChangedOr(conn, monetConn, res.value, stepSkipped, new ExceptionRunnable<SQLException>() {
				@Override public void run() throws SQLException {
					res.or(true);
					fullEtlJobs.add(new ExceptionRunnable<SQLException>() {
						@Override public void run() throws SQLException {
							generateStarTable(mondrianTable);
						}
						@Override public String toString() {
							return "generating star table " + mondrianTable;
						}
					});
				}});

		return res.value;
	}

	/**
	 * copies a table from PSQL to Monet, in case it changed from the last etl'ed copy
	 * @param needed
	 * @param tableName
	 * @throws SQLException
	 */
	protected void pumpTableIfChanged(final BooleanWrapper needed, final List<ExceptionRunnable<? extends Exception>> fullEtlJobs, final String tableName, final String orderBy) throws SQLException {
		final Fingerprint fingerprint = new Fingerprint("table_" + tableName, Arrays.asList(Fingerprint.buildTableHashingQuery(tableName, orderBy)));
		fingerprint.runIfFingerprintChangedOr(conn, monetConn, needed.value, stepSkipped, new ExceptionRunnable<SQLException>() {
			@Override public void run() throws SQLException {
				needed.or(true);
				fullEtlJobs.add(new ExceptionRunnable<SQLException>() {
					@Override public void run() throws SQLException {
						monetConn.copyTableFromPostgres(conn, tableName);
					}
					@Override public String toString() {
						return "copying table " + tableName + " from PSQL to Monet";
					}
				});
				if (fingerprint.hasChangeBeenDetected())
					reasonsForFull.add("table_" + tableName + "_changed");
			}});
	}
	
	/**
	 * generates the Mondrian Date Table into MonetDB. Since Monet lacks the needed features, the table construction is done in postgres and then pumped into monet
	 * @throws SQLException
	 */
	protected void generateMondrianDateTable() throws SQLException {
		generateStarTableWithQueryInPostgres(MONDRIAN_DATE_TABLE, "date_code", 
			"SELECT to_char(transaction_date, 'J')::integer AS day_code, (CAST (transaction_date as date)) AS full_date, date_part('year'::text, (CAST (transaction_date as date)))::integer AS year_code, date_part('year'::text, (CAST (transaction_date as date)))::text AS year_name, date_part('month'::text, (CAST (transaction_date as date)))::integer AS month_code, to_char((CAST (transaction_date as date)), 'Month'::text) AS month_name, date_part('quarter'::text, (CAST (transaction_date as date)))::integer AS quarter_code, ('Q'::text || date_part('quarter'::text, (CAST (transaction_date as date)))) AS quarter_name " + 
			" FROM generate_series('1970-1-1', '2050-1-1', interval '1 day') transaction_date " + 
			" UNION ALL " + 
			" SELECT 999999999, '9999-1-1', 9999, 'Undefined', 99, 'Undefined', 99, 'Undefined' " + 
			" ORDER BY day_code",
			new ArrayList<String>());
		for (AmpFiscalCalendar calendar:DbUtil.getAllFisCalenders()) {
			generateFiscalCalendarColumns(calendar);
		}
		monetConn.copyTableFromPostgres(conn, MONDRIAN_DATE_TABLE);
	}
	
	protected void generateFiscalCalendarColumns(AmpFiscalCalendar calendar) throws SQLException {
		ICalendarWorker worker = calendar.getworker();
		SQLUtils.executeQuery(conn, "ALTER TABLE " + MONDRIAN_DATE_TABLE + " ADD year_code_" + calendar.getAmpFiscalCalId() + " bigint");
		SQLUtils.executeQuery(conn, "ALTER TABLE " + MONDRIAN_DATE_TABLE + " ADD year_name_" + calendar.getAmpFiscalCalId() + " text");
		SQLUtils.executeQuery(conn, "ALTER TABLE " + MONDRIAN_DATE_TABLE + " ADD month_code_" + calendar.getAmpFiscalCalId() + " bigint");
		SQLUtils.executeQuery(conn, "ALTER TABLE " + MONDRIAN_DATE_TABLE + " ADD month_name_" + calendar.getAmpFiscalCalId() + " text");
		SQLUtils.executeQuery(conn, "ALTER TABLE " + MONDRIAN_DATE_TABLE + " ADD quarter_code_" + calendar.getAmpFiscalCalId() + " bigint");
		SQLUtils.executeQuery(conn, "ALTER TABLE " + MONDRIAN_DATE_TABLE + " ADD quarter_name_" + calendar.getAmpFiscalCalId() + " text");
		ResultSet rs = SQLUtils.rawRunQuery(conn, "SELECT day_code, full_date FROM " + MONDRIAN_DATE_TABLE, null);
		List<List<Object>> wr = new ArrayList<>();
		while (rs.next()) {
			wr.add(buildDateRowForCalendar(rs, worker));
		}
		//'9999-1-1', 9999, 'Undefined', 99, 'Undefined', 99, 'Undefined'
		//wr.add(Arrays.<Object>asList(999999999l, 9999l, "Undefined", "Undefined", 99l, "Undefined"));
		String tableName = "mondrian_dates_temp_calendar_" + calendar.getAmpFiscalCalId();
		SQLUtils.executeQuery(conn, "DROP TABLE IF EXISTS " + tableName);
		SQLUtils.executeQuery(conn, "CREATE TABLE " + tableName + " (dd bigint PRIMARY KEY, year_code bigint, year_name text, month_code bigint, month_name text, quarter_code bigint, quarter_name text)");
		SQLUtils.insert(conn, tableName, null, null,
				Arrays.asList("dd", "year_code", "year_name",  "month_code", "month_name", "quarter_code", "quarter_name"), wr);
		String query = String.format("UPDATE " + MONDRIAN_DATE_TABLE + " AS mdt SET year_code_%d=c.year_code, year_name_%d=c.year_name, month_code_%d=c.month_code, month_name_%d=c.month_name, quarter_code_%d=c.quarter_code, quarter_name_%d=c.quarter_name FROM " + tableName + " AS c WHERE c.dd = mdt.day_code", 
				calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId());
		SQLUtils.executeQuery(conn, query);
		SQLUtils.executeQuery(conn, 
				String.format("UPDATE " + MONDRIAN_DATE_TABLE + " SET year_code_%d=9999, year_name_%d='Undefined', month_code_%d=99, month_name_%d='Undefined', quarter_code_%d=99,quarter_name_%d='Undefined' WHERE day_code=999999999",
						calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(),calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId(), calendar.getAmpFiscalCalId()));
	}
	
	protected List<Object> buildDateRowForCalendar(ResultSet rs, ICalendarWorker worker) {
		try {
			Long key = rs.getLong(1);
			Date date = rs.getDate(2);
			worker.setTime(date);
			List<Object> row = new ArrayList<>();
			row.add(key);
			row.add(worker.getYear());
			row.add(worker.getFiscalYear());
			row.add(worker.getFiscalMonth().getMonthId());
			row.add(worker.getFiscalMonth().toString());
			row.add(worker.getQuarter());
			row.add("Q" + worker.getQuarter());
			return row;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * processes a MondrianTableDescription using {@link #generateStarTableWithQuery(String, String, String...)} (which creates the table and the indices and the collations
	 * @param mondrianTable
	 * @throws SQLException
	 */
	protected void generateStarTable(MondrianTableDescription mondrianTable) throws SQLException {
		String query = "SELECT * FROM v_" + mondrianTable.tableName;
		if (mondrianTable.isTranslated()) {
			generateStarTableWithQueryInPostgres(mondrianTable.tableName, mondrianTable.primaryKeyColumnName, query, mondrianTable.indexedColumns);
		
			// now, the base (non-multilingual) dimension table is ready, so we need to make multilingual clones of it		
			for (String locale:locales)
				cloneMondrianTableForLocale(mondrianTable, locale);
		} else {
			monetConn.createTableFromQuery(conn, query, mondrianTable.tableName);
		}
	}
	
	/**
	 * makes a localized version of a Mondrian dimension table, using the i18n fetchers
	 * @param mondrianTable
	 * @param locale
	 * @throws SQLException
	 */
	protected void cloneMondrianTableForLocale(MondrianTableDescription mondrianTable, String locale) throws SQLException {
		if (!mondrianTable.isTranslated())
			return;
		//logger.warn("cloning table " + mondrianTable.tableName + " into locale " + locale);
		String localizedTableName = mondrianTable.tableName + "_" + locale;
		
		List<List<Object>> vals = mondrianTable.readTranslatedTable(this.conn, locale, null);
		monetConn.copyTableStructureFromPostgres(this.conn, mondrianTable.tableName, localizedTableName);
		SQLUtils.insert(monetConn.conn, localizedTableName, null, null, monetConn.getTableColumns(localizedTableName), vals);

		
		// -> cannot check sanity because a write to the db might have just happened during the ETL, thus invalidating the cloning <-
		
/*		// checking sanity
		long initTableSz = SQLUtils.countRows(conn, mondrianTable.tableName);
		long createdTableSz = SQLUtils.countRows(monetConn.conn, localizedTableName);
		if (initTableSz != createdTableSz && !mondrianTable.isFiltering)
			throw new RuntimeException(String.format("HUGE BUG: multilingual-cloned dimension table has a size of %d, while the original dimension table has a size of %d", createdTableSz, initTableSz));*/
	}
	
	/**
	 * makes a localized version of the activities portion of a Mondrian dimension table, using the i18n fetchers
	 * @param mondrianTable
	 * @param locale
	 * @throws SQLException
	 */
	protected void incrementallyCloneMondrianTableForLocale(MondrianTableDescription mondrianTable, String locale) throws SQLException {
		if (!mondrianTable.isTranslated())
			return;
		if (etlConfig.activityIds.isEmpty())
			return;
		logger.warn("incrementally cloning table " + mondrianTable.tableName + " into locale " + locale);
		String localizedTableName = mondrianTable.tableName + "_" + locale;
		
		List<List<Object>> vals = mondrianTable.readTranslatedTable(this.conn, locale, " WHERE " + etlConfig.activityIdsIn("amp_activity_id"));
//		monetConn.copyTableStructureFromPostgres(this.conn, mondrianTable.tableName, localizedTableName);
		monetConn.executeQuery("DELETE FROM " + localizedTableName + " WHERE " + etlConfig.activityIdsIn("amp_activity_id"));
		SQLUtils.insert(monetConn.conn, localizedTableName, null, null, monetConn.getTableColumns(localizedTableName), vals);
		
		// -> cannot check sanity because a write to the db might have just happened during the ETL, thus un-consistenting the cloning <-
		
/*		// checking sanity
		long initTableSz = SQLUtils.countRows(conn, mondrianTable.tableName);
		long createdTableSz = SQLUtils.countRows(monetConn.conn, localizedTableName);
		if (initTableSz != createdTableSz && !mondrianTable.isFiltering)
			throw new RuntimeException(String.format("HUGE BUG: multilingual-cloned dimension table %s has a size of %d, while the original dimension table has a size of %d", mondrianTable.tableName, createdTableSz, initTableSz)); */
	}
	
	
	/**
	 * makes a snapshot of the view v_<strong>tableName</strong> in the table <strong>tableName</strong> and then creates indices on the relevant columns
	 * @param tableName
	 * @param columnsToIndex columns on which to create indices
	 * @throws SQLException
	 */
	protected void generateStarTableWithQueryInPostgres(String tableName, String primaryKey, String tableCreationQuery, Collection<String> columnsToIndex) throws SQLException {
		SQLUtils.executeQuery(conn, "DROP TABLE IF EXISTS " + tableName);
		SQLUtils.executeQuery(conn, "CREATE TABLE " + tableName + " AS " + tableCreationQuery);
		SQLUtils.flush(conn);
		postprocessDimensionTable(tableName, primaryKey, columnsToIndex);
	}

	
	/**
	 * makes any needed postprocessing on a Mondrian Dimension table (indices, collations, etc)
	 * @param tableName
	 * @param columnsToIndex
	 */
	protected void postprocessDimensionTable(String tableName, String primaryKey, Collection<String> columnsToIndex) {
//		// change text column types' collation to C -> 7x faster GROUP BY / ORDER BY for stupid Mondrian
//		Map<String, String> tableColumns = SQLUtils.getTableColumnsWithTypes(tableName, true);
//		Set<String> textColumnTypes = new HashSet<>(Arrays.asList("text", "character varying", "varchar"));
//		for (String columnName:tableColumns.keySet()) {
//			String columnType = tableColumns.get(columnName);
//			if (textColumnTypes.contains(columnType)) {
//				String q = String.format("ALTER TABLE %s ALTER COLUMN %s SET DATA TYPE text COLLATE \"C\"", tableName, columnName);
//				SQLUtils.executeQuery(conn, q);
//			}
//		}
		
		// create indices
		for (String columnToIndex:columnsToIndex) {
			String indexCreationQuery = String.format("CREATE INDEX %s_%s ON %s(%s)", tableName, columnToIndex, tableName, columnToIndex);
			SQLUtils.executeQuery(conn, indexCreationQuery);
		}
		
//		// create primary key
//		if (primaryKey != null) {
//			SQLUtils.executeQuery(conn, String.format("ALTER TABLE %s ADD CONSTRAINT %s_PK PRIMARY KEY (%s)", tableName, tableName, primaryKey));
//		}
	}

	/**
	 * drops preexisting fact table and creates an empty one
	 */
	protected void recreateFactTable() throws SQLException {
		monetConn.dropTable(FACT_TABLE.tableName);
		FACT_TABLE.create(monetConn.conn, false);
	}
	
	/**
	 * incremental ETL for the per-activity data structures
	 * @throws SQLException
	 */
	protected void generateActivitiesEntries() throws SQLException {
		generateSectorsEtlTables();
		StopWatch.next(MONDRIAN_ETL, true, "generateSectorsEntries");
		
		generateProgramsEtlTables();
		StopWatch.next(MONDRIAN_ETL, true, "generateProgramsEntries");
		
		generateLocationsEtlTables();
		StopWatch.next(MONDRIAN_ETL, true, "generateLocationsEntries");
		
		generateOrganisationsEtlTables();
		StopWatch.next(MONDRIAN_ETL, true, "generateOrganisationsEntries");
		
		generateRawTransactionTables();
		generateFactTable();
		
		generateActivitiesDimensionsTables();
		monetConn.flush();
	}

	/**
	 * (incremental or full) generates dimension tables which have per-activity rows
	 * @throws SQLException
	 */
	protected void generateActivitiesDimensionsTables() throws SQLException {
		logger.warn("building per-activity-value dimension tables...");
		for (final MondrianTableDescription mondrianTable:MondrianTablesRepository.MONDRIAN_ACTIVITY_DIMENSIONS)
			generatePerActivityTables(mondrianTable);
		StopWatch.next(MONDRIAN_ETL, true, "generateActivityDimensionEntries");
	}
	
	/**
	 * (incremental or full) generates raw transactions tables to be used Monet-side for the Cartesian
	 * @throws SQLException
	 */
	protected void generateRawTransactionTables() throws SQLException {
		logger.warn("materializing the raw transactions tables...");		
		for (MondrianTableDescription mondrianTable: MondrianTablesRepository.MONDRIAN_RAW_TRANSACTIONS_TABLES)
			generatePerActivityTables(mondrianTable);
	}
	
	/**
	 * syncs a mondrian table between its view psql-side and materialization monet-side
	 * @param mondrianTable
	 * @throws SQLException
	 */
	protected void generatePerActivityTables(MondrianTableDescription mondrianTable) throws SQLException {
		String query = "SELECT * FROM v_" + mondrianTable.tableName + " WHERE " + etlConfig.activityIdsIn("amp_activity_id");
		
		/**
		 * table not translated -> original only exists in Monet
		 * table translated and filtered: original only exists in postgres, translated only exist in Monet
		 * table translated and not filtered: original only exists in postgres, translated only exist in Monet
		 *  
		 */
		if (etlConfig.fullEtl) {
			long start = System.currentTimeMillis();
			
			if (!mondrianTable.isTranslated()) {
				monetConn.createTableFromQuery(this.conn, query, mondrianTable.tableName);				
			} else {
				generateStarTableWithQueryInPostgres(mondrianTable.tableName, mondrianTable.primaryKeyColumnName, query, mondrianTable.indexedColumns);
			}
			long baseDone = System.currentTimeMillis();
			for (String locale:locales)
				cloneMondrianTableForLocale(mondrianTable, locale);
			long cloningDone = System.currentTimeMillis();
			logger.info("full ETL on " + mondrianTable.tableName + ", base table took " + (baseDone - start) + " ms");
			logger.info("\tfull ETL on " + mondrianTable.tableName + ", cloning for locales " + locales + " took " + (cloningDone - baseDone) + " ms");
		} else {
			// relation exists in Monet IF (table is not translated) OR (isTranslated AND is not filtered)
			if (mondrianTable.isTranslated()) {
				SQLUtils.executeQuery(conn, "DELETE FROM " + mondrianTable.tableName + " WHERE " + etlConfig.activityIdsIn("amp_activity_id"));
				SQLUtils.executeQuery(conn, "INSERT INTO " + mondrianTable.tableName + " " + query);
				for (String locale:locales)
					incrementallyCloneMondrianTableForLocale(mondrianTable, locale);
			} else {
				monetConn.executeQuery("DELETE FROM " + mondrianTable.tableName + " WHERE " + etlConfig.activityIdsIn("amp_activity_id"));
				monetConn.copyEntries(mondrianTable.tableName, SQLUtils.rawRunQuery(conn, query, null));
			}
		}
		runPledgeLogue(mondrianTable);
		//runLogue(mondrianTable.epilogue);
	}
	
	/**
	 * ignores translations for now, as all translated pledges contents lies in the fact table
	 * @param mondrianTable
	 * @throws SQLException
	 */
	protected void runPledgeLogue(MondrianTableDescription mondrianTable) throws SQLException {
		
		if (mondrianTable.pledgeView == null || etlConfig.pledgeIds.isEmpty())
			return;

		if (!SQLUtils.tableExists(mondrianTable.pledgeView))
			throw new RuntimeException("Mondrian table " + mondrianTable.tableName + " has a pledge view defined which is missing: " + mondrianTable.pledgeView);
		
		Set<String> localeSuffixes = new LinkedHashSet<>(mondrianTable.isTranslated() ? locales : Arrays.asList(""));
				
		logger.warn("incrementally cloning pledges table " + mondrianTable.tableName + " into locales " + localeSuffixes);
		List<String> columns = new ArrayList<String>(SQLUtils.getTableColumns(mondrianTable.pledgeView));
		columns.set(0, "pledge_id AS amp_activity_id");
		String columnsPart = SQLUtils.generateCSV(columns);
		try(ResultSet rs = SQLUtils.rawRunQuery(conn, "SELECT " + columnsPart + " FROM " + mondrianTable.pledgeView + " WHERE " + etlConfig.pledgeIdsIn("pledge_id"), null)) {
			List<List<Object>> entries = mondrianTable.readFetchedTable(rs, "en");
			for(List<Object> entry:entries) {
				if (entry.size() < 2)
					throw new RuntimeException("while pledge-logueing MTD " + mondrianTable.tableName + ", found an empty structure in pledge-view " + mondrianTable.pledgeView + "; dying");
				entry.set(0, PersistenceManager.getLong(entry.get(0)) + PLEDGE_ID_ADDER);
			}
			
			for(String suffix:localeSuffixes) {
				String usedSuffix = suffix.isEmpty() ? "" : ("_" + suffix);
				String localizedTableName = mondrianTable.tableName + usedSuffix;
				monetConn.executeQuery("DELETE FROM " + localizedTableName + " WHERE " + etlConfig.pledgeIdsIn("amp_activity_id - " + PLEDGE_ID_ADDER));
				SQLUtils.insert(monetConn.conn, localizedTableName, null, null, monetConn.getTableColumns(localizedTableName), entries);
			}
		}
		//monetConn.executeQuery("DELETE FROM " + mondrianTable.);
	}
	
	//protected
	
	
	/**
	 * runs the monet-side Cartesian
	 * @throws SQLException
	 */
	protected void generateFactTable() throws SQLException {
		logger.warn("running the fact-table-generating cartesian...");
		List<String> factTableQueries = generateFactTableQueries();
		//monetConn.dropTable(FACT_TABLE.tableName);
		for(int i = 0; i < factTableQueries.size(); i++) {
			logger.warn("\texecuting query #" + (i + 1) + "...");
			String query = factTableQueries.get(i);
			monetConn.executeQuery(query);
			logger.warn("\t...executing query #" + (i + 1) + " done");
		}
		logger.warn("...running the fact-table-generating cartesian done");
		long factTableSize = SQLUtils.countRows(monetConn.conn, "mondrian_fact_table");
		long nrTransactions = SQLUtils.countRows(monetConn.conn, "mondrian_raw_donor_transactions");
		double explosionFactor = nrTransactions > 0 ? ((1.0 * factTableSize) / nrTransactions) : 1.0;
		logger.warn(
				String.format("fact table generated %d fact table entries using %d initial transactions (multiplication factor: %.2f)", factTableSize, nrTransactions, explosionFactor));
	}
	
	/**
	 * (incremental or full) runs a query on the PostgreSQL database, redistributes percentages, then writes results (one-table-per-locale) to Monet
	 * @param query
	 * @param tableName
	 * @throws SQLException
	 */
	protected void runEtlOnTable(String query, String tableName, boolean forceIncremental) throws SQLException {
		try (ResultSet rs = SQLUtils.rawRunQuery(conn, query, null)) {
			// Map<activityId, percentages_on_sector_scheme
			Map<Long, PercentagesDistribution> secs = PercentagesDistribution.readInput(rs);
			serializeETLTable(secs, tableName, false, forceIncremental);
		}
	}

	/**
	 * incremental ETL for the etl_ tables pertaining to orgs
	 * @throws SQLException
	 */
	protected void generateOrganisationsEtlTables() throws SQLException {
		if (etlConfig.activityIds.isEmpty())
			return;
		//logger.warn("generating orgs ETL tables...");
		String activitiesCondition = etlConfig.activityIdsIn("amp_activity_id");
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_executing_agency WHERE (%s)", activitiesCondition), "etl_executing_agencies", false);
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_beneficiary_agency WHERE (%s)", activitiesCondition), "etl_beneficiary_agencies", false);
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_implementing_agency WHERE (%s)", activitiesCondition), "etl_implementing_agencies", false);
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_responsible_organisation WHERE (%s)", activitiesCondition), "etl_responsible_agencies", false);
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_contracting_agency WHERE (%s)", activitiesCondition), "etl_contracting_agencies", false);
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_regional_group WHERE (%s)", activitiesCondition), "etl_regional_groups", false);
		runEtlOnTable(String.format("select amp_activity_id, amp_org_id, percentage from v_sector_group WHERE (%s)", activitiesCondition), "etl_sector_groups", false);
	}
	
	/**
	 * incremental ETL for the etl_ tables pertaining to sectors
	 * @throws SQLException
	 */
	protected void generateSectorsEtlTables() throws SQLException {
		//logger.warn("generating Sector ETL tables...");
		generateSectorsEtlTables("Primary");
		generateSectorsEtlTables("Secondary");
		generateSectorsEtlTables("Tertiary");
	}

	/**
	 * incremental ETL for the etl_ tables pertaining to programs
	 * @throws SQLException
	 */
	protected void generateProgramsEtlTables() throws SQLException {
		//logger.warn("generating Program ETL tables...");
		generateProgramsEtlTables("National Plan Objective");
		generateProgramsEtlTables("Primary Program");
		generateProgramsEtlTables("Secondary Program");
		generateProgramsEtlTables("Tertiary Program");
	}

	/**
	 * incremental ETL for the etl_ tables pertaining to locations
	 * @throws SQLException
	 */
	protected void generateLocationsEtlTables() throws SQLException {
		if (etlConfig.activityIds.isEmpty() && etlConfig.pledgeIds.isEmpty())
			return;
		//logger.warn("generating location ETL tables...");
		String activitiesCondition = etlConfig.activityIdsIn("aal.amp_activity_id");
		String query = String.format("select aal.amp_activity_id, acvl.id, aal.location_percentage from amp_activity_location aal, amp_category_value_location acvl, amp_location al WHERE (%s) AND (aal.amp_location_id = al.amp_location_id) AND (al.location_id = acvl.id)", activitiesCondition);
		runEtlOnTable(query, "etl_locations", false);
		
		String pledgesCondition = etlConfig.pledgeIdsIn("pledge_id");
		String pledgesQuery = String.format("select pledge_id + %d AS amp_activity_id, location_id, location_percentage from amp_funding_pledges_location WHERE (%s)", PLEDGE_ID_ADDER, pledgesCondition);
		runEtlOnTable(pledgesQuery, "etl_locations", true);		
	}
	
	protected void generateProgramsEtlTables(String schemeName) throws SQLException {
		if (etlConfig.activityIds.isEmpty() && etlConfig.pledgeIds.isEmpty())
			return;
		String activitiesCondition = etlConfig.activityIdsIn("aap.amp_activity_id");
		String query = String.format("select aap.amp_activity_id, aap.amp_program_id, aap.program_percentage FROM amp_activity_program aap, v_mondrian_programs vmp WHERE (%s) AND (aap.amp_program_id = vmp.amp_theme_id) AND (vmp.program_setting_name = '%s')", activitiesCondition, schemeName);
		runEtlOnTable(query, "etl_activity_program_" + schemeName.replace(' ', '_').toLowerCase(), false);
		
		String pledgesCondition = etlConfig.pledgeIdsIn("afpp.pledge_id");
		String pledgesQuery = String.format("select afpp.pledge_id + %d AS amp_activity_id, afpp.amp_program_id, afpp.program_percentage FROM amp_funding_pledges_program afpp, v_mondrian_programs vmp WHERE (%s) AND (afpp.amp_program_id = vmp.amp_theme_id) AND (vmp.program_setting_name = '%s')", PLEDGE_ID_ADDER, pledgesCondition, schemeName);
		runEtlOnTable(pledgesQuery, "etl_activity_program_" + schemeName.replace(' ', '_').toLowerCase(), true);
	}
	
	protected void generateSectorsEtlTables(String schemeName) throws SQLException {
		if (etlConfig.activityIds.isEmpty() && etlConfig.activityIds.isEmpty())
			return;
		String activitiesCondition = etlConfig.activityIdsIn("aas.amp_activity_id");
		String query = String.format("select aas.amp_activity_id, aas.amp_sector_id, aas.sector_percentage from amp_activity_sector aas, v_mondrian_sectors vms WHERE (%s) AND (aas.amp_sector_id = vms.amp_sector_id) AND (vms.typename='%s')", activitiesCondition, schemeName);
		runEtlOnTable(query, "etl_activity_sector_" + schemeName.toLowerCase(), false);
		
		String pledgesCondition = etlConfig.pledgeIdsIn("afps.pledge_id");
		String pledgesQuery = String.format("select afps.pledge_id + %d AS amp_activity_id, afps.amp_sector_id, afps.sector_percentage from amp_funding_pledges_sector afps, v_mondrian_sectors vms WHERE (%s) AND (afps.amp_sector_id = vms.amp_sector_id) AND (vms.typename='%s')", PLEDGE_ID_ADDER, pledgesCondition, schemeName);
		runEtlOnTable(pledgesQuery, "etl_activity_sector_" + schemeName.toLowerCase(), true);
	}
	
	/**
	 * (incremental or full) processes an ETL table with cleaned-up percentages
	 * 	- full: DROP + CREATE + INSERT
	 *  - incremental: DELETE + INSERT
	 * @param percs
	 * @param tableName
	 * @throws SQLException
	 */
	protected void serializeETLTable(Map<Long, PercentagesDistribution> percs, String tableName, boolean createIndices, boolean forceIncremental) throws SQLException {
		boolean recreateTable = etlConfig.fullEtl && (!forceIncremental);
		
		if (recreateTable) {
			monetConn.dropTable(tableName);
			monetConn.executeQuery("CREATE TABLE " + tableName + " (act_id integer, ent_id integer, percentage double)");
		} else {
			monetConn.executeQuery("DELETE FROM " + tableName + " WHERE act_id IN (" + Util.toCSStringForIN(percs.keySet()) + ")");
		}
				
		List<List<Object>> entries = new ArrayList<>();
		for (long actId:percs.keySet()) {
			PercentagesDistribution pd = percs.get(actId);
			pd.postProcess(MONDRIAN_DUMMY_ID_FOR_ETL);
			for (Map.Entry<Long, Double> entry:pd.getPercentages().entrySet())
				entries.add(Arrays.<Object>asList(actId, entry.getKey(), entry.getValue() / 100.0));
		}
		SQLUtils.insert(monetConn.conn, tableName, null, null, Arrays.asList("act_id", "ent_id", "percentage"), entries);

		createIndices &= recreateTable;
		if (createIndices) {
			SQLUtils.executeQuery(conn, String.format("CREATE INDEX %s_act_id_idx ON %s(act_id)", tableName, tableName)); // create index on activityId
			SQLUtils.executeQuery(conn, String.format("CREATE INDEX %s_ent_id_idx ON %s(ent_id)", tableName, tableName)); // create index on entityId (entity=sector/program/org)
			SQLUtils.executeQuery(conn, String.format("CREATE INDEX %s_act_ent_id_idx ON %s(act_id, ent_id)", tableName, tableName)); // create index on entityId (entity=sector/program/org)
		}
		monetConn.flush();
	}
	
//	/**
//	 * clears the fact table of the stale entries signaled by {@link #activityIdsToRemove} and {@link #pledgesToRedo}
//	 */
//	protected void deleteStaleFactTableEntries() {
////		String deleteActivitiesQuery = String.format("DELETE FROM mondrian_fact_table WHERE entity_type = '%c' AND entity_id IN (%s)", ReportEntityType.ENTITY_TYPE_ACTIVITY.getAsChar(), Util.toCSStringForIN(activityIdsToRemove));
//		String deleteActivitiesQuery = String.format("DELETE FROM mondrian_fact_table WHERE entity_id IN (%s)", Util.toCSStringForIN(activityIdsToRemove));
//		Set<Long> pledgesToRedoChanged = new HashSet<>();
//		for(Long pledgeId:pledgesToRedo)
//			pledgesToRedoChanged.add(pledgeId + AmpReportGenerator.PLEDGES_IDS_START);
//		String deletePledgesQuery = String.format("DELETE FROM mondrian_fact_table WHERE entity_id IN (%s)", Util.toCSStringForIN(pledgesToRedoChanged));
//		monetConn.executeQuery(deleteActivitiesQuery);
//		monetConn.executeQuery(deletePledgesQuery);
//	}
	
	/**
	 * reads from factTableQuery the list of queries, inserts entity IDs in it and then filtering out those queries which would do nothing anyway
	 * @return
	 */
	protected List<String> generateFactTableQueries() {
		try {
			logger.info("The path for the factTabelQuery.sql is:");
			logger.info(this.getClass().getResource("").getPath());
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("factTableQuery.sql"), "utf-8"));
			StringBuilder builder = new StringBuilder();
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				if (line.trim().startsWith("--"))
					continue;
				builder.append(line);
				builder.append(" ");
			};
			reader.close();
			String str = builder.toString().trim();
			StringTokenizer stok = new StringTokenizer(str, ";");
			List<String> res = new ArrayList<>();
			Set<Long> allEntities = etlConfig.getAllEntityIds();
			while (stok.hasMoreTokens()) {
				String q = stok.nextToken();
				if (q.indexOf("@@activityIdCondition@@") != 0 && allEntities.isEmpty())
					continue; // query references activities, but these are empty -> it is useless
				String activityCondition = etlConfig.fullEtl ? " >0 " : (" IN (" +Util.toCSStringForIN(allEntities) + ")");
				res.add(q.replace("@@activityIdCondition@@", activityCondition));
			}
			return res;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static EtlResult runETL(boolean forceFull) {
		synchronized(ETL_LOCK) {
			try(Connection conn = PersistenceManager.getJdbcConnection()) {
				try(MonetConnection monetConn = MonetConnection.getConnection()) {
					MondrianETL etl = new MondrianETL(conn, monetConn, forceFull);
					EtlResult etlResult = etl.execute();
					logger.error("Mondrian ETL result: " + etlResult);
					return etlResult;
				}
			}
			catch(Exception e) {
				if (e instanceof RuntimeException)
					throw (RuntimeException) e;
				throw new RuntimeException(e);
			}
		}
	}
}
