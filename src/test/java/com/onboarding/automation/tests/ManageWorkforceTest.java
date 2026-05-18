package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.ManageWorkforcePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ManageWorkforceTest extends BaseTest {

    @Test
    public void testWorkforceViewDetails() {
        ManageWorkforcePage page = new ManageWorkforcePage(driver);
        page.clickWorkforceTab();

        // Verify PDF download option exists
        Assert.assertTrue(page.isDownloadPdfVisible(), "Download PDF button not visible");

        page.clickViewDetails();
    }
}