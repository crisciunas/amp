/**
 * 
 */
package org.dgfoundation.amp.reports;

import org.dgfoundation.amp.ar.AmpARFilter;
import org.dgfoundation.amp.newreports.GroupingCriteria;
import org.dgfoundation.amp.newreports.ReportColumn;
import org.dgfoundation.amp.newreports.ReportFiltersImpl;
import org.dgfoundation.amp.newreports.ReportMeasure;
import org.dgfoundation.amp.newreports.ReportSpecificationImpl;
import org.dgfoundation.amp.reports.mondrian.AmpARFilterTranslator;
import org.digijava.module.aim.ar.util.FilterUtil;
import org.digijava.module.aim.dbentity.AmpReportColumn;
import org.digijava.module.aim.dbentity.AmpReports;

/**
 * Utility methods
 * @author Nadejda Mandrescu
 */
public class ReportUtils {
	
	/**
	 * Translation of {@link AmpReports} report to Reports API report structure 
	 * @param report - {@link AmpReports} 
	 * @return {@link ReportSpecificationImpl}
	 */
	public static ReportSpecificationImpl toReportSpecification(AmpReports report) {
		//basic
		ReportSpecificationImpl spec = new ReportSpecificationImpl(report.getName());
		for (AmpReportColumn column : report.getColumns()) {
			spec.addColumn(new ReportColumn(column.getColumn().getColumnName()));
		}
		for (String measureName: report.getMeasureNames()) {
			spec.addMeasure(new ReportMeasure(measureName));
		}
		spec.setDisplayEmptyFundingColumns(report.getAllowEmptyFundingColumns());
		spec.setDisplayEmptyFundingRows(false); //default expectation
		spec.setColsHierarchyTotals(0);
		spec.setRowsHierarchiesTotals(report.getHierarchies().size());
		
		switch(report.getOptions()) {
		case "A": spec.setGroupingCriteria(GroupingCriteria.GROUPING_YEARLY); break;
		case "Q": spec.setGroupingCriteria(GroupingCriteria.GROUPING_QUARTERLY); break;
		case "M": spec.setGroupingCriteria(GroupingCriteria.GROUPING_MONTHLY); break;
		default: spec.setGroupingCriteria(GroupingCriteria.GROUPING_TOTALS_ONLY); break;
		}
		
		AmpARFilter arFilter = FilterUtil.buildFilter(report, report.getAmpReportId());
		AmpARFilterTranslator arFilterTranslator = new AmpARFilterTranslator(arFilter); 
		spec.setFilters(new ReportFiltersImpl(arFilterTranslator.buildFilters()));
		
		//TODO:
		//report.getAlsoShowPledges()
		//report.getHideActivities()
		//report.getHierarchyNames()
		//report.isSummaryReportNoHierachies()
		//..
		
		return spec;
	}
}
