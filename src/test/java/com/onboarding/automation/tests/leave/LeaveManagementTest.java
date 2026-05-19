package com.onboarding.automation.tests.leave;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.LoginPage;
import com.onboarding.automation.pages.leave.LeavePage;
import com.onboarding.automation.pages.leave.creditleave.CreditLeavePage;
import com.onboarding.automation.pages.leave.bulkapply.BulkApplyPage;
import com.onboarding.automation.pages.leave.PatternRulePage;
import com.onboarding.automation.pages.leave.ConfigurationsPage;
import com.onboarding.automation.pages.leave.LeaveSettingsPage;
import com.onboarding.automation.pages.leave.workflows.WorkflowSetupPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeaveManagementTest extends BaseTest {

    private LeavePage leavePage;
    private final String LEAVE_BASE_URL = "https://uat_mcdp_hcm.omfysgroup.com/hcm/leave";

    @BeforeMethod
    public void initiateLeaveModule() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("OMI-0076", "Omfys@1234");
        Assert.assertTrue(loginPage.waitForSuccessfulLogin(), "Login failed!");

        driver.get(LEAVE_BASE_URL);
        leavePage = new LeavePage(driver);
        // FIXED: Removed waitForPageLoad() - using simple wait instead
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(priority = 1, description = "Verify Overview tab navigation")
    public void testOverviewNavigation() {
        leavePage.clickOverview();
        Assert.assertEquals(driver.getCurrentUrl(), LEAVE_BASE_URL,
                "Overview navigation failed!");
    }

    @Test(priority = 2, description = "Verify Credit Leave tab navigation")
    public void testCreditLeaveNavigation() {
        leavePage.clickCreditLeave();
        CreditLeavePage creditPage = new CreditLeavePage(driver);
        Assert.assertTrue(creditPage.isManualButtonClickable(),
                "Credit Leave page failed to load - Manual button not clickable!");
    }

    @Test(priority = 3, description = "Verify Bulk Apply tab navigation")
    public void testBulkApplyNavigation() {
        leavePage.clickBulkApply();
        BulkApplyPage bulkPage = new BulkApplyPage(driver);
        Assert.assertTrue(bulkPage.isLoaded(), "Bulk Apply page failed to load!");
    }

    @Test(priority = 4, description = "Verify Workflow Setup tab navigation")
    public void testWorkflowSetupNavigation() {
        leavePage.clickWorkflowSetup();
        WorkflowSetupPage workflowPage = new WorkflowSetupPage(driver);
        Assert.assertTrue(workflowPage.isLoaded(), "Workflow Setup page failed to load!");
    }

    @Test(priority = 5, description = "Verify Pattern Rule tab navigation")
    public void testPatternRuleNavigation() {
        leavePage.clickPatternRule();
        PatternRulePage patternPage = new PatternRulePage(driver);
        Assert.assertTrue(patternPage.isLoaded(), "Pattern Rule page failed to load!");
    }

    @Test(priority = 6, description = "Verify Configurations tab navigation")
    public void testConfigurationsNavigation() {
        leavePage.clickConfigurations();
        ConfigurationsPage configPage = new ConfigurationsPage(driver);
        Assert.assertTrue(configPage.isLoaded(), "Configurations page failed to load!");
    }

    @Test(priority = 7, description = "Verify Leave Settings tab navigation")
    public void testLeaveSettingsNavigation() {
        leavePage.clickLeaveSettings();
        LeaveSettingsPage settingsPage = new LeaveSettingsPage(driver);
        Assert.assertTrue(settingsPage.isLoaded(), "Leave Settings page failed to load!");
    }
}