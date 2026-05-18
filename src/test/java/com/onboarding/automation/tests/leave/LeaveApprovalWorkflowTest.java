package com.onboarding.automation.tests.leave;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.LoginPage;
import com.onboarding.automation.pages.leave.LeavePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LeaveApprovalWorkflowTest extends BaseTest {
    private LeavePage leavePage;

    @BeforeMethod
    public void setupWorkflow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs("OMI-0076", "Omfys@123");

        // NEW: Wait for the login redirect to finish before jumping to Leave
        WebDriverWait syncWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        syncWait.until(ExpectedConditions.urlContains("home"));

        driver.get("https://uat_mcdp_hcm.omfysgroup.com/hcm/leave");
        leavePage = new LeavePage(driver);

        try {
            leavePage.waitForPageLoad();
        } catch (Exception e) {
            // Fallback for slow UAT
            driver.navigate().refresh();
            leavePage.waitForPageLoad();
        }
    }

//    @Test(priority = 1, description = "Verify Leave Preview details for a searched employee")
//    public void testEmployeeLeavePreview() {
//        // FIX #1: Click pending card with overlay handling
//        leavePage.clickPendingCard();
//
//        // Verify modal opened successfully
//        Assert.assertTrue(leavePage.isPendingModalVisible(), "Modal failed to open!");
//
//        // Search for employee
//        leavePage.searchInModal("Omkar Surve");
//
//        // Click first employee link
//        leavePage.clickFirstEmployeeLink();
//
//        // Verify leave type
//        Assert.assertEquals(leavePage.getPreviewLeaveType(), "LWP", "Leave type mismatch!");
//
//        // Clean up
//        leavePage.closePreview();
//        leavePage.closeModals();
//    }

    @Test(priority = 2, description = "Verify table pagination filters rows correctly")
    public void testPaginationEntries() {
        // FIX #2: Click and wait for modal to be fully ready
        leavePage.clickPendingCard();
        Assert.assertTrue(leavePage.isPendingModalVisible(), "Modal failed to open!");

        // Now select entries per page (method now handles modal readiness)
        leavePage.selectEntriesPerPage("10");

        // Verify row count
        int rows = leavePage.getTableRowCount();
        Assert.assertTrue(rows <= 10, "Table displayed more than 10 rows! Actual rows: " + rows);

        // Clean up
        leavePage.closeModals();
    }

//    @Test(priority = 3, description = "Verify search filters the table results")
//    public void testSearchFunctionality() {
//        // FIX #3: Open modal and verify
//        leavePage.clickPendingCard();
//        Assert.assertTrue(leavePage.isPendingModalVisible(), "Modal failed to open!");
//
//        // Search for employee (method now waits for results)
//        String searchTarget = "Prashant";
//        leavePage.searchInModal(searchTarget);
//
//        // Get first row name (method now waits for visibility)
//        String resultName = leavePage.getFirstRowEmployeeName();
//
//        // Verify search result contains the search keyword
//        Assert.assertTrue(resultName.contains(searchTarget),
//                "Search result '" + resultName + "' does not contain keyword '" + searchTarget + "'!");
//
//        // Clean up
//        leavePage.closeModals();
//    }

    @Test(priority = 4, description = "Verify calendar month navigation")
    public void testCalendarNavigation() {
        // FIX #4: Get starting month
        String startMonth = leavePage.getMonthText();

        // Click next month (method now handles SVG properly)
        leavePage.clickNextMonth();

        // Get updated month
        String endMonth = leavePage.getMonthText();

        // Verify month changed
        Assert.assertNotEquals(startMonth, endMonth,
                "Month navigation failed to update label! Start: " + startMonth + ", End: " + endMonth);
    }
}