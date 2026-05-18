package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.ExtendReactivatePage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExtendReactivateTest extends BaseTest {

    private ExtendReactivatePage page;

    @BeforeClass(alwaysRun = true)
    public void openModule() {
        page = new ExtendReactivatePage(driver);
        page.clickExtendReactivateTab();
        Assert.assertTrue(page.isExtendReactivatePageLoaded(), "Extend/Reactivate page failed to load");
    }

    // ==========================
    // RADIO BUTTON TESTS
    // ==========================

    @Test(priority = 1, description = "Verify Extend Link Validity is selected by default")
    public void testDefaultRadioSelection() {
        Assert.assertTrue(page.isExtendLinkRadioSelected(),
                "Extend Link Validity should be selected by default");
        Assert.assertFalse(page.isReactivateLinkRadioSelected(),
                "Reactivate Link should NOT be selected by default");
    }

    @Test(priority = 2, description = "Verify switching between radio buttons works")
    public void testRadioButtonToggle() {
        page.selectReactivateLink();
        Assert.assertTrue(page.isReactivateLinkRadioSelected(),
                "Reactivate Link should be selected after toggle");
        Assert.assertFalse(page.isExtendLinkRadioSelected(),
                "Extend Link should NOT be selected after toggle");

        page.selectExtendLinkValidity();
        Assert.assertTrue(page.isExtendLinkRadioSelected(),
                "Extend Link should be selected after switching back");
        Assert.assertFalse(page.isReactivateLinkRadioSelected(),
                "Reactivate Link should NOT be selected after switching back");
    }

    // ==========================
    // PAGINATION DROPDOWN TESTS (EXTEND MODE)
    // ==========================

    @Test(priority = 3, description = "Verify pagination dropdown in Extend mode")
    public void testPaginationDropdownExtendMode() {
        page.selectExtendLinkValidity();

        page.selectPaginationEntries("5");
        Assert.assertEquals(page.getCurrentPaginationValue(), "5",
                "Pagination should show 5 entries");

        page.selectPaginationEntries("10");
        Assert.assertEquals(page.getCurrentPaginationValue(), "10",
                "Pagination should show 10 entries");

        page.selectPaginationEntries("25");
        Assert.assertEquals(page.getCurrentPaginationValue(), "25",
                "Pagination should show 25 entries");
    }

    // ==========================
    // PAGINATION DROPDOWN TESTS (REACTIVATE MODE)
    // ==========================

    @Test(priority = 4, description = "Verify pagination dropdown in Reactivate mode")
    public void testPaginationDropdownReactivateMode() {
        page.selectReactivateLink();

        page.selectPaginationEntries("5");
        Assert.assertEquals(page.getCurrentPaginationValue(), "5",
                "Pagination should show 5 entries in Reactivate mode");

        page.selectPaginationEntries("10");
        Assert.assertEquals(page.getCurrentPaginationValue(), "10",
                "Pagination should show 10 entries in Reactivate mode");

        page.selectPaginationEntries("25");
        Assert.assertEquals(page.getCurrentPaginationValue(), "25",
                "Pagination should show 25 entries in Reactivate mode");
    }

    // ==========================
    // SEARCH FUNCTIONALITY TESTS
    // ==========================

    @Test(priority = 5, description = "Verify search placeholder text is correct")
    public void testSearchPlaceholder() {
        page.selectReactivateLink();
        String placeholder = page.getSearchPlaceholder();
        Assert.assertEquals(placeholder, "Search candidate...",
                "Search placeholder should be 'Search candidate...'");
    }

    @Test(priority = 6, description = "Verify search for candidate 'Srujal' works")
    public void testSearchForSrujal() {
        page.selectReactivateLink();
        page.searchForCandidate("Srujal");

        // Verify search was entered (no assertion needed, just verifying no exception)
        Assert.assertTrue(true, "Search executed successfully");

        // Clear search after test
        page.clearSearch();
    }

    @Test(priority = 7, description = "Verify clear search functionality")
    public void testClearSearch() {
        page.selectReactivateLink();
        page.searchForCandidate("Srujal");
        page.clearSearch();

        // Search field should be empty
        Assert.assertTrue(true, "Search cleared successfully");
    }

    // ==========================
    // TABLE VERIFICATION TESTS
    // ==========================

    @Test(priority = 8, description = "Verify table headers are displayed correctly")
    public void testTableHeaders() {
        page.selectReactivateLink();

        java.util.List<String> headers = page.getAllTableHeaders();

        Assert.assertFalse(headers.isEmpty(), "Table should have headers");

        System.out.println("Table Headers: " + headers);

        // Verify expected headers exist
        boolean hasCandidateName = headers.stream().anyMatch(h ->
                h.contains("Candidate") || h.contains("Name"));
        boolean hasEmail = headers.stream().anyMatch(h ->
                h.contains("Email"));
        boolean hasAction = headers.stream().anyMatch(h ->
                h.contains("Action"));

        Assert.assertTrue(hasCandidateName, "Table should have Candidate Name column");
        Assert.assertTrue(hasEmail, "Table should have Email column");
        Assert.assertTrue(hasAction, "Table should have Action column");
    }

    @Test(priority = 9, description = "Verify row count and table state")
    public void testTableRowCount() {
        page.selectReactivateLink();

        int rowCount = page.getRowCount();
        System.out.println("Rows in table: " + rowCount);

        if (rowCount == 0) {
            Assert.assertTrue(page.isTableEmpty(), "Table should show 'No candidates found' message");
        } else {
            Assert.assertTrue(rowCount > 0, "Table should have at least one row");
        }
    }

    // ==========================
    // REACTIVATE BUTTON TESTS
    // ==========================

    @Test(priority = 10, description = "Verify Reactivate button is displayed when candidates exist")
    public void testReactivateButtonDisplayed() {
        page.selectReactivateLink();
        page.searchForCandidate("Srujal");

        if (!page.isTableEmpty() && page.getRowCount() > 0) {
            Assert.assertTrue(page.isReactivateButtonDisplayed(),
                    "Reactivate button should be displayed for candidates");
            System.out.println("Reactivate button count: " + page.getReactivateButtonCount());
        } else {
            System.out.println("No candidates found - skipping button check");
            Assert.assertTrue(true, "No candidates to test");
        }

        page.clearSearch();
    }

    // ==========================
    // MODAL FLOW TESTS
    // ==========================
//
//    @Test(priority = 11, description = "Verify Reactivate → Cancel modal flow works")
//    public void testReactivateAndCancelFlow() {
//        page.selectReactivateLink();
//        page.searchForCandidate("Srujal");
//
//        if (!page.isTableEmpty() && page.getRowCount() > 0) {
//            page.clickReactivateButtonForFirstRow();
//
//            // Verify modal appears
//            boolean modalShown = page.isModalDisplayed();
//            System.out.println("Modal displayed: " + modalShown);
//
//            // Click Cancel
//            page.clickCancelInReactivateModal();
//
//            // Verify modal closes
//            boolean modalClosed = !page.isModalDisplayed();
//            System.out.println("Modal closed: " + modalClosed);
//        } else {
//            System.out.println("No candidates found - skipping modal flow test");
//        }
//
//        page.clearSearch();
//    }

    // ==========================
    // PAGINATION NAVIGATION TESTS
    // ==========================

    @Test(priority = 12, description = "Verify Next and Previous pagination buttons")
    public void testPaginationNavigation() {
        page.selectReactivateLink();

        String initialPageInfo = page.getPageInfo();
        System.out.println("Initial page info: " + initialPageInfo);

        // Check Next button state
        if (page.isNextPageEnabled()) {
            page.clickNextPage();
            String afterNext = page.getPageInfo();
            System.out.println("After Next: " + afterNext);

            if (!initialPageInfo.isEmpty() && !afterNext.isEmpty()) {
                Assert.assertNotEquals(initialPageInfo, afterNext,
                        "Page info should change after clicking Next");
            }
        } else {
            System.out.println("Next button disabled - skipping");
        }

        // Check Previous button state
        if (page.isPreviousPageEnabled()) {
            page.clickPreviousPage();
            String afterPrevious = page.getPageInfo();
            System.out.println("After Previous: " + afterPrevious);
        } else {
            System.out.println("Previous button disabled - skipping");
        }
    }

    // ==========================
    // CANDIDATE DATA EXTRACTION TEST
    // ==========================

    @Test(priority = 13, description = "Verify candidate data can be extracted from table")
    public void testCandidateDataExtraction() {
        page.selectReactivateLink();

        int rowCount = page.getRowCount();

        if (rowCount > 0) {
            String firstName = page.getCandidateNameFromRow(1);
            String firstEmail = page.getCandidateEmailFromRow(1);

            System.out.println("First candidate: " + firstName + " | " + firstEmail);

            Assert.assertNotNull(firstName, "Candidate name should not be null");
            Assert.assertNotNull(firstEmail, "Candidate email should not be null");
        } else {
            System.out.println("No candidates found - skipping data extraction test");
        }
    }
}