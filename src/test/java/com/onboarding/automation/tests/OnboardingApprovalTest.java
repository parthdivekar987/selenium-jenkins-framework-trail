package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.OnboardingApprovalPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OnboardingApprovalTest extends BaseTest {

    @Test
    public void testApprovalSearchAndPagination() {
        OnboardingApprovalPage page = new OnboardingApprovalPage(driver);
        page.clickApprovalTab();

        page.searchForCandidate("Medhaj");

        // Check if action button is clickable
        Assert.assertTrue(page.isActionButtonClickable(), "Action button is not clickable");

        // Test Pagination
        page.selectPaginationEntries("5");
        page.clickNextPage();
        page.selectPaginationEntries("10");
    }
}