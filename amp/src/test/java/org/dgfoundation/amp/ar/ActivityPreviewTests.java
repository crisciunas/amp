package org.dgfoundation.amp.ar;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dgfoundation.amp.testutils.ReportTestingUtils;
import org.dgfoundation.amp.testutils.ReportsTestCase;
import org.digijava.module.aim.dbentity.AmpActivityVersion;
import org.digijava.module.aim.form.helpers.ActivityFundingDigest;
import org.digijava.module.aim.helper.Constants;
import org.digijava.module.aim.helper.Funding;
import org.digijava.module.aim.helper.FundingOrganization;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ActivityPreviewTests extends ReportsTestCase 
{
	public ActivityPreviewTests(String name)
	{
		super(name);
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite(ActivityPreviewTests.class.getName());
		suite.addTest(new ActivityPreviewTests("testPureMtef"));
		suite.addTest(new ActivityPreviewTests("testPureFunding"));
		suite.addTest(new ActivityPreviewTests("testMixedActivity"));

		return suite;
	}
	
	public void testPureMtef()
	{
		AmpActivityVersion act = ReportTestingUtils.loadActivityByName("mtef activity 1");
		ActivityFundingDigest afd = new ActivityFundingDigest();
		afd.populateFromFundings(act.getFunding(), "USD", null, false);
		
		List<FundingOrganization> fundOrgs = afd.getFundingOrganizations();
		assertEquals(1, fundOrgs.size());
		FundingOrganization fo = fundOrgs.get(0);
		assertEquals("UNDP", fo.getOrgName());
		assertEquals(1, fo.getFundings().size());
		Funding funding = fo.getFundings().get(0);
		assertEquals(1, funding.getFundingDetails().size());
		assertEquals(Constants.MTEFPROJECTION, funding.getFundingDetails().get(0).getTransactionType());
		assertEquals("789 123", funding.getSubtotalMTEFs());
		//System.out.println(fo.getOrgName());
	}
	
	public void testPureFunding()
	{
		AmpActivityVersion act = ReportTestingUtils.loadActivityByName("Eth Water");
		ActivityFundingDigest afd = new ActivityFundingDigest();
		afd.populateFromFundings(act.getFunding(), "USD", null, false);
		
		List<FundingOrganization> fundOrgs = afd.getFundingOrganizations();
		assertEquals(7, fundOrgs.size());
		Set<String> allOrgNames = new HashSet<String>();
		for(FundingOrganization fundOrg:fundOrgs)
			allOrgNames.add(fundOrg.getOrgName());
		assertTrue(allOrgNames.contains("Finland"));
		assertTrue(allOrgNames.contains("Ministry of Economy"));
		assertTrue(allOrgNames.contains("Ministry of Finance"));
		assertTrue(allOrgNames.contains("Norway"));
		assertTrue(allOrgNames.contains("UNDP"));
		assertTrue(allOrgNames.contains("USAID"));
		assertTrue(allOrgNames.contains("World Bank"));
		assertEquals("545 000", afd.getTotalDisbursements());
		
	}

	public void testMixedActivity()
	{
		AmpActivityVersion act = ReportTestingUtils.loadActivityByName("Test MTEF directed");
		ActivityFundingDigest afd = new ActivityFundingDigest();
		afd.populateFromFundings(act.getFunding(), "USD", null, false);
		
		List<FundingOrganization> fundOrgs = afd.getFundingOrganizations();
		assertEquals(2, fundOrgs.size());
		Set<String> allOrgNames = new HashSet<String>();
		for(FundingOrganization fundOrg:fundOrgs)
			allOrgNames.add(fundOrg.getOrgName());
		assertTrue(allOrgNames.contains("Ministry of Economy"));
		assertTrue(allOrgNames.contains("USAID"));
		assertEquals("143 777", afd.getTotalDisbursements());
		assertEquals("215 000", afd.getTotalMtefProjections());
		
		FundingOrganization usaid = null, moe = null;
		for(FundingOrganization forg:fundOrgs)
		{
			if (forg.getOrgName().equals("USAID"))
				usaid = forg;
			
			if (forg.getOrgName().equals("Ministry of Economy"))
				moe = forg;
		}
		assertEquals(1, moe.getFundings().size());
		assertEquals("143 777", moe.getFundings().get(0).getSubtotalDisbursements());
		assertEquals("215 000", moe.getFundings().get(0).getSubtotalMTEFs());
		
		assertEquals(1, usaid.getFundings().size());
		assertEquals("44 333", usaid.getFundings().get(0).getSubtotalDisbursements());
		assertEquals("87 900", usaid.getFundings().get(0).getSubtotalMTEFs());

	}

}
