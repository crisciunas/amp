package org.dgfoundation.amp.ar;

import java.util.HashSet;
import java.util.List;

import org.dgfoundation.amp.testmodels.ColumnReportDataModel;
import org.dgfoundation.amp.testmodels.GroupColumnModel;
import org.dgfoundation.amp.testmodels.GroupReportModel;
import org.dgfoundation.amp.testmodels.SimpleColumnModel;
import org.dgfoundation.amp.testutils.*;

import org.hibernate.cfg.*;

import static org.dgfoundation.amp.testutils.ReportTestingUtils.NULL_PLACEHOLDER;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MiscColumnsTests extends ReportsTestCase
{
	public MiscColumnsTests(String name)
	{
		super(name);
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite(MiscColumnsTests.class.getName());
		suite.addTest(new MiscColumnsTests("testSscColumns"));// - these columns removed in AMP 2.7
		suite.addTest(new MiscColumnsTests("testModeOfPaymentUndisbursedBalance"));
		suite.addTest(new MiscColumnsTests("testProjectedProjectCostEUR"));
		suite.addTest(new MiscColumnsTests("testProjectedProjectCostUSD"));
		return suite;
	}
	
	public void testSscColumns()
	{
		GroupReportModel fssc_correct = GroupReportModel.withColumnReports("AMP-15844-ssc-columns", 
				ColumnReportDataModel.withColumns("AMP-15844-ssc-columns", 
						SimpleColumnModel.withContents("Project Title", NULL_PLACEHOLDER),
						SimpleColumnModel.withContents("Type of Cooperation", "SSC Project 1", "Official Development Aid (ODA)", "SSC Project 2", "Regional South South Cooperation"),
						SimpleColumnModel.withContents("Type of Implementation", "SSC Project 1", "Program", "SSC Project 2", "Action"),
						SimpleColumnModel.withContents("Modalities", "SSC Project 1", "Diplomats and courses", "SSC Project 2", "Virtual Platforms and blogs to consult, learn, and exchange ideas"),
						SimpleColumnModel.withContents("Component Description", "SSC Project 1", "SSC Project 1 DescriptionOfComponent", "SSC Project 2", "SSC Project 2 Description of Component"),
						SimpleColumnModel.withContents("Component Title", "SSC Project 1", "SSC Project 1 TitleOfComponent", "SSC Project 2", "SSC Project 2 Title of Component"),

						
						GroupColumnModel.withSubColumns("Funding", 
								GroupColumnModel.withSubColumns("2013", 
									SimpleColumnModel.withContents("Actual Commitments", "ptc activity 1", "666 777", "SSC Project 1", "111 333", "SSC Project 2", "567 421"),
									SimpleColumnModel.withContents("Actual Disbursements", "SSC Project 1", "555 111", "SSC Project 2", "131 845"))),
						GroupColumnModel.withSubColumns("Total Costs", 
								SimpleColumnModel.withContents("Actual Commitments", "ptc activity 1", "666 777", "SSC Project 1", "111 333", "SSC Project 2", "567 421"),
								SimpleColumnModel.withContents("Actual Disbursements", "SSC Project 1", "555 111", "SSC Project 2", "131 845")))
						);
		
		boolean caughtException = false;
		try
		{
			runReportTest("flat SSC Columns", "AMP-15844-ssc-columns", new String[] {"ptc activity 1", "SSC Project 1", "SSC Project 2"}, fssc_correct);
		}
		catch(RuntimeException ex)
		{
			caughtException = ex.getMessage().startsWith("no report with the given name");
		}
		assertEquals("report AMP-15844-ssc-columns should have been deleted and non-existant", true, caughtException);
	}
	
	
	public void testModeOfPaymentUndisbursedBalance()
	{
		GroupReportModel mop_ub_correct = GroupReportModel.withColumnReports("AMP-15863-mode-of-payment-undisbursed-balance",
				ColumnReportDataModel.withColumns("AMP-15863-mode-of-payment-undisbursed-balance",
						SimpleColumnModel.withContents("Project Title", "Eth Water", "Eth Water", "mtef activity 2", "mtef activity 2", "SSC Project 1", "SSC Project 1").setIsPledge(false), 
						SimpleColumnModel.withContents("Mode of Payment", "Eth Water", "Mode of Payment Unallocated", "mtef activity 2", "Mode of Payment Unallocated", "SSC Project 1", "Mode of Payment Unallocated").setIsPledge(false), 
						GroupColumnModel.withSubColumns("Total Costs",
							SimpleColumnModel.withContents("Undisbursed Balance", "Eth Water", "-545 000", "mtef activity 2", "0", "SSC Project 1", "-443 778").setIsPledge(false)))
					.withTrailCells(null, null, "-988 778"))
				.withTrailCells(null, null, "-988 778")
				.withPositionDigest(true,
					"(line 0:RHLC Project Title: (startRow: 0, rowSpan: 2, totalRowSpan: 2, colStart: 0, colSpan: 1), RHLC Mode of Payment: (startRow: 0, rowSpan: 2, totalRowSpan: 2, colStart: 1, colSpan: 1), RHLC Total Costs: (startRow: 0, rowSpan: 1, totalRowSpan: 2, colStart: 2, colSpan: 1))",
					"(line 1:RHLC Undisbursed Balance: (startRow: 1, rowSpan: 1, totalRowSpan: 1, colStart: 2, colSpan: 1))");
		
		runReportTest("Mode of payment + Undisbursed Balance", "AMP-15863-mode-of-payment-undisbursed-balance", new String[] {"Eth Water", "mtef activity 2", "SSC Project 1"}, mop_ub_correct);
	}
	
	public void testProjectedProjectCostEUR()
	{
		GroupReportModel prop_cost_eur_correct = GroupReportModel.withColumnReports("Proposed-cost-EUR",
				ColumnReportDataModel.withColumns("Proposed-cost-EUR", 
					SimpleColumnModel.withContents("Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Cost 1 - USD", "Proposed Project Cost 2 - EUR", "Proposed Project Cost 2 - EUR"),
					SimpleColumnModel.withContents("Proposed Project Amount", "Proposed Project Cost 1 - USD", "770 600", "Proposed Project Cost 2 - EUR", "2 500 000"),
					GroupColumnModel.withSubColumns("Total Costs",
							SimpleColumnModel.withContents("Actual Commitments", NULL_PLACEHOLDER),
							SimpleColumnModel.withContents("Actual Disbursements", NULL_PLACEHOLDER)
							)
				));
		
		runReportTest("Proposed-cost in EUR", "Proposed-cost-EUR", new String[] {"Proposed Project Cost 1 - USD", "Proposed Project Cost 2 - EUR"}, prop_cost_eur_correct);
	}
	
	public void testProjectedProjectCostUSD()
	{
		GroupReportModel prop_cost_eur_correct = GroupReportModel.withColumnReports("Proposed-cost-USD",
				ColumnReportDataModel.withColumns("Proposed-cost-USD", 
					SimpleColumnModel.withContents("Project Title", "Proposed Project Cost 1 - USD", "Proposed Project Cost 1 - USD", "Proposed Project Cost 2 - EUR", "Proposed Project Cost 2 - EUR"),
					SimpleColumnModel.withContents("Proposed Project Amount", "Proposed Project Cost 1 - USD", "1 000 000", "Proposed Project Cost 2 - EUR", "3 359 312,01"),
					GroupColumnModel.withSubColumns("Total Costs",
							SimpleColumnModel.withContents("Actual Commitments", NULL_PLACEHOLDER),
							SimpleColumnModel.withContents("Actual Disbursements", NULL_PLACEHOLDER)
							)
				));
		
		runReportTest("Proposed-cost in USD", "Proposed-cost-USD", new String[] {"Proposed Project Cost 1 - USD", "Proposed Project Cost 2 - EUR"}, prop_cost_eur_correct);
	}

}