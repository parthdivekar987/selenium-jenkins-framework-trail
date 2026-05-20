package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.OnboardingApprovalPage;
import com.onboarding.automation.utils.TestLogger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OnboardingApprovalTest extends BaseTest {

    @Test
    public void testApprovalSearchAndPagination() {
        TestLogger.testStart("TC_OA_01", "Verify Approval Search and Pagination");
        
        try {
            OnboardingApprovalPage page = new OnboardingApprovalPage(driver);
            
            TestLogger.step("1", "Click on Approval Tab");
            page.clickApprovalTab();
            TestLogger.interact("OnboardingApprovalPage", "Approval tab clicked");

            TestLogger.step("2", "Search for candidate: Medhaj");
            page.searchForCandidate("Medhaj");
            TestLogger.interact("SearchForm", "Searched for candidate: Medhaj");

            TestLogger.step("3", "Verify Action button is clickable");
            Assert.assertTrue(page.isActionButtonClickable(), "Action button is not clickable");
            TestLogger.verify("Action button is clickable");

            TestLogger.step("4", "Set pagination to 5 entries");
            page.selectPaginationEntries("5");
            TestLogger.interact("Pagination", "Changed entries per page to 5");

            TestLogger.step("5", "Click Next Page button");
            page.clickNextPage();
            TestLogger.interact("Pagination", "Moved to next page");

            TestLogger.step("6", "Set pagination to 10 entries");
            page.selectPaginationEntries("10");
            TestLogger.interact("Pagination", "Changed entries per page to 10");

            TestLogger.success("All approval operations completed successfully");
            TestLogger.testEnd("TC_OA_01", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_OA_01", "FAILED");
            throw e;
        }
    }
}
