package org.dgfoundation.amp.ar.viewfetcher;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>IMMUTABLE</b>class holding the data necessary reading the i18n value of a property translated through DG_EDITOR entries<br />
 * <b>NEVER EVER MAKE THIS CLASS MUTABLE OR CONTAIN MUTABLE FIELDS</b> <br />
 * The class fetches its data from a view which has rows of the form (activity_id, text, locale)
 * @author Dolghier Constantin
 *
 */
public class DgEditorPropertyDescription implements PropertyDescription
{
	public final String viewName;
	public final String textColumnName;
	public final String languageColumnName; 
	
	private final String _toString;
	private final int _hashCode;
	
	public DgEditorPropertyDescription(String viewName, String textColumnName, String languageColumnName)
	{
		this.viewName = viewName;
		this.textColumnName = textColumnName;
		this.languageColumnName = languageColumnName;
		
		sanityCheck();
		_toString = String.format("EDGE IPP: viewName = %s, textColumnName = %s, languageColumnName = %s", viewName, textColumnName, languageColumnName);
		_hashCode = _toString.hashCode();
	}	
	
	/**
	 * throws Exception if value is not valid
	 */
	private void sanityCheck()
	{
		try
		{
			java.util.LinkedHashSet<String> columns = SQLUtils.getTableColumns(viewName);
//			if (columns.size() < 3)
//				throw new RuntimeException(String.format("error while configuring DG_EDITOR-backed translatable view %s: too little number of columns!", this.viewName));
			
			if (!columns.contains("amp_activity_id"))
				throw new RuntimeException(String.format("error while configuring DG_EDITOR-backed translatable view %s: it should contain the column <amp_activity_id> !", this.viewName));

			if (!columns.contains(textColumnName))
				throw new RuntimeException(String.format("error while configuring DG_EDITOR-backed translatable view %s: it should contain the text column <%s>!", this.viewName, textColumnName));

			if (!columns.contains(languageColumnName))
				throw new RuntimeException(String.format("error while configuring DG_EDITOR-backed translatable view %s: it should contain the locale column <%s>!", this.viewName, languageColumnName));			
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * take care when changing this function, as its output is part of the instance's hash!
	 */
	@Override
	public String toString()
	{
		return _toString;
	}
	
	@Override
	public int hashCode()
	{
		return _hashCode;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (!(other instanceof InternationalizedPropertyDescription))
			return false;
		return this.toString().equals(other.toString());
	}
	
	/**
	 * generates the SQL query which will fetch all the (id, translation) values for this field
	 * @param ids
	 * @param locale
	 * @return
	 */
	public String generateGeneralizedQuery(Collection<Long> ids, String locale)
	{
		return String.format("SELECT amp_activity_id, %s FROM %s where %s = '%s' AND (%s IS NOT NULL AND %s != '') AND amp_activity_id IN (%s)",
				textColumnName, viewName, languageColumnName, locale,
				textColumnName, textColumnName,
				ids.isEmpty() ? "-999" : SQLUtils.generateCSV(ids));
	}
	
	/**
	 * imports result generated by a query of the type "SELECT id, value FROM model". The only functions called on the ResultSet are <br />
	 * rs.getLong(1) and rs.getString(2) - this is useful if you are supplying a mock implementation
	 * @param rs
	 */
	public void importValues(Map<Long, String> values, ResultSet rs)
	{
		try
		{
			while (rs.next())
			{
				Long id = rs.getLong(1);
				String value = rs.getString(2);
			
				values.put(id, value);
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException("error while fetching translations", e);
		}
	}
	
	@Override
	public Map<Long, String> generateValues(java.sql.Connection connection, Collection<Long> ids, String locale) throws SQLException{ // will only be called for non-calculated
		Map<Long, String> res = new HashMap<Long, String>();
		
		// import values in the requested locale
		importValues(res, SQLUtils.rawRunQuery(connection, generateGeneralizedQuery(ids, locale), null));
		
		importBaseLanguageTranslations(connection, res, locale, ids);
		
		return res;
	}
	
	protected void importBaseLanguageTranslations(java.sql.Connection connection, Map<Long, String> res, String locale, Collection<Long> ids) throws SQLException {

		List<String> baseLanguages = SQLUtils.fetchAsList(connection, "SELECT default_language FROM dg_site WHERE site_id='amp' AND default_language IS NOT NULL", 1);
		String baseLanguage = baseLanguages.isEmpty() ? "en" : baseLanguages.get(0);
		
		if (!locale.equals(baseLanguage)) {
			// compute ids which have no translation in the requested language -> use English for those
			java.util.HashSet<Long> remainingIds = new java.util.HashSet<Long>(ids);
			remainingIds.removeAll(res.keySet());
	
			// import values which have no locale-translation in English
			importValues(res, SQLUtils.rawRunQuery(connection, generateGeneralizedQuery(remainingIds, baseLanguage), null));
		}
	}
	
	@Override
	public boolean isCalculated()
	{
		return false;
	}
	
	@Override
	public boolean getDeleteOriginal()
	{
		return true;
	}
	
	@Override
	public String getValueFor(java.sql.ResultSet currentLine, java.sql.ResultSet rawCurrentLine, ColumnValuesCacher cacher, String locale) // will only be called for cacheable
	{
		throw new UnsupportedOperationException("cacheable properties do not get values one-by-one");
	}
	
	@Override
	public String getNiceDescription()
	{
		return String.format("%s::%s::%s", viewName, textColumnName, languageColumnName);
	}
	
	@Override
	public boolean allIdsHaveValues(){
		return false;
	}
}
