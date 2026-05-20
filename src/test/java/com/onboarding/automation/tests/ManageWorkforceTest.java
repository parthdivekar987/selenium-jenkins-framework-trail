package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.ManageWorkforcePage;
import com.onboarding.automation.utils.TestLogger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ManageWorkforceTest extends BaseTest {

    @Test
    public void testWorkforceViewDetails() {
        TestLogger.testStart("TC_WF_01", "Verify Workforce View Details and PDF Download");
        
        try {
            ManageWorkforcePage page = new ManageWorkforcePage(driver);
            
            TestLogger.step("1", "Click on Workforce Tab");
            page.clickWorkforceTab();
            TestLogger.interact("ManageWorkforcePage", "Workforce tab clicked");

            TestLogger.step("2", "Verify PDF download option exists");
            Assert.assertTrue(page.isDownloadPdfVisible(), "Download PDF button not visible");
            TestLogger.verify("Download PDF button is visible");

            TestLogger.step("3", "Click View Details button");
            page.clickViewDetails();
            TestLogger.interact("ManageWorkforcePage", "View Details button clicked");

            TestLogger.success("Workforce details loaded successfully");
            TestLogger.testEnd("TC_WF_01", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_WF_01", "FAILED");
            throw e;
        }
    }
}
