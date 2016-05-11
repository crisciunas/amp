package org.dgfoundation.amp.ar.amp212;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dgfoundation.amp.ar.ColumnConstants;
import org.dgfoundation.amp.ar.MeasureConstants;
import org.dgfoundation.amp.mondrian.ReportAreaForTests;
import org.dgfoundation.amp.newreports.AmountsUnits;
import org.dgfoundation.amp.newreports.AreaOwner;
import org.dgfoundation.amp.newreports.GroupingCriteria;
import org.dgfoundation.amp.newreports.ReportSpecificationImpl;
import org.dgfoundation.amp.nireports.GrandTotalsDigest;
import org.dgfoundation.amp.nireports.output.NiReportExecutor;
import org.dgfoundation.amp.testmodels.NiReportModel;
import org.digijava.module.aim.helper.DateConversion;
import org.junit.Test;

/**
 * 
 * testcases for the fetching states of AMP + the AMP schema
 * 
 * @author Constantin Dolghier
 *
 */
public class AmpSchemaSanityTests extends BasicSanityChecks {

	final List<String> mtefActs = Arrays.asList(
		"mtef activity 1",
		"mtef activity 2",
		"Pure MTEF Project",
		"activity with MTEFs",
		"Activity with both MTEFs and Act.Comms",
		"activity with many MTEFs",
		"Test MTEF directed",
		"activity with pipeline MTEFs and act. disb"
	);
	
	final List<String> ppcActs = Arrays.asList(
			"Proposed Project Cost 1 - USD",
			"Proposed Project Cost 2 - EUR",
			"SubNational no percentages",
			"Activity with primary_tertiary_program",
			"activity with primary_program",
			"activity with tertiary_program",
			"activity 1 with agreement",
			"activity with directed MTEFs"
		);
	
	final List<String> humanitarianAidActs = Arrays.asList(
			"TAC_activity_1", 
			"crazy funding 1", 
			"date-filters-activity", 
			"Activity with planned disbursements", 
			"TAC_activity_2", 
			"pledged 2"
		);
	
	public AmpSchemaSanityTests() {
		super("AmpReportsSchema sanity tests");
	}
	
	@Override
	protected NiReportExecutor getNiExecutor(List<String> activityNames) {
		return getDbExecutor(activityNames);
	}
	
	@Test
	public void testActivityIds() {
		NiReportModel cor =new NiReportModel("testcase amp activity ids")
				.withHeaders(Arrays.asList(
						"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 4))",
						"(Activity Id: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 1, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2))",
						"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1))"))
					.withWarnings(Arrays.asList())
					.withBody(      new ReportAreaForTests(null)
				      .withContents("Activity Id", "", "Project Title", "", "Totals-Actual Commitments", "1,011,456", "Totals-Actual Disbursements", "0")
				      .withChildren(
				        new ReportAreaForTests(new AreaOwner(19), "Activity Id", "19", "Project Title", "Pure MTEF Project"),
				        new ReportAreaForTests(new AreaOwner(70), "Activity Id", "70", "Project Title", "Activity with both MTEFs and Act.Comms", "Totals-Actual Commitments", "888,000"),
				        new ReportAreaForTests(new AreaOwner(73), "Activity Id", "73", "Project Title", "activity with directed MTEFs", "Totals-Actual Commitments", "123,456")      ));
		runNiTestCase(
				buildSpecification("testcase amp activity ids", 
						Arrays.asList(ColumnConstants.ACTIVITY_ID, ColumnConstants.PROJECT_TITLE), 
						Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS, MeasureConstants.ACTUAL_DISBURSEMENTS), 
						null, GroupingCriteria.GROUPING_TOTALS_ONLY),
				"en", 
				Arrays.asList("Pure MTEF Project", "activity with directed MTEFs", "Activity with both MTEFs and Act.Comms"),
				cor);
	}
	
	@Test
	public void testProjectImplementationDelay() {
		Date toDate = new Timestamp(1461099600000l);
		String corNowCalculation = String.format("%s", (DateConversion.getFormattedPeriod(
				DateConversion.getPeriod(toDate, new Date())))).toLowerCase();
		
		NiReportModel cor = new NiReportModel("testcase for Project Implementation Delay")
				.withHeaders(Arrays.asList(
						"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 3))",
						"(Project Implementation Delay: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 1, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1))",
						"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))"))
					.withWarnings(Arrays.asList())
					.withBody(      new ReportAreaForTests(null)
				      .withContents("Project Implementation Delay", "", "Project Title", "", "Totals-Actual Commitments", "0")
				      .withChildren(
				        new ReportAreaForTests(new AreaOwner(81), "Project Implementation Delay", "20 days", "Project Title", "PID: original, proposed, actual"),
				        new ReportAreaForTests(new AreaOwner(82), "Project Implementation Delay", "6 years 21 days", "Project Title", "PID: original, actual"),
				        new ReportAreaForTests(new AreaOwner(83), "Project Title", "PID: original > actual"),
				        new ReportAreaForTests(new AreaOwner(84), "Project Implementation Delay", corNowCalculation, "Project Title", "PID: original"),
				        new ReportAreaForTests(new AreaOwner(85), "Project Implementation Delay", "20 days", "Project Title", "PID: original, proposed")      ));
		runNiTestCase(
				buildSpecification("testcase for Project Implementation Delay", 
						Arrays.asList(ColumnConstants.PROJECT_IMPLEMENTATION_DELAY, ColumnConstants.PROJECT_TITLE), 
						Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS), 
						null, GroupingCriteria.GROUPING_TOTALS_ONLY),
				"en", 
				Arrays.asList("PID: original, proposed, actual", "PID: original, actual", "PID: original > actual", "PID: original", "PID: original, proposed"),
				cor);
	}
	
	@Test
	public void testPlannedActualArrears() {
		NiReportModel cor = new NiReportModel("Testcase for Actual and Planned Arrears")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 3))",
					"(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 1, colSpan: 2))",
					"(Actual Arrears: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 1, colSpan: 1));(Planned Arrears: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Totals-Actual Arrears", "132,000", "Totals-Planned Arrears", "72,000")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(78), "Project Title", "activity with many MTEFs"),
			        new ReportAreaForTests(new AreaOwner(80), "Project Title", "arrears test", "Totals-Actual Arrears", "132,000", "Totals-Planned Arrears", "72,000")      ));

		runNiTestCase(
				buildSpecification("Testcase for Actual and Planned Arrears", 
						Arrays.asList(ColumnConstants.PROJECT_TITLE), 
						Arrays.asList(MeasureConstants.ACTUAL_ARREARS, MeasureConstants.PLANNED_ARREARS), 
						null, GroupingCriteria.GROUPING_TOTALS_ONLY),
				"en", 
				Arrays.asList("activity with many MTEFs", "arrears test"),
				cor);
	}
	
	@Test
	public void testMtefColumnsPlain() {
		assertEquals("{RAW / Project Title=, RAW / MTEF 2011/2012=1283182.4159, RAW / MTEF 2012/2013=202437, RAW / MTEF 2013/2014=120180.405, RAW / Funding / 2006 / Actual Commitments=80000, RAW / Funding / 2006 / Actual Disbursements=0, RAW / Funding / 2009 / Actual Commitments=78470, RAW / Funding / 2009 / Actual Disbursements=0, RAW / Funding / 2010 / Actual Commitments=0, RAW / Funding / 2010 / Actual Disbursements=613561.3161, RAW / Funding / 2011 / Actual Commitments=896327.2977, RAW / Funding / 2011 / Actual Disbursements=0, RAW / Funding / 2012 / Actual Commitments=19577.5, RAW / Funding / 2012 / Actual Disbursements=9162, RAW / Funding / 2013 / Actual Commitments=5905874.9666, RAW / Funding / 2013 / Actual Disbursements=954144.5636, RAW / Funding / 2014 / Actual Commitments=7409649.482335, RAW / Funding / 2014 / Actual Disbursements=576269.62, RAW / Funding / 2015 / Actual Commitments=1803396.8724, RAW / Funding / 2015 / Actual Disbursements=399024.454, RAW / Totals / Actual Commitments=16193296.119035, RAW / Totals / Actual Disbursements=2552161.9537, RAW / Totals / MTEF=1605799.8209}", 
			buildDigest(spec("AMP-16100-flat-mtefs-eur"), acts, new GrandTotalsDigest(z -> true)).toString());
	}
	
	@Test
	public void testMtefColumnsMixedPlain() {
		assertEquals("{RAW / Project Title=, RAW / MTEF 2011/2012=1718011, RAW / Pipeline MTEF Projections 2011/2012=908888, RAW / Projection MTEF Projections 2011/2012=809123, RAW / MTEF 2012/2013=271000, RAW / Pipeline MTEF Projections 2012/2013=108000, RAW / Projection MTEF Projections 2012/2013=163000, RAW / MTEF 2013/2014=158654, RAW / Pipeline MTEF Projections 2013/2014=158654, RAW / Projection MTEF Projections 2013/2014=0, RAW / Funding / 2006 / Actual Commitments=96840.576201, RAW / Funding / 2009 / Actual Commitments=100000, RAW / Funding / 2011 / Actual Commitments=1213119, RAW / Funding / 2012 / Actual Commitments=25000, RAW / Funding / 2013 / Actual Commitments=7842086, RAW / Funding / 2014 / Actual Commitments=8159813.768451, RAW / Funding / 2015 / Actual Commitments=1971831.841736, RAW / Totals / Actual Commitments=19408691.186388, RAW / Totals / MTEF=2147665, RAW / Totals / Pipeline MTEF=1175542, RAW / Totals / Projection MTEF=972123}", 
			buildDigest(spec("AMP-21275-all-plain-mtefs"), acts, new GrandTotalsDigest(z -> true)).toString());
	}

	@Test
	public void testMtefColumnsMixedPlain2() {
		assertEquals("{RAW / Project Title=, RAW / MTEF 2011/2012=1718011, RAW / Pipeline MTEF Projections 2011/2012=908888, RAW / MTEF 2012/2013=271000, RAW / Projection MTEF Projections 2012/2013=163000, RAW / MTEF 2013/2014=158654, RAW / Funding / 2006 / Actual Commitments=96840.576201, RAW / Funding / 2009 / Actual Commitments=100000, RAW / Funding / 2011 / Actual Commitments=1213119, RAW / Funding / 2012 / Actual Commitments=25000, RAW / Funding / 2013 / Actual Commitments=7842086, RAW / Funding / 2014 / Actual Commitments=8159813.768451, RAW / Funding / 2015 / Actual Commitments=1971831.841736, RAW / Totals / Actual Commitments=19408691.186388, RAW / Totals / MTEF=2147665, RAW / Totals / Pipeline MTEF=908888, RAW / Totals / Projection MTEF=163000}", 
			buildDigest(spec("AMP-21275-all-plain-mtefs-rare"), acts, new GrandTotalsDigest(z -> true)).toString());
	}
	
	@Test
	public void testMtefColumnsBehaveLikeTrivialMeasuresOnHierarchies() {
		NiReportModel cor = new NiReportModel("AMP-22422-test-mtefs-hiers")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 15))",
					"(Executing Agency: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Contracting Agency: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 2, colSpan: 1));(MTEF 2010/2011: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 3, colSpan: 1));(MTEF 2011/2012: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 4, colSpan: 1));(MTEF 2012/2013: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 5, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 6, colSpan: 6));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 12, colSpan: 3))",
					"(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 8, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 10, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(MTEF: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Executing Agency", "", "Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "1 718 011", "MTEF 2012/2013", "271 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "545 000", "Funding-2015-Actual Commitments", "1 011 456", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "1 011 456", "Totals-Actual Disbursements", "768 777", "Totals-MTEF", "1 989 011")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "Finland")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "40 740,48", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "40 740,48", "Totals-Actual Disbursements", "0", "Totals-MTEF", "0", "Executing Agency", "Finland")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "40 740,48", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "40 740,48", "Totals-Actual Disbursements", "0", "Totals-MTEF", "0", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "40 740,48", "Totals-Actual Commitments", "40 740,48")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "Ministry of Economy")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "33 888", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "0", "Totals-MTEF", "33 888", "Executing Agency", "Ministry of Economy")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "33 888", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "0", "Totals-MTEF", "33 888", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(19), "Project Title", "Pure MTEF Project", "MTEF 2011/2012", "33 888", "Totals-MTEF", "33 888")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "Norway")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "27 160,32", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "27 160,32", "Totals-Actual Disbursements", "0", "Totals-MTEF", "0", "Executing Agency", "Norway")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "27 160,32", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "27 160,32", "Totals-Actual Disbursements", "0", "Totals-MTEF", "0", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "27 160,32", "Totals-Actual Commitments", "27 160,32")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "UNDP")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "272 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "272 500", "Totals-MTEF", "0", "Executing Agency", "UNDP")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "272 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "272 500", "Totals-MTEF", "0", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(24), "Project Title", "Eth Water", "Funding-2013-Actual Disbursements", "272 500", "Totals-Actual Disbursements", "272 500")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "USAID")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "55 555,2", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "55 555,2", "Totals-Actual Disbursements", "0", "Totals-MTEF", "0", "Executing Agency", "USAID")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "55 555,2", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "55 555,2", "Totals-Actual Disbursements", "0", "Totals-MTEF", "0", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "55 555,2", "Totals-Actual Commitments", "55 555,2")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "Water Foundation")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "150 000", "MTEF 2012/2013", "65 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "143 777", "Totals-MTEF", "215 000", "Executing Agency", "Water Foundation")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "150 000", "MTEF 2012/2013", "65 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "143 777", "Totals-MTEF", "215 000", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(18), "Project Title", "Test MTEF directed", "MTEF 2011/2012", "150 000", "MTEF 2012/2013", "65 000", "Funding-2010-Actual Disbursements", "143 777", "Totals-Actual Disbursements", "143 777", "Totals-MTEF", "215 000")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "World Bank")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "272 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "272 500", "Totals-MTEF", "0", "Executing Agency", "World Bank")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined")).withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "0", "MTEF 2012/2013", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "272 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "272 500", "Totals-MTEF", "0", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(24), "Project Title", "Eth Water", "Funding-2013-Actual Disbursements", "272 500", "Totals-Actual Disbursements", "272 500")          )        ),
			        new ReportAreaForTests(new AreaOwner("Executing Agency", "Executing Agency: Undefined")).withContents("Contracting Agency", "", "Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "1 534 123", "MTEF 2012/2013", "206 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "888 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "888 000", "Totals-Actual Disbursements", "80 000", "Totals-MTEF", "1 740 123", "Executing Agency", "Executing Agency: Undefined")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Contracting Agency", "Contracting Agency: Undefined"))
			          .withContents("Project Title", "", "MTEF 2010/2011", "0", "MTEF 2011/2012", "1 534 123", "MTEF 2012/2013", "206 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "888 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "888 000", "Totals-Actual Disbursements", "80 000", "Totals-MTEF", "1 740 123", "Contracting Agency", "Contracting Agency: Undefined")
			          .withChildren(
			            new ReportAreaForTests(new AreaOwner(25), "Project Title", "mtef activity 1", "MTEF 2011/2012", "789 123", "Totals-MTEF", "789 123"),
			            new ReportAreaForTests(new AreaOwner(70), "Project Title", "Activity with both MTEFs and Act.Comms", "MTEF 2011/2012", "700 000", "MTEF 2012/2013", "150 000", "Funding-2015-Actual Commitments", "888 000", "Totals-Actual Commitments", "888 000", "Totals-MTEF", "850 000"),
			            new ReportAreaForTests(new AreaOwner(78), "Project Title", "activity with many MTEFs", "MTEF 2011/2012", "45 000", "MTEF 2012/2013", "56 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Disbursements", "80 000", "Totals-MTEF", "101 000")          )        )      ));

		runNiTestCase(cor, spec("AMP-22422-test-mtefs-hiers"), Arrays.asList("Pure MTEF Project", "activity with directed MTEFs", "Activity with both MTEFs and Act.Comms", "activity with many MTEFs", "mtef activity 1", "Test MTEF directed", "Eth Water"));
	}
	
	@Test
	public void testProjectTitleLanguages() {
		NiReportModel correctReport = new NiReportModel("testcase EN")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 3))",
					"(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 1, colSpan: 2))",
					"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 1, colSpan: 1));(Actual Disbursements: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Totals-Actual Commitments", "7,181,333", "Totals-Actual Disbursements", "1,550,111")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(24), "Project Title", "Eth Water", "Totals-Actual Disbursements", "545,000"),
			        new ReportAreaForTests(new AreaOwner(30), "Project Title", "SSC Project 1", "Totals-Actual Commitments", "111,333", "Totals-Actual Disbursements", "555,111"),
			        new ReportAreaForTests(new AreaOwner(48), "Project Title", "pledged 2", "Totals-Actual Commitments", "7,070,000", "Totals-Actual Disbursements", "450,000")      ));
		
		runNiTestCase(
				this.buildSpecification("testcase EN", 
						Arrays.asList(ColumnConstants.PROJECT_TITLE), 
						Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS, MeasureConstants.ACTUAL_DISBURSEMENTS), 
						null, GroupingCriteria.GROUPING_TOTALS_ONLY),						
						"en", 
						Arrays.asList("Eth Water", "SSC Project 1", "pledged 2"),
						correctReport); 
				
		NiReportModel correctReportRu = new NiReportModel("testcase RU")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 3))",
					"(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 1, colSpan: 2))",
					"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 1, colSpan: 1));(Actual Disbursements: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Totals-Actual Commitments", "7,181,333", "Totals-Actual Disbursements", "1,550,111")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(24), "Project Title", "Вода Eth", "Totals-Actual Disbursements", "545,000"),
			        new ReportAreaForTests(new AreaOwner(30), "Project Title", "Проект КЮЮ 1", "Totals-Actual Commitments", "111,333", "Totals-Actual Disbursements", "555,111"),
			        new ReportAreaForTests(new AreaOwner(48), "Project Title", "обещание 2", "Totals-Actual Commitments", "7,070,000", "Totals-Actual Disbursements", "450,000")      ));
		
		runNiTestCase(
				buildSpecification("testcase RU", 
						Arrays.asList(ColumnConstants.PROJECT_TITLE), 
						Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS, MeasureConstants.ACTUAL_DISBURSEMENTS), 
						null, GroupingCriteria.GROUPING_TOTALS_ONLY),
				"ru", 
				Arrays.asList("Eth Water", "SSC Project 1", "pledged 2"),
				correctReportRu);
	}
	
	@Test
	public void testMtefColumnsInMillions() {
		NiReportModel cor =  new NiReportModel("MTEF millions")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 4))",
					"(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(MTEF 2011/2012: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 1, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2))",
					"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(MTEF: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "MTEF 2011/2012", "1,72", "Totals-Actual Commitments", "0,89", "Totals-MTEF", "1,72")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(18), "Project Title", "Test MTEF directed", "MTEF 2011/2012", "0,15", "Totals-MTEF", "0,15"),
			        new ReportAreaForTests(new AreaOwner(19), "Project Title", "Pure MTEF Project", "MTEF 2011/2012", "0,03", "Totals-MTEF", "0,03"),
			        new ReportAreaForTests(new AreaOwner(25), "Project Title", "mtef activity 1", "MTEF 2011/2012", "0,79", "Totals-MTEF", "0,79"),
			        new ReportAreaForTests(new AreaOwner(27), "Project Title", "mtef activity 2"),
			        new ReportAreaForTests(new AreaOwner(70), "Project Title", "Activity with both MTEFs and Act.Comms", "MTEF 2011/2012", "0,7", "Totals-Actual Commitments", "0,89", "Totals-MTEF", "0,7"),
			        new ReportAreaForTests(new AreaOwner(76), "Project Title", "activity with pipeline MTEFs and act. disb"),
			        new ReportAreaForTests(new AreaOwner(78), "Project Title", "activity with many MTEFs", "MTEF 2011/2012", "0,04", "Totals-MTEF", "0,04")      ));

		ReportSpecificationImpl spec = buildSpecification("MTEF millions",
			Arrays.asList(ColumnConstants.PROJECT_TITLE, "MTEF 2011/2012"), 
			Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS), 
			null,
			GroupingCriteria.GROUPING_TOTALS_ONLY);
		
		spec.getOrCreateSettings().setUnitsOption(AmountsUnits.AMOUNTS_OPTION_MILLIONS);
		runNiTestCase(cor, spec, mtefActs);
	}
/*	
	
	@Test
	public void test_AMP_18499_should_fail_for_now() {
		// for running manually: open http://localhost:8080/aim/viewNewAdvancedReport.do~view=reset~widget=false~resetSettings=true~ampReportId=73 OR http://localhost:8080/TEMPLATE/ampTemplate/saikuui/index.html#report/open/73
		ReportAreaForTests cor = new ReportAreaForTests()
	    .withContents("Project Title", "Report Totals", "Actual Commitments", "666 777")
	    .withChildren(new ReportAreaForTests().withContents("Project Title", "ptc activity 1", "Actual Commitments", "666 777")  );
		
		runMondrianTestCase(
				buildSpecification("AMP-18499", Arrays.asList(ColumnConstants.PROJECT_TITLE), Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS), null, GroupingCriteria.GROUPING_TOTALS_ONLY),
				"en",
				Arrays.asList("Proposed Project Cost 1 - USD", "Project with documents", "ptc activity 1"),
				cor);
	}
	
	@Test
	public void test_AMP_18504_should_fail_for_now() {
		// for running manually: http://localhost:8080/aim/viewNewAdvancedReport.do~view=reset~widget=false~resetSettings=true~ampReportId=24 or http://localhost:8080/TEMPLATE/ampTemplate/saikuui/index.html#report/open/24
		
		ReportAreaForTests cor = new ReportAreaForTests()
	    .withContents("Project Title", "Report Totals", "Donor Agency", "", "2009-Actual Commitments", "100 000", "2009-Actual Disbursements", "0", "2010-Actual Commitments", "0", "2010-Actual Disbursements", "60 000", "2012-Actual Commitments", "25 000", "2012-Actual Disbursements", "12 000", "2013-Actual Commitments", "2 670 000", "2013-Actual Disbursements", "0", "2014-Actual Commitments", "4 400 000", "2014-Actual Disbursements", "450 000", "Total Measures-Actual Commitments", "7 195 000", "Total Measures-Actual Disbursements", "522 000")
	    .withChildren(
	      new ReportAreaForTests()
	          .withContents("Project Title", "date-filters-activity", "Donor Agency", "Ministry of Finance", "2009-Actual Commitments", "100 000", "2009-Actual Disbursements", "", "2010-Actual Commitments", "", "2010-Actual Disbursements", "60 000", "2012-Actual Commitments", "25 000", "2012-Actual Disbursements", "12 000", "2013-Actual Commitments", "", "2013-Actual Disbursements", "", "2014-Actual Commitments", "", "2014-Actual Disbursements", "", "Total Measures-Actual Commitments", "125 000", "Total Measures-Actual Disbursements", "72 000"),
	      new ReportAreaForTests()
	          .withContents("Project Title", "pledged 2", "Donor Agency", "USAID", "2009-Actual Commitments", "", "2009-Actual Disbursements", "", "2010-Actual Commitments", "", "2010-Actual Disbursements", "", "2012-Actual Commitments", "", "2012-Actual Disbursements", "", "2013-Actual Commitments", "2 670 000", "2013-Actual Disbursements", "", "2014-Actual Commitments", "4 400 000", "2014-Actual Disbursements", "450 000", "Total Measures-Actual Commitments", "7 070 000", "Total Measures-Actual Disbursements", "450 000")  );
		
		runMondrianTestCase(
				buildSpecification("AMP-18504",
						Arrays.asList(ColumnConstants.PROJECT_TITLE, ColumnConstants.DONOR_AGENCY),
						Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS, MeasureConstants.ACTUAL_DISBURSEMENTS),
						null, GroupingCriteria.GROUPING_YEARLY),
				"en",
				Arrays.asList("date-filters-activity", "pledged 2"),
				cor);
	}
*/
	
	@Test
	public void test_AMP_18509() {
		NiReportModel cor = new NiReportModel("AMP-18509")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 5, colStart: 0, colSpan: 24))",
					"(Project Title: (startRow: 1, rowSpan: 4, totalRowSpan: 4, colStart: 0, colSpan: 1));(Region: (startRow: 1, rowSpan: 4, totalRowSpan: 4, colStart: 1, colSpan: 1));(AMP ID: (startRow: 1, rowSpan: 4, totalRowSpan: 4, colStart: 2, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 4, colStart: 3, colSpan: 18));(Totals: (startRow: 1, rowSpan: 3, totalRowSpan: 4, colStart: 21, colSpan: 3))",
					"(2009: (startRow: 2, rowSpan: 1, totalRowSpan: 3, colStart: 3, colSpan: 3));(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 3, colStart: 6, colSpan: 3));(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 3, colStart: 9, colSpan: 6));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 3, colStart: 15, colSpan: 3));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 3, colStart: 18, colSpan: 3))",
					"(Q1: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 3));(Q2: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 3));(Q3: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 9, colSpan: 3));(Q4: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 12, colSpan: 3));(Q4: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 15, colSpan: 3));(Q2: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 18, colSpan: 3))",
					"(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 15, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 16, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 17, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 18, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 19, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 20, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 21, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 22, colSpan: 1));(Actual Expenditures: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 23, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Region", "", "AMP ID", "", "Funding-2009-Q1-Actual Commitments", "100,000", "Funding-2009-Q1-Actual Disbursements", "0", "Funding-2009-Q1-Actual Expenditures", "0", "Funding-2010-Q2-Actual Commitments", "0", "Funding-2010-Q2-Actual Disbursements", "60,000", "Funding-2010-Q2-Actual Expenditures", "0", "Funding-2012-Q3-Actual Commitments", "25,000", "Funding-2012-Q3-Actual Disbursements", "0", "Funding-2012-Q3-Actual Expenditures", "0", "Funding-2012-Q4-Actual Commitments", "0", "Funding-2012-Q4-Actual Disbursements", "12,000", "Funding-2012-Q4-Actual Expenditures", "0", "Funding-2013-Q4-Actual Commitments", "2,670,000", "Funding-2013-Q4-Actual Disbursements", "0", "Funding-2013-Q4-Actual Expenditures", "0", "Funding-2014-Q2-Actual Commitments", "4,400,000", "Funding-2014-Q2-Actual Disbursements", "450,000", "Funding-2014-Q2-Actual Expenditures", "0", "Totals-Actual Commitments", "7,195,000", "Totals-Actual Disbursements", "522,000", "Totals-Actual Expenditures", "0")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(26), "Project Title", "date-filters-activity", "Region", "", "AMP ID", "872113null", "Funding-2009-Q1-Actual Commitments", "100,000", "Funding-2010-Q2-Actual Disbursements", "60,000", "Funding-2012-Q3-Actual Commitments", "25,000", "Funding-2012-Q4-Actual Disbursements", "12,000", "Totals-Actual Commitments", "125,000", "Totals-Actual Disbursements", "72,000"),
			        new ReportAreaForTests(new AreaOwner(48), "Project Title", "pledged 2", "Region", "Cahul County", "AMP ID", "87211347", "Funding-2013-Q4-Actual Commitments", "2,670,000", "Funding-2014-Q2-Actual Commitments", "4,400,000", "Funding-2014-Q2-Actual Disbursements", "450,000", "Totals-Actual Commitments", "7,070,000", "Totals-Actual Disbursements", "450,000")      ));
		
		runNiTestCase(
			buildSpecification("AMP-18509", 
				Arrays.asList(ColumnConstants.PROJECT_TITLE, ColumnConstants.REGION, ColumnConstants.AMP_ID),
				Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS, MeasureConstants.ACTUAL_DISBURSEMENTS, MeasureConstants.ACTUAL_EXPENDITURES),
				null,
				GroupingCriteria.GROUPING_QUARTERLY),
			"en",
			Arrays.asList("date-filters-activity", "pledged 2"),
			cor);
	}
	
	@Test
	public void test_AMP_18577_only_count_donor_transactions() {
		NiReportModel cor = new NiReportModel("AMP_18577_only_count_donor_transaction")
		.withHeaders(Arrays.asList(
				"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 4))",
				"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Region: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 1));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 3, colSpan: 1))",
				"(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1))",
				"(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1))"))
			.withWarnings(Arrays.asList())
			.withBody(      new ReportAreaForTests(null).withContents("Project Title", "", "Region", "", "Funding-2010-Actual Disbursements", "143,777", "Totals-Actual Disbursements", "143,777")
		      .withChildren(
		        new ReportAreaForTests(null, "Project Title", "Test MTEF directed", "Region", "Anenii Noi County", "Funding-2010-Actual Disbursements", "143,777", "Totals-Actual Disbursements", "143,777")      ));
		
		runNiTestCase(
				buildSpecification("AMP_18577_only_count_donor_transaction",
				Arrays.asList("Project Title", "Region"),
				Arrays.asList(MeasureConstants.ACTUAL_DISBURSEMENTS),
				null,
				GroupingCriteria.GROUPING_YEARLY),
			"en",
			Arrays.asList("Test MTEF directed"),
			cor
		);
	}
	
	@Test
	public void test_AMP_18330_empty_rows() {
		NiReportModel cor = new NiReportModel("test_AMP_18330_empty_rows")
		.withHeaders(Arrays.asList(
				"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 4))",
				"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Region: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 1));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 3, colSpan: 1))",
				"(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1))",
				"(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1))"))
			.withWarnings(Arrays.asList())
			.withBody(      new ReportAreaForTests(null)
		      .withContents("Project Title", "", "Region", "", "Funding-2010-Actual Disbursements", "143,777", "Totals-Actual Disbursements", "143,777")
		      .withChildren(
		        new ReportAreaForTests(null, "Project Title", "Test MTEF directed", "Region", "Anenii Noi County", "Funding-2010-Actual Disbursements", "143,777", "Totals-Actual Disbursements", "143,777"),
		        new ReportAreaForTests(null, "Project Title", "activity with primary_program", "Region", "")      ));
		
		ReportSpecificationImpl spec = buildSpecification("test_AMP_18330_empty_rows",
				Arrays.asList(ColumnConstants.PROJECT_TITLE, ColumnConstants.REGION),
				Arrays.asList(MeasureConstants.ACTUAL_DISBURSEMENTS),
				null,
				GroupingCriteria.GROUPING_YEARLY);
		
		spec.setDisplayEmptyFundingRows(true);
		
		runNiTestCase(spec, "en",
			Arrays.asList("Test MTEF directed", "activity with primary_program"),
			cor
		);
	}
	
	@Test
	public void test_AMP_18748_no_data() {
		NiReportModel cor = new NiReportModel("test_AMP_18748_no_data")
		.withHeaders(Arrays.asList(
				"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 3))",
				"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Region: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 2, colSpan: 1))",
				"",
				"(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))"))
			.withWarnings(Arrays.asList())
			.withBody(      new ReportAreaForTests(null).withContents("Project Title", "", "Region", "", "Totals-Actual Disbursements", "0")
		      .withChildren(      ));
		
		ReportSpecificationImpl spec = buildSpecification("test_AMP_18748_no_data",
				Arrays.asList(ColumnConstants.PROJECT_TITLE, ColumnConstants.REGION),
				Arrays.asList(MeasureConstants.ACTUAL_DISBURSEMENTS),
				null,
				GroupingCriteria.GROUPING_YEARLY);
		
		runNiTestCase(spec, "en",
				Arrays.asList("__hopefully____invalid________name____"),
			cor
		);
	}
	
	@Test
	public void test_AMP_22343_Monthly_Fiscal_Calendar() {
		// tests that the headers come out with the months sorted out in the fiscal year order
		NiReportModel cor = new NiReportModel("AMP-22343-fiscal-monthly")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 5, colStart: 0, colSpan: 9))",
					"(Project Title: (startRow: 1, rowSpan: 4, totalRowSpan: 4, colStart: 0, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 4, colStart: 1, colSpan: 6));(Totals: (startRow: 1, rowSpan: 3, totalRowSpan: 4, colStart: 7, colSpan: 2))",
					"(Fiscal Year 2013 - 2014: (startRow: 2, rowSpan: 1, totalRowSpan: 3, colStart: 1, colSpan: 6))",
					"(August: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 1, colSpan: 2));(December: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 2));(February: (startRow: 3, rowSpan: 1, totalRowSpan: 2, colStart: 5, colSpan: 2))",
					"(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 1, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Commitments: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Disbursements: (startRow: 4, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Funding-Fiscal Year 2013 - 2014-August-Actual Commitments", "111 333", "Funding-Fiscal Year 2013 - 2014-August-Actual Disbursements", "1 100 111", "Funding-Fiscal Year 2013 - 2014-December-Actual Commitments", "890 000", "Funding-Fiscal Year 2013 - 2014-December-Actual Disbursements", "0", "Funding-Fiscal Year 2013 - 2014-February-Actual Commitments", "75 000", "Funding-Fiscal Year 2013 - 2014-February-Actual Disbursements", "0", "Totals-Actual Commitments", "1 076 333", "Totals-Actual Disbursements", "1 100 111")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(24), "Project Title", "Eth Water", "Funding-Fiscal Year 2013 - 2014-August-Actual Disbursements", "545 000", "Totals-Actual Disbursements", "545 000"),
			        new ReportAreaForTests(new AreaOwner(30), "Project Title", "SSC Project 1", "Funding-Fiscal Year 2013 - 2014-August-Actual Commitments", "111 333", "Funding-Fiscal Year 2013 - 2014-August-Actual Disbursements", "555 111", "Totals-Actual Commitments", "111 333", "Totals-Actual Disbursements", "555 111"),
			        new ReportAreaForTests(new AreaOwner(36), "Project Title", "Activity With Zones and Percentages", "Funding-Fiscal Year 2013 - 2014-December-Actual Commitments", "890 000", "Totals-Actual Commitments", "890 000"),
			        new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Funding-Fiscal Year 2013 - 2014-February-Actual Commitments", "75 000", "Totals-Actual Commitments", "75 000")      ));
		
		runNiTestCase(spec("AMP-22343-fiscal-monthly"), "en",
			Arrays.asList("Eth Water", "SSC Project 1", "Activity With Zones and Percentages", "SubNational no percentages"), 
			cor);
	}
	
	@Test
	public void test_AMP_22322_directed_mtefs_as_plain_mtefs_columns() {
		NiReportModel corPlain = new NiReportModel("AMP-22322-directed-mtefs")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 14))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(MTEF 2011/2012: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(MTEF 2012/2013: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 2, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 3, colSpan: 8));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 11, colSpan: 3))",
					"(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 5, colSpan: 2));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 7, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 9, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(MTEF: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1))"))
				.withWarnings(Arrays.asList(
					"-1: [entityId: -1, message: measure Real MTEFs not supported in NiReports]"))
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "MTEF 2011/2012", "1 718 011", "MTEF 2012/2013", "271 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "75 000", "Funding-2015-Actual Commitments", "888 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "888 000", "Totals-Actual Disbursements", "333 777", "Totals-MTEF", "1 989 011")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(18), "Project Title", "Test MTEF directed", "MTEF 2011/2012", "150 000", "MTEF 2012/2013", "65 000", "Funding-2010-Actual Disbursements", "143 777", "Totals-Actual Disbursements", "143 777", "Totals-MTEF", "215 000"),
			        new ReportAreaForTests(new AreaOwner(19), "Project Title", "Pure MTEF Project", "MTEF 2011/2012", "33 888", "Totals-MTEF", "33 888"),
			        new ReportAreaForTests(new AreaOwner(25), "Project Title", "mtef activity 1", "MTEF 2011/2012", "789 123", "Totals-MTEF", "789 123"),
			        new ReportAreaForTests(new AreaOwner(27), "Project Title", "mtef activity 2"),
			        new ReportAreaForTests(new AreaOwner(70), "Project Title", "Activity with both MTEFs and Act.Comms", "MTEF 2011/2012", "700 000", "MTEF 2012/2013", "150 000", "Funding-2015-Actual Commitments", "888 000", "Totals-Actual Commitments", "888 000", "Totals-MTEF", "850 000"),
			        new ReportAreaForTests(new AreaOwner(76), "Project Title", "activity with pipeline MTEFs and act. disb", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Disbursements", "75 000", "Totals-Actual Disbursements", "110 000"),
			        new ReportAreaForTests(new AreaOwner(78), "Project Title", "activity with many MTEFs", "MTEF 2011/2012", "45 000", "MTEF 2012/2013", "56 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Disbursements", "80 000", "Totals-MTEF", "101 000")      ));

		NiReportModel corByBenf = new NiReportModel("AMP-22322-directed-mtefs-by-beneficiary")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 15))",
					"(Beneficiary Agency: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(MTEF 2011/2012: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 2, colSpan: 1));(MTEF 2012/2013: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 3, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 4, colSpan: 8));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 12, colSpan: 3))",
					"(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 2));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 8, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 10, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(MTEF: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1))"))
				.withWarnings(Arrays.asList(
					"-1: [entityId: -1, message: measure Real MTEFs not supported in NiReports]"))
				.withBody(      new ReportAreaForTests(null).withContents("Beneficiary Agency", "", "Project Title", "", "MTEF 2011/2012", "1 718 011", "MTEF 2012/2013", "271 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "75 000", "Funding-2015-Actual Commitments", "888 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "888 000", "Totals-Actual Disbursements", "333 777", "Totals-MTEF", "1 989 011")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Beneficiary Agency", "Beneficiary Agency: Undefined"))
			        .withContents("Project Title", "", "MTEF 2011/2012", "1 718 011", "MTEF 2012/2013", "271 000", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "75 000", "Funding-2015-Actual Commitments", "888 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "888 000", "Totals-Actual Disbursements", "333 777", "Totals-MTEF", "1 989 011", "Beneficiary Agency", "Beneficiary Agency: Undefined")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(18), "Project Title", "Test MTEF directed", "MTEF 2011/2012", "150 000", "MTEF 2012/2013", "65 000", "Funding-2010-Actual Disbursements", "143 777", "Totals-Actual Disbursements", "143 777", "Totals-MTEF", "215 000"),
			          new ReportAreaForTests(new AreaOwner(19), "Project Title", "Pure MTEF Project", "MTEF 2011/2012", "33 888", "Totals-MTEF", "33 888"),
			          new ReportAreaForTests(new AreaOwner(25), "Project Title", "mtef activity 1", "MTEF 2011/2012", "789 123", "Totals-MTEF", "789 123"),
			          new ReportAreaForTests(new AreaOwner(70), "Project Title", "Activity with both MTEFs and Act.Comms", "MTEF 2011/2012", "700 000", "MTEF 2012/2013", "150 000", "Funding-2015-Actual Commitments", "888 000", "Totals-Actual Commitments", "888 000", "Totals-MTEF", "850 000"),
			          new ReportAreaForTests(new AreaOwner(76), "Project Title", "activity with pipeline MTEFs and act. disb", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Disbursements", "75 000", "Totals-Actual Disbursements", "110 000"),
			          new ReportAreaForTests(new AreaOwner(78), "Project Title", "activity with many MTEFs", "MTEF 2011/2012", "45 000", "MTEF 2012/2013", "56 000", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Disbursements", "80 000", "Totals-MTEF", "101 000")        )      ));
		
		runNiTestCase(spec("AMP-22322-directed-mtefs"), "en", mtefActs, corPlain);
		runNiTestCase(spec("AMP-22322-directed-mtefs-by-beneficiary"), "en", mtefActs, corByBenf);
	}
	
	@Test
	public void testProposedProjectCost() {
		NiReportModel corPPCUSD = new NiReportModel("Proposed-cost-USD")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 8))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Proposed Project Amount: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 4));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 6, colSpan: 2))",
					"(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Proposed Project Amount", "4 630 902,72", "Funding-2014-Actual Commitments", "172 000", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "580 245", "Funding-2015-Actual Disbursements", "321 765", "Totals-Actual Commitments", "752 245", "Totals-Actual Disbursements", "321 765")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(15), "Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Amount", "1 000 000"),
			        new ReportAreaForTests(new AreaOwner(17), "Project Title", "Proposed Project Cost 2 - EUR", "Proposed Project Amount", "3 399 510,47"),
			        new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Proposed Project Amount", "60 000", "Funding-2014-Actual Commitments", "75 000", "Totals-Actual Commitments", "75 000"),
			        new ReportAreaForTests(new AreaOwner(43), "Project Title", "Activity with primary_tertiary_program", "Proposed Project Amount", "66 392,25", "Funding-2014-Actual Commitments", "50 000", "Totals-Actual Commitments", "50 000"),
			        new ReportAreaForTests(new AreaOwner(44), "Project Title", "activity with primary_program", "Proposed Project Amount", "35 000", "Funding-2014-Actual Commitments", "32 000", "Totals-Actual Commitments", "32 000"),
			        new ReportAreaForTests(new AreaOwner(45), "Project Title", "activity with tertiary_program", "Proposed Project Amount", "70 000", "Funding-2014-Actual Commitments", "15 000", "Totals-Actual Commitments", "15 000"),
			        new ReportAreaForTests(new AreaOwner(65), "Project Title", "activity 1 with agreement", "Funding-2015-Actual Commitments", "456 789", "Funding-2015-Actual Disbursements", "321 765", "Totals-Actual Commitments", "456 789", "Totals-Actual Disbursements", "321 765"),
			        new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "123 456", "Totals-Actual Commitments", "123 456")      ));

		runNiTestCase(spec("Proposed-cost-USD"), "en", ppcActs, corPPCUSD);
		
		NiReportModel corPPCEUR = new NiReportModel("Proposed-cost-EUR")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 8))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Proposed Project Amount: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 4));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 6, colSpan: 2))",
					"(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Proposed Project Amount", "3 444 862", "Funding-2014-Actual Commitments", "129 533", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "529 416", "Funding-2015-Actual Disbursements", "293 578", "Totals-Actual Commitments", "658 949", "Totals-Actual Disbursements", "293 578")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(15), "Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Amount", "770 600"),
			        new ReportAreaForTests(new AreaOwner(17), "Project Title", "Proposed Project Cost 2 - EUR", "Proposed Project Amount", "2 500 000"),
			        new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Proposed Project Amount", "45 186", "Funding-2014-Actual Commitments", "56 482", "Totals-Actual Commitments", "56 482"),
			        new ReportAreaForTests(new AreaOwner(43), "Project Title", "Activity with primary_tertiary_program", "Proposed Project Amount", "50 000", "Funding-2014-Actual Commitments", "37 655", "Totals-Actual Commitments", "37 655"),
			        new ReportAreaForTests(new AreaOwner(44), "Project Title", "activity with primary_program", "Proposed Project Amount", "26 358", "Funding-2014-Actual Commitments", "24 099", "Totals-Actual Commitments", "24 099"),
			        new ReportAreaForTests(new AreaOwner(45), "Project Title", "activity with tertiary_program", "Proposed Project Amount", "52 717", "Funding-2014-Actual Commitments", "11 296", "Totals-Actual Commitments", "11 296"),
			        new ReportAreaForTests(new AreaOwner(65), "Project Title", "activity 1 with agreement", "Funding-2015-Actual Commitments", "416 774", "Funding-2015-Actual Disbursements", "293 578", "Totals-Actual Commitments", "416 774", "Totals-Actual Disbursements", "293 578"),
			        new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "112 641", "Totals-Actual Commitments", "112 641")      ));

		runNiTestCase(spec("Proposed-cost-EUR"), "en", ppcActs, corPPCEUR);
	}
	
	@Test
	public void testProposedProjectCostMillions() {
		NiReportModel cor =  new NiReportModel("PPCMillions")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 3))",
					"(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Proposed Project Amount: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 1, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1))",
					"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Proposed Project Amount", "4,63", "Totals-Actual Commitments", "0,75")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(15), "Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Amount", "1"),
			        new ReportAreaForTests(new AreaOwner(17), "Project Title", "Proposed Project Cost 2 - EUR", "Proposed Project Amount", "3,4"),
			        new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Proposed Project Amount", "0,06", "Totals-Actual Commitments", "0,08"),
			        new ReportAreaForTests(new AreaOwner(43), "Project Title", "Activity with primary_tertiary_program", "Proposed Project Amount", "0,07", "Totals-Actual Commitments", "0,05"),
			        new ReportAreaForTests(new AreaOwner(44), "Project Title", "activity with primary_program", "Proposed Project Amount", "0,04", "Totals-Actual Commitments", "0,03"),
			        new ReportAreaForTests(new AreaOwner(45), "Project Title", "activity with tertiary_program", "Proposed Project Amount", "0,07", "Totals-Actual Commitments", "0,02"),
			        new ReportAreaForTests(new AreaOwner(65), "Project Title", "activity 1 with agreement", "Totals-Actual Commitments", "0,46"),
			        new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Totals-Actual Commitments", "0,12")      ));
		
		ReportSpecificationImpl spec = buildSpecification("PPCMillions", 
			Arrays.asList(ColumnConstants.PROJECT_TITLE, ColumnConstants.PROPOSED_PROJECT_AMOUNT),
			Arrays.asList(MeasureConstants.ACTUAL_COMMITMENTS), 
			null,
			GroupingCriteria.GROUPING_TOTALS_ONLY);
		spec.getOrCreateSettings().setUnitsOption(AmountsUnits.AMOUNTS_OPTION_MILLIONS);
		
		runNiTestCase(spec, "en", ppcActs, cor);
	}
	
	@Test
	public void testAnnualProposedProjectCost() {
		NiReportModel corAnnualPPC = new NiReportModel("Annual-Proposed-Project-Cost")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 12))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Proposed Project Amount: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 8));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 10, colSpan: 2))",
					"(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 8, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Annual Proposed Project Cost: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Annual Proposed Project Cost: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Annual Proposed Project Cost: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Annual Proposed Project Cost: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Annual Proposed Project Cost: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Proposed Project Amount", "4 630 902,72", "Funding-2012-Actual Commitments", "0", "Funding-2012-Annual Proposed Project Cost", "350 000", "Funding-2013-Actual Commitments", "0", "Funding-2013-Annual Proposed Project Cost", "726 072,61", "Funding-2014-Actual Commitments", "172 000", "Funding-2014-Annual Proposed Project Cost", "3 382 784,49", "Funding-2015-Actual Commitments", "580 245", "Funding-2015-Annual Proposed Project Cost", "0", "Totals-Actual Commitments", "752 245", "Totals-Annual Proposed Project Cost", "4 458 857,1")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(15), "Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Amount", "1 000 000", "Funding-2012-Annual Proposed Project Cost", "350 000", "Funding-2013-Annual Proposed Project Cost", "726 072,61", "Funding-2014-Annual Proposed Project Cost", "132 784,49", "Totals-Annual Proposed Project Cost", "1 208 857,1"),
			        new ReportAreaForTests(new AreaOwner(17), "Project Title", "Proposed Project Cost 2 - EUR", "Proposed Project Amount", "3 399 510,47", "Funding-2014-Annual Proposed Project Cost", "3 250 000", "Totals-Annual Proposed Project Cost", "3 250 000"),
			        new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Proposed Project Amount", "60 000", "Funding-2014-Actual Commitments", "75 000", "Totals-Actual Commitments", "75 000"),
			        new ReportAreaForTests(new AreaOwner(43), "Project Title", "Activity with primary_tertiary_program", "Proposed Project Amount", "66 392,25", "Funding-2014-Actual Commitments", "50 000", "Totals-Actual Commitments", "50 000"),
			        new ReportAreaForTests(new AreaOwner(44), "Project Title", "activity with primary_program", "Proposed Project Amount", "35 000", "Funding-2014-Actual Commitments", "32 000", "Totals-Actual Commitments", "32 000"),
			        new ReportAreaForTests(new AreaOwner(45), "Project Title", "activity with tertiary_program", "Proposed Project Amount", "70 000", "Funding-2014-Actual Commitments", "15 000", "Totals-Actual Commitments", "15 000"),
			        new ReportAreaForTests(new AreaOwner(65), "Project Title", "activity 1 with agreement", "Funding-2015-Actual Commitments", "456 789", "Totals-Actual Commitments", "456 789"),
			        new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "123 456", "Totals-Actual Commitments", "123 456")      ));

		runNiTestCase(spec("Annual-Proposed-Project-Cost"), "en", ppcActs, corAnnualPPC);
	}
	
	@Test
	public void testRevisedProjectCost() {
		NiReportModel cor =  new NiReportModel("AMP-22375-revised-project-cost")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 9))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Proposed Project Amount: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Revised Project Amount: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 2, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 3, colSpan: 4));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 7, colSpan: 2))",
					"(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 5, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Proposed Project Amount", "4 630 902,72", "Revised Project Amount", "4 412 539,84", "Funding-2014-Actual Commitments", "172 000", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "580 245", "Funding-2015-Actual Disbursements", "321 765", "Totals-Actual Commitments", "752 245", "Totals-Actual Disbursements", "321 765")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(15), "Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Amount", "1 000 000", "Revised Project Amount", "1 217 000"),
			        new ReportAreaForTests(new AreaOwner(17), "Project Title", "Proposed Project Cost 2 - EUR", "Proposed Project Amount", "3 399 510,47", "Revised Project Amount", "3 195 539,84"),
			        new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Proposed Project Amount", "60 000", "Funding-2014-Actual Commitments", "75 000", "Totals-Actual Commitments", "75 000"),
			        new ReportAreaForTests(new AreaOwner(43), "Project Title", "Activity with primary_tertiary_program", "Proposed Project Amount", "66 392,25", "Funding-2014-Actual Commitments", "50 000", "Totals-Actual Commitments", "50 000"),
			        new ReportAreaForTests(new AreaOwner(44), "Project Title", "activity with primary_program", "Proposed Project Amount", "35 000", "Funding-2014-Actual Commitments", "32 000", "Totals-Actual Commitments", "32 000"),
			        new ReportAreaForTests(new AreaOwner(45), "Project Title", "activity with tertiary_program", "Proposed Project Amount", "70 000", "Funding-2014-Actual Commitments", "15 000", "Totals-Actual Commitments", "15 000"),
			        new ReportAreaForTests(new AreaOwner(65), "Project Title", "activity 1 with agreement", "Funding-2015-Actual Commitments", "456 789", "Funding-2015-Actual Disbursements", "321 765", "Totals-Actual Commitments", "456 789", "Totals-Actual Disbursements", "321 765"),
			        new ReportAreaForTests(new AreaOwner(73), "Project Title", "activity with directed MTEFs", "Funding-2015-Actual Commitments", "123 456", "Totals-Actual Commitments", "123 456")));

		runNiTestCase(spec("AMP-22375-revised-project-cost"), "en", ppcActs, cor);
	}
	
	@Test
	public void testHierByHumanitarianAid() {
		NiReportModel cor = new NiReportModel("AMP-20325-by-humanitarian-aid")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 18))",
					"(Humanitarian Aid: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 14));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 16, colSpan: 2))",
					"(2009: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2));(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2));(2011: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 2));(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 8, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 10, colSpan: 2));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 12, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 14, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 15, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 16, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 17, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Humanitarian Aid", "", "Project Title", "", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "636 534", "Funding-2011-Actual Commitments", "1 213 119", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "3 003 333", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "4 400 000", "Funding-2014-Actual Disbursements", "450 200", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "570", "Totals-Actual Commitments", "8 741 452", "Totals-Actual Disbursements", "1 099 304")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Humanitarian Aid", "No"))
			        .withContents("Project Title", "", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "60 000", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "200", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "570", "Totals-Actual Commitments", "125 000", "Totals-Actual Disbursements", "72 770", "Humanitarian Aid", "No")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(26), "Project Title", "date-filters-activity", "Funding-2009-Actual Commitments", "100 000", "Funding-2010-Actual Disbursements", "60 000", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Totals-Actual Commitments", "125 000", "Totals-Actual Disbursements", "72 000"),
			          new ReportAreaForTests(new AreaOwner(69), "Project Title", "Activity with planned disbursements", "Funding-2014-Actual Disbursements", "200", "Funding-2015-Actual Disbursements", "570", "Totals-Actual Disbursements", "770")        ),
			        new ReportAreaForTests(new AreaOwner("Humanitarian Aid", "Yes"))
			        .withContents("Project Title", "", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "123 321", "Funding-2011-Actual Commitments", "213 231", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "333 333", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "546 564", "Totals-Actual Disbursements", "123 321", "Humanitarian Aid", "Yes")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(12), "Project Title", "TAC_activity_1", "Funding-2010-Actual Disbursements", "123 321", "Funding-2011-Actual Commitments", "213 231", "Totals-Actual Commitments", "213 231", "Totals-Actual Disbursements", "123 321"),
			          new ReportAreaForTests(new AreaOwner(32), "Project Title", "crazy funding 1", "Funding-2013-Actual Commitments", "333 333", "Totals-Actual Commitments", "333 333")        ),
			        new ReportAreaForTests(new AreaOwner("Humanitarian Aid", "Humanitarian Aid: Undefined"))
			        .withContents("Project Title", "", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "453 213", "Funding-2011-Actual Commitments", "999 888", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "4 400 000", "Funding-2014-Actual Disbursements", "450 000", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "8 069 888", "Totals-Actual Disbursements", "903 213", "Humanitarian Aid", "Humanitarian Aid: Undefined")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(13), "Project Title", "TAC_activity_2", "Funding-2010-Actual Disbursements", "453 213", "Funding-2011-Actual Commitments", "999 888", "Totals-Actual Commitments", "999 888", "Totals-Actual Disbursements", "453 213"),
			          new ReportAreaForTests(new AreaOwner(48), "Project Title", "pledged 2", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2014-Actual Commitments", "4 400 000", "Funding-2014-Actual Disbursements", "450 000", "Totals-Actual Commitments", "7 070 000", "Totals-Actual Disbursements", "450 000")        )      ));

		
		runNiTestCase(spec("AMP-20325-by-humanitarian-aid"), "en", 
			humanitarianAidActs, cor);
	}
	
	@Test
	public void testHierByDisasterResponseMarker() {
		NiReportModel cor = new NiReportModel("AMP-20980-disaster-response-marker-hier")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 8))",
					"(Disaster Response Marker: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 5));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 7, colSpan: 1))",
					"(2009: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1));(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 1));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 1));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 5, colSpan: 1));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 1))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Disaster Response Marker", "", "Project Title", "", "Funding-2009-Actual Commitments", "100 000", "Funding-2012-Actual Commitments", "25 000", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2014-Actual Commitments", "4 433 000", "Funding-2015-Actual Commitments", "117 000", "Totals-Actual Commitments", "7 345 000")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Disaster Response Marker", "No")).withContents("Project Title", "", "Funding-2009-Actual Commitments", "0", "Funding-2012-Actual Commitments", "0", "Funding-2013-Actual Commitments", "0", "Funding-2014-Actual Commitments", "33 000", "Funding-2015-Actual Commitments", "0", "Totals-Actual Commitments", "33 000", "Disaster Response Marker", "No")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(71), "Project Title", "activity_with_disaster_response", "Funding-2014-Actual Commitments", "33 000", "Totals-Actual Commitments", "33 000")        ),
			        new ReportAreaForTests(new AreaOwner("Disaster Response Marker", "Yes")).withContents("Project Title", "", "Funding-2009-Actual Commitments", "0", "Funding-2012-Actual Commitments", "0", "Funding-2013-Actual Commitments", "0", "Funding-2014-Actual Commitments", "0", "Funding-2015-Actual Commitments", "67 000", "Totals-Actual Commitments", "67 000", "Disaster Response Marker", "Yes")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(71), "Project Title", "activity_with_disaster_response", "Funding-2015-Actual Commitments", "67 000", "Totals-Actual Commitments", "67 000")        ),
			        new ReportAreaForTests(new AreaOwner("Disaster Response Marker", "Disaster Response Marker: Undefined"))
			        .withContents("Project Title", "", "Funding-2009-Actual Commitments", "100 000", "Funding-2012-Actual Commitments", "25 000", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2014-Actual Commitments", "4 400 000", "Funding-2015-Actual Commitments", "50 000", "Totals-Actual Commitments", "7 245 000", "Disaster Response Marker", "Disaster Response Marker: Undefined")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(26), "Project Title", "date-filters-activity", "Funding-2009-Actual Commitments", "100 000", "Funding-2012-Actual Commitments", "25 000", "Totals-Actual Commitments", "125 000"),
			          new ReportAreaForTests(new AreaOwner(48), "Project Title", "pledged 2", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2014-Actual Commitments", "4 400 000", "Totals-Actual Commitments", "7 070 000"),
			          new ReportAreaForTests(new AreaOwner(71), "Project Title", "activity_with_disaster_response", "Funding-2015-Actual Commitments", "50 000", "Totals-Actual Commitments", "50 000")        )      ));

		
		runNiTestCase(spec("AMP-20980-disaster-response-marker-hier"), "en",
			Arrays.asList("activity_with_disaster_response", "date-filters-activity", "pledged 2"), 
			cor);
	}
	
	@Test
	public void testPlainDisasterResponseMarker() {
		NiReportModel cor = new NiReportModel("AMP-20980-disaster-response-marker")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 8))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Disaster Response Marker: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 5));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 7, colSpan: 1))",
					"(2009: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1));(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 1));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 1));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 5, colSpan: 1));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 1))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Disaster Response Marker", "", "Funding-2009-Actual Commitments", "100 000", "Funding-2012-Actual Commitments", "25 000", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2014-Actual Commitments", "4 433 000", "Funding-2015-Actual Commitments", "117 000", "Totals-Actual Commitments", "7 345 000")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(26), "Project Title", "date-filters-activity", "Funding-2009-Actual Commitments", "100 000", "Funding-2012-Actual Commitments", "25 000", "Totals-Actual Commitments", "125 000"),
			        new ReportAreaForTests(new AreaOwner(48), "Project Title", "pledged 2", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2014-Actual Commitments", "4 400 000", "Totals-Actual Commitments", "7 070 000"),
			        new ReportAreaForTests(new AreaOwner(71), "Project Title", "activity_with_disaster_response", "Disaster Response Marker", "No, Yes", "Funding-2014-Actual Commitments", "33 000", "Funding-2015-Actual Commitments", "117 000", "Totals-Actual Commitments", "150 000")      ));
		
		runNiTestCase(spec("AMP-20980-disaster-response-marker"), "en",
			Arrays.asList("activity_with_disaster_response", "date-filters-activity", "pledged 2"), 
			cor);
	}
	
	@Test
	public void testActivityCountNonSummary() {
		NiReportModel cor = new NiReportModel("ActivityCountReport")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 3, colStart: 0, colSpan: 6))",
					"(Region: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1));(Project Title: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 1, colSpan: 1));(Activity Count: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 2, colSpan: 1));(Zone: (startRow: 1, rowSpan: 2, totalRowSpan: 2, colStart: 3, colSpan: 1));(Totals: (startRow: 1, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2))",
					"(Actual Commitments: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements: (startRow: 2, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Region", "", "Project Title", "", "Activity Count", "12", "Zone", "", "Totals-Actual Commitments", "6 678 792,63", "Totals-Actual Disbursements", "1 433 888")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Region", "Anenii Noi County", 9085))
			        .withContents("Project Title", "", "Activity Count", "8", "Zone", "", "Totals-Actual Commitments", "1 611 832", "Totals-Actual Disbursements", "1 243 888", "Region", "Anenii Noi County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(18), "Project Title", "Test MTEF directed", "Zone", "", "Totals-Actual Disbursements", "143 777"),
			          new ReportAreaForTests(new AreaOwner(24), "Project Title", "Eth Water", "Zone", "", "Totals-Actual Disbursements", "545 000"),
			          new ReportAreaForTests(new AreaOwner(28), "Project Title", "ptc activity 1", "Zone", "", "Totals-Actual Commitments", "666 777"),
			          new ReportAreaForTests(new AreaOwner(29), "Project Title", "ptc activity 2", "Zone", "", "Totals-Actual Commitments", "333 222"),
			          new ReportAreaForTests(new AreaOwner(30), "Project Title", "SSC Project 1", "Zone", "", "Totals-Actual Commitments", "111 333", "Totals-Actual Disbursements", "555 111"),
			          new ReportAreaForTests(new AreaOwner(33), "Project Title", "Activity with Zones", "Zone", "Bulboaca", "Totals-Actual Commitments", "285 000"),
			          new ReportAreaForTests(new AreaOwner(36), "Project Title", "Activity With Zones and Percentages", "Zone", "Dolboaca", "Totals-Actual Commitments", "178 000"),
			          new ReportAreaForTests(new AreaOwner(40), "Project Title", "SubNational no percentages", "Zone", "", "Totals-Actual Commitments", "37 500")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Chisinau County", 9089))
			        .withContents("Project Title", "", "Activity Count", "4", "Zone", "", "Totals-Actual Commitments", "5 066 960,63", "Totals-Actual Disbursements", "190 000", "Region", "Chisinau County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner(46), "Project Title", "pledged education activity 1", "Zone", "", "Totals-Actual Commitments", "5 000 000"),
			          new ReportAreaForTests(new AreaOwner(50), "Project Title", "activity with capital spending", "Zone", "", "Totals-Actual Commitments", "65 760,63", "Totals-Actual Disbursements", "80 000"),
			          new ReportAreaForTests(new AreaOwner(66), "Project Title", "Activity 2 with multiple agreements", "Zone", "", "Totals-Actual Commitments", "1 200"),
			          new ReportAreaForTests(new AreaOwner(76), "Project Title", "activity with pipeline MTEFs and act. disb", "Zone", "", "Totals-Actual Disbursements", "110 000")        )      ));
		
		runNiTestCase(spec("ActivityCountReport"), "en", acts, cor);
	}
	
	@Test
	public void testActivityCountUnarySummary() {
		NiReportModel cor = new NiReportModel("activity-count-summary")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 20))",
					"(Primary Sector: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Activity Count: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 2, colSpan: 16));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 18, colSpan: 2))",
					"(2006: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 2));(2009: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 4, colSpan: 2));(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 6, colSpan: 2));(2011: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 8, colSpan: 2));(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 10, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 12, colSpan: 2));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 14, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 16, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 15, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 16, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 17, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 18, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 19, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Primary Sector", "", "Activity Count", "37", "Funding-2006-Actual Commitments", "96 840,58", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "780 311", "Funding-2011-Actual Commitments", "1 213 119", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "7 842 086", "Funding-2013-Actual Disbursements", "1 266 956", "Funding-2014-Actual Commitments", "8 159 813,77", "Funding-2014-Actual Disbursements", "710 200", "Funding-2015-Actual Commitments", "1 971 831,84", "Funding-2015-Actual Disbursements", "437 335", "Totals-Actual Commitments", "19 408 691,19", "Totals-Actual Disbursements", "3 206 802")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "31", "Funding-2006-Actual Commitments", "58 104,35", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "203 777", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "3 981 665", "Funding-2013-Actual Disbursements", "1 135 111", "Funding-2014-Actual Commitments", "3 734 250,55", "Funding-2014-Actual Disbursements", "240 000", "Funding-2015-Actual Commitments", "1 332 044,26", "Funding-2015-Actual Disbursements", "275 882,5", "Totals-Actual Commitments", "9 231 064,16", "Totals-Actual Disbursements", "1 866 770,5", "Primary Sector", "110 - EDUCATION"),
			        new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "7", "Funding-2006-Actual Commitments", "9 684,06", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "123 321", "Funding-2011-Actual Commitments", "213 231", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "567 421", "Funding-2013-Actual Disbursements", "131 845", "Funding-2014-Actual Commitments", "363,21", "Funding-2014-Actual Disbursements", "5 200", "Funding-2015-Actual Commitments", "592 987,58", "Funding-2015-Actual Disbursements", "161 452,5", "Totals-Actual Commitments", "1 383 686,86", "Totals-Actual Disbursements", "421 818,5", "Primary Sector", "112 - BASIC EDUCATION"),
			        new ReportAreaForTests(new AreaOwner("Primary Sector", "113 - SECONDARY EDUCATION", 6246), "Activity Count", "2", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "4 413 200", "Funding-2014-Actual Disbursements", "450 000", "Funding-2015-Actual Commitments", "46 800", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "7 130 000", "Totals-Actual Disbursements", "450 000", "Primary Sector", "113 - SECONDARY EDUCATION"),
			        new ReportAreaForTests(new AreaOwner("Primary Sector", "120 - HEALTH", 6252), "Activity Count", "2", "Funding-2006-Actual Commitments", "29 052,17", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "623 000", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "15 000", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "652 052,17", "Totals-Actual Disbursements", "15 000", "Primary Sector", "120 - HEALTH"),
			        new ReportAreaForTests(new AreaOwner("Primary Sector", "130 - POPULATION POLICIES/PROGRAMMES AND REPRODUCTIVE HEALTH", 6267), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "453 213", "Funding-2011-Actual Commitments", "999 888", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "999 888", "Totals-Actual Disbursements", "453 213", "Primary Sector", "130 - POPULATION POLICIES/PROGRAMMES AND REPRODUCTIVE HEALTH"),
			        new ReportAreaForTests(new AreaOwner("Primary Sector", "Primary Sector: Undefined", -999999999), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "12 000", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "12 000", "Totals-Actual Disbursements", "0", "Primary Sector", "Primary Sector: Undefined")      ));
		
		runNiTestCase(spec("activity-count-summary"), "en", acts, cor);
	}
	
	@Test
	public void testActivityCountDoubleSummary() {
		NiReportModel cor = new NiReportModel("activity-count-summary-dual")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 21))",
					"(Region: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Primary Sector: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 1, colSpan: 1));(Activity Count: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 2, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 3, colSpan: 16));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 19, colSpan: 2))",
					"(2006: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 3, colSpan: 2));(2009: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 5, colSpan: 2));(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 7, colSpan: 2));(2011: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 9, colSpan: 2));(2012: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 11, colSpan: 2));(2013: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 13, colSpan: 2));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 15, colSpan: 2));(2015: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 17, colSpan: 2))",
					"(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 15, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 16, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 17, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 18, colSpan: 1));(Actual Commitments: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 19, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 20, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Region", "", "Primary Sector", "", "Activity Count", "37", "Funding-2006-Actual Commitments", "96 840,58", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "780 311", "Funding-2011-Actual Commitments", "1 213 119", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "7 842 086", "Funding-2013-Actual Disbursements", "1 266 956", "Funding-2014-Actual Commitments", "8 159 813,77", "Funding-2014-Actual Disbursements", "710 200", "Funding-2015-Actual Commitments", "1 971 831,84", "Funding-2015-Actual Disbursements", "437 335", "Totals-Actual Commitments", "19 408 691,19", "Totals-Actual Disbursements", "3 206 802")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner("Region", "Anenii Noi County", 9085))
			        .withContents("Primary Sector", "", "Activity Count", "8", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "1 574 332", "Funding-2013-Actual Disbursements", "1 100 111", "Funding-2014-Actual Commitments", "37 500", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "1 611 832", "Totals-Actual Disbursements", "1 243 888", "Region", "Anenii Noi County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "8", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "143 777", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "1 449 732", "Funding-2013-Actual Disbursements", "1 100 111", "Funding-2014-Actual Commitments", "37 500", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "1 487 232", "Totals-Actual Disbursements", "1 243 888", "Primary Sector", "110 - EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "120 - HEALTH", 6252), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "124 600", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "124 600", "Totals-Actual Disbursements", "0", "Primary Sector", "120 - HEALTH")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Balti County", 9086))
			        .withContents("Primary Sector", "", "Activity Count", "7", "Funding-2006-Actual Commitments", "53 262,32", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "1 330 333", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "37 500", "Funding-2014-Actual Disbursements", "27 500", "Funding-2015-Actual Commitments", "723 189", "Funding-2015-Actual Disbursements", "321 765", "Totals-Actual Commitments", "2 144 284,32", "Totals-Actual Disbursements", "349 265", "Region", "Balti County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "7", "Funding-2006-Actual Commitments", "31 957,39", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "831 933", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "37 500", "Funding-2014-Actual Disbursements", "16 500", "Funding-2015-Actual Commitments", "388 234,5", "Funding-2015-Actual Disbursements", "160 882,5", "Totals-Actual Commitments", "1 289 624,89", "Totals-Actual Disbursements", "177 382,5", "Primary Sector", "110 - EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "3", "Funding-2006-Actual Commitments", "5 326,23", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "2 750", "Funding-2015-Actual Commitments", "334 954,5", "Funding-2015-Actual Disbursements", "160 882,5", "Totals-Actual Commitments", "340 280,73", "Totals-Actual Disbursements", "163 632,5", "Primary Sector", "112 - BASIC EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "120 - HEALTH", 6252), "Activity Count", "2", "Funding-2006-Actual Commitments", "15 978,7", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "498 400", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "8 250", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "514 378,7", "Totals-Actual Disbursements", "8 250", "Primary Sector", "120 - HEALTH")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Cahul County", 9087)).withContents("Primary Sector", "", "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "4 400 000", "Funding-2014-Actual Disbursements", "450 000", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "7 070 000", "Totals-Actual Disbursements", "450 000", "Region", "Cahul County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "113 - SECONDARY EDUCATION", 6246), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "2 670 000", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "4 400 000", "Funding-2014-Actual Disbursements", "450 000", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "7 070 000", "Totals-Actual Disbursements", "450 000", "Primary Sector", "113 - SECONDARY EDUCATION")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Chisinau City", 9088)).withContents("Primary Sector", "", "Activity Count", "4", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "50 000", "Funding-2014-Actual Disbursements", "27 500", "Funding-2015-Actual Commitments", "246 912", "Funding-2015-Actual Disbursements", "17 500", "Totals-Actual Commitments", "296 912", "Totals-Actual Disbursements", "45 000", "Region", "Chisinau City")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "4", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "50 000", "Funding-2014-Actual Disbursements", "27 500", "Funding-2015-Actual Commitments", "246 912", "Funding-2015-Actual Disbursements", "17 500", "Totals-Actual Commitments", "296 912", "Totals-Actual Disbursements", "45 000", "Primary Sector", "110 - EDUCATION")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Chisinau County", 9089)).withContents("Primary Sector", "", "Activity Count", "4", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "1 700 000", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Commitments", "3 365 760,63", "Funding-2014-Actual Disbursements", "155 000", "Funding-2015-Actual Commitments", "1 200", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "5 066 960,63", "Totals-Actual Disbursements", "190 000", "Region", "Chisinau County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "4", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "1 700 000", "Funding-2013-Actual Disbursements", "35 000", "Funding-2014-Actual Commitments", "3 365 760,63", "Funding-2014-Actual Disbursements", "155 000", "Funding-2015-Actual Commitments", "1 200", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "5 066 960,63", "Totals-Actual Disbursements", "190 000", "Primary Sector", "110 - EDUCATION")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Drochia County", 9090))
			        .withContents("Primary Sector", "", "Activity Count", "2", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "621 600", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "621 600", "Totals-Actual Disbursements", "80 000", "Region", "Drochia County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "2", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "372 960", "Funding-2015-Actual Disbursements", "80 000", "Totals-Actual Commitments", "372 960", "Totals-Actual Disbursements", "80 000", "Primary Sector", "110 - EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "248 640", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "248 640", "Totals-Actual Disbursements", "0", "Primary Sector", "112 - BASIC EDUCATION")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Dubasari County", 9091))
			        .withContents("Primary Sector", "", "Activity Count", "2", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "123 321", "Funding-2011-Actual Commitments", "213 231", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "27 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "17 500", "Totals-Actual Commitments", "213 231", "Totals-Actual Disbursements", "168 321", "Region", "Dubasari County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "27 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "17 500", "Totals-Actual Commitments", "0", "Totals-Actual Disbursements", "45 000", "Primary Sector", "110 - EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "123 321", "Funding-2011-Actual Commitments", "213 231", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "213 231", "Totals-Actual Disbursements", "123 321", "Primary Sector", "112 - BASIC EDUCATION")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Edinet County", 9092)).withContents("Primary Sector", "", "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "567 421", "Funding-2013-Actual Disbursements", "131 845", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "567 421", "Totals-Actual Disbursements", "131 845", "Region", "Edinet County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "567 421", "Funding-2013-Actual Disbursements", "131 845", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "567 421", "Totals-Actual Disbursements", "131 845", "Primary Sector", "112 - BASIC EDUCATION")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Falesti County", 9093)).withContents("Primary Sector", "", "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "453 213", "Funding-2011-Actual Commitments", "999 888", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "999 888", "Totals-Actual Disbursements", "453 213", "Region", "Falesti County")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "130 - POPULATION POLICIES/PROGRAMMES AND REPRODUCTIVE HEALTH", 6267), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "453 213", "Funding-2011-Actual Commitments", "999 888", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "999 888", "Totals-Actual Disbursements", "453 213", "Primary Sector", "130 - POPULATION POLICIES/PROGRAMMES AND REPRODUCTIVE HEALTH")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Transnistrian Region", 9105))
			        .withContents("Primary Sector", "", "Activity Count", "2", "Funding-2006-Actual Commitments", "43 578,26", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "123 321", "Funding-2014-Actual Disbursements", "22 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "166 899,26", "Totals-Actual Disbursements", "22 500", "Region", "Transnistrian Region")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "2", "Funding-2006-Actual Commitments", "26 146,96", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "123 321", "Funding-2014-Actual Disbursements", "13 500", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "149 467,96", "Totals-Actual Disbursements", "13 500", "Primary Sector", "110 - EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "1", "Funding-2006-Actual Commitments", "4 357,83", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "2 250", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "4 357,83", "Totals-Actual Disbursements", "2 250", "Primary Sector", "112 - BASIC EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "120 - HEALTH", 6252), "Activity Count", "1", "Funding-2006-Actual Commitments", "13 073,48", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "0", "Funding-2014-Actual Disbursements", "6 750", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "13 073,48", "Totals-Actual Disbursements", "6 750", "Primary Sector", "120 - HEALTH")        ),
			        new ReportAreaForTests(new AreaOwner("Region", "Region: Undefined", -8977))
			        .withContents("Primary Sector", "", "Activity Count", "11", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "60 000", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "145 732,14", "Funding-2014-Actual Disbursements", "200", "Funding-2015-Actual Commitments", "378 930,84", "Funding-2015-Actual Disbursements", "570", "Totals-Actual Commitments", "649 662,98", "Totals-Actual Disbursements", "72 770", "Region", "Region: Undefined")
			        .withChildren(
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "110 - EDUCATION", 6236), "Activity Count", "9", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "100 000", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "60 000", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "25 000", "Funding-2012-Actual Disbursements", "12 000", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "120 168,92", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "322 737,76", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "567 906,68", "Totals-Actual Disbursements", "72 000", "Primary Sector", "110 - EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "112 - BASIC EDUCATION", 6242), "Activity Count", "2", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "363,21", "Funding-2014-Actual Disbursements", "200", "Funding-2015-Actual Commitments", "9 393,08", "Funding-2015-Actual Disbursements", "570", "Totals-Actual Commitments", "9 756,3", "Totals-Actual Disbursements", "770", "Primary Sector", "112 - BASIC EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "113 - SECONDARY EDUCATION", 6246), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "13 200", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "46 800", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "60 000", "Totals-Actual Disbursements", "0", "Primary Sector", "113 - SECONDARY EDUCATION"),
			          new ReportAreaForTests(new AreaOwner("Primary Sector", "Primary Sector: Undefined", -999999999), "Activity Count", "1", "Funding-2006-Actual Commitments", "0", "Funding-2006-Actual Disbursements", "0", "Funding-2009-Actual Commitments", "0", "Funding-2009-Actual Disbursements", "0", "Funding-2010-Actual Commitments", "0", "Funding-2010-Actual Disbursements", "0", "Funding-2011-Actual Commitments", "0", "Funding-2011-Actual Disbursements", "0", "Funding-2012-Actual Commitments", "0", "Funding-2012-Actual Disbursements", "0", "Funding-2013-Actual Commitments", "0", "Funding-2013-Actual Disbursements", "0", "Funding-2014-Actual Commitments", "12 000", "Funding-2014-Actual Disbursements", "0", "Funding-2015-Actual Commitments", "0", "Funding-2015-Actual Disbursements", "0", "Totals-Actual Commitments", "12 000", "Totals-Actual Disbursements", "0", "Primary Sector", "Primary Sector: Undefined")        )      ));
		
		runNiTestCase(spec("activity-count-summary-dual"), "en", acts, cor);
	}
	@Test
	public void testCapitalExpenditurePercentages() {
		NiReportModel cor = new NiReportModel("testCapitalExpenditurePercentages")
			.withHeaders(Arrays.asList(
					"(RAW: (startRow: 0, rowSpan: 1, totalRowSpan: 4, colStart: 0, colSpan: 19))",
					"(Project Title: (startRow: 1, rowSpan: 3, totalRowSpan: 3, colStart: 0, colSpan: 1));(Funding: (startRow: 1, rowSpan: 1, totalRowSpan: 3, colStart: 1, colSpan: 12));(Totals: (startRow: 1, rowSpan: 2, totalRowSpan: 3, colStart: 13, colSpan: 6))",
					"(2010: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 1, colSpan: 6));(2014: (startRow: 2, rowSpan: 1, totalRowSpan: 2, colStart: 7, colSpan: 6))",
					"(Planned Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 1, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1));(Planned Disbursements - Capital: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 3, colSpan: 1));(Planned Disbursements - Expenditure: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 4, colSpan: 1));(Actual Disbursements - Capital: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 5, colSpan: 1));(Actual Disbursements - Recurrent: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 6, colSpan: 1));(Planned Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 7, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 8, colSpan: 1));(Planned Disbursements - Capital: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 9, colSpan: 1));(Planned Disbursements - Expenditure: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 10, colSpan: 1));(Actual Disbursements - Capital: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 11, colSpan: 1));(Actual Disbursements - Recurrent: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 12, colSpan: 1));(Planned Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 13, colSpan: 1));(Actual Disbursements: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 14, colSpan: 1));(Planned Disbursements - Capital: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 15, colSpan: 1));(Planned Disbursements - Expenditure: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 16, colSpan: 1));(Actual Disbursements - Capital: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 17, colSpan: 1));(Actual Disbursements - Recurrent: (startRow: 3, rowSpan: 1, totalRowSpan: 1, colStart: 18, colSpan: 1))"))
				.withWarnings(Arrays.asList())
				.withBody(      new ReportAreaForTests(null)
			      .withContents("Project Title", "", "Funding-2010-Planned Disbursements", "0", "Funding-2010-Actual Disbursements", "123,321", "Funding-2010-Planned Disbursements - Capital", "0", "Funding-2010-Planned Disbursements - Expenditure", "0", "Funding-2010-Actual Disbursements - Capital", "0", "Funding-2010-Actual Disbursements - Recurrent", "0", "Funding-2014-Planned Disbursements", "90,000", "Funding-2014-Actual Disbursements", "80,000", "Funding-2014-Planned Disbursements - Capital", "10,800", "Funding-2014-Planned Disbursements - Expenditure", "79,200", "Funding-2014-Actual Disbursements - Capital", "27,200", "Funding-2014-Actual Disbursements - Recurrent", "52,800", "Totals-Planned Disbursements", "90,000", "Totals-Actual Disbursements", "203,321", "Totals-Planned Disbursements - Capital", "10,800", "Totals-Planned Disbursements - Expenditure", "79,200", "Totals-Actual Disbursements - Capital", "27,200", "Totals-Actual Disbursements - Recurrent", "52,800")
			      .withChildren(
			        new ReportAreaForTests(new AreaOwner(12), "Project Title", "TAC_activity_1", "Funding-2010-Actual Disbursements", "123,321", "Totals-Actual Disbursements", "123,321"),
			        new ReportAreaForTests(new AreaOwner(50), "Project Title", "activity with capital spending", "Funding-2014-Planned Disbursements", "90,000", "Funding-2014-Actual Disbursements", "80,000", "Funding-2014-Planned Disbursements - Capital", "10,800", "Funding-2014-Planned Disbursements - Expenditure", "79,200", "Funding-2014-Actual Disbursements - Capital", "27,200", "Funding-2014-Actual Disbursements - Recurrent", "52,800", "Totals-Planned Disbursements", "90,000", "Totals-Actual Disbursements", "80,000", "Totals-Planned Disbursements - Capital", "10,800", "Totals-Planned Disbursements - Expenditure", "79,200", "Totals-Actual Disbursements - Capital", "27,200", "Totals-Actual Disbursements - Recurrent", "52,800")      ));

		
		ReportSpecificationImpl spec = buildSpecification("testCapitalExpenditurePercentages", 
			Arrays.asList(ColumnConstants.PROJECT_TITLE),
			Arrays.asList(MeasureConstants.PLANNED_DISBURSEMENTS, MeasureConstants.ACTUAL_DISBURSEMENTS, MeasureConstants.PLANNED_DISBURSEMENTS_CAPITAL, MeasureConstants.PLANNED_DISBURSEMENTS_EXPENDITURE, MeasureConstants.ACTUAL_DISBURSEMENTS_CAPITAL, MeasureConstants.ACTUAL_DISBURSEMENTS_RECURRENT), 
			null, 
			GroupingCriteria.GROUPING_YEARLY);
		
		runNiTestCase(spec, "en", Arrays.asList("activity with capital spending", "TAC_activity_1"), cor);
	}
}