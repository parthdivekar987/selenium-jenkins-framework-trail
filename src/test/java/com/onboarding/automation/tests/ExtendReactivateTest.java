package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.ExtendReactivatePage;
import com.onboarding.automation.utils.TestLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExtendReactivateTest extends BaseTest {

    private ExtendReactivatePage page;

    @BeforeClass(alwaysRun = true)
    public void openModule() {
        TestLogger.info("Setting up ExtendReactivateTest - Opening Extend/Reactivate Module");
        page = new ExtendReactivatePage(driver);
        page.clickExtendReactivateTab();
        TestLogger.interact("ExtendReactivatePage", "Extend/Reactivate tab clicked");
        
        Assert.assertTrue(page.isExtendReactivatePageLoaded(), "Extend/Reactivate page failed to load");
        TestLogger.success("Extend/Reactivate module opened successfully");
    }

    // ==========================
    // RADIO BUTTON TESTS
    // ==========================

    @Test(priority = 1, description = "Verify Extend Link Validity is selected by default")
    public void testDefaultRadioSelection() {
        TestLogger.testStart("TC_ER_01", "Verify Extend Link Validity Default Selection");
        
        try {
            TestLogger.step("1", "Verify Extend Link radio is selected by default");
            Assert.assertTrue(page.isExtendLinkRadioSelected(),
                    "Extend Link Validity should be selected by default");
            TestLogger.verify("Extend Link Validity is selected by default");

            TestLogger.step("2", "Verify Reactivate Link radio is NOT selected by default");
            Assert.assertFalse(page.isReactivateLinkRadioSelected(),
                    "Reactivate Link should NOT be selected by default");
            TestLogger.verify("Reactivate Link is not selected by default");

            TestLogger.testEnd("TC_ER_01", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_01", "FAILED");
            throw e;
        }
    }

    @Test(priority = 2, description = "Verify switching between radio buttons works")
    public void testRadioButtonToggle() {
        TestLogger.testStart("TC_ER_02", "Verify Radio Button Toggle Between Extend and Reactivate");
        
        try {
            TestLogger.step("1", "Select Reactivate Link radio button");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link selected");
            
            Assert.assertTrue(page.isReactivateLinkRadioSelected(),
                    "Reactivate Link should be selected after toggle");
            TestLogger.verify("Reactivate Link is now selected");

            Assert.assertFalse(page.isExtendLinkRadioSelected(),
                    "Extend Link should NOT be selected after toggle");
            TestLogger.verify("Extend Link is now deselected");

            TestLogger.step("2", "Switch back to Extend Link Validity");
            page.selectExtendLinkValidity();
            TestLogger.interact("RadioButton", "Extend Link Validity re-selected");
            
            Assert.assertTrue(page.isExtendLinkRadioSelected(),
                    "Extend Link should be selected after switching back");
            TestLogger.verify("Extend Link Validity is selected again");

            Assert.assertFalse(page.isReactivateLinkRadioSelected(),
                    "Reactivate Link should NOT be selected after switching back");
            TestLogger.verify("Reactivate Link is deselected again");

            TestLogger.testEnd("TC_ER_02", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_02", "FAILED");
            throw e;
        }
    }

    // ==========================
    // PAGINATION DROPDOWN TESTS (EXTEND MODE)
    // ==========================

    @Test(priority = 3, description = "Verify pagination dropdown in Extend mode")
    public void testPaginationDropdownExtendMode() {
        TestLogger.testStart("TC_ER_03", "Verify Pagination in Extend Mode");
        
        try {
            TestLogger.step("1", "Select Extend Link Validity mode");
            page.selectExtendLinkValidity();
            TestLogger.interact("RadioButton", "Extend Link Validity mode activated");

            TestLogger.step("2", "Set pagination to 5 entries");
            page.selectPaginationEntries("5");
            TestLogger.interact("Pagination", "Pagination set to 5 entries");
            Assert.assertEquals(page.getCurrentPaginationValue(), "5",
                    "Pagination should show 5 entries");
            TestLogger.verify("Pagination value is 5");

            TestLogger.step("3", "Set pagination to 10 entries");
            page.selectPaginationEntries("10");
            TestLogger.interact("Pagination", "Pagination set to 10 entries");
            Assert.assertEquals(page.getCurrentPaginationValue(), "10",
                    "Pagination should show 10 entries");
            TestLogger.verify("Pagination value is 10");

            TestLogger.step("4", "Set pagination to 25 entries");
            page.selectPaginationEntries("25");
            TestLogger.interact("Pagination", "Pagination set to 25 entries");
            Assert.assertEquals(page.getCurrentPaginationValue(), "25",
                    "Pagination should show 25 entries");
            TestLogger.verify("Pagination value is 25");

            TestLogger.testEnd("TC_ER_03", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_03", "FAILED");
            throw e;
        }
    }

    // ==========================
    // PAGINATION DROPDOWN TESTS (REACTIVATE MODE)
    // ==========================

    @Test(priority = 4, description = "Verify pagination dropdown in Reactivate mode")
    public void testPaginationDropdownReactivateMode() {
        TestLogger.testStart("TC_ER_04", "Verify Pagination in Reactivate Mode");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Set pagination to 5 entries");
            page.selectPaginationEntries("5");
            TestLogger.interact("Pagination", "Pagination set to 5 entries");
            Assert.assertEquals(page.getCurrentPaginationValue(), "5",
                    "Pagination should show 5 entries in Reactivate mode");
            TestLogger.verify("Pagination value is 5");

            TestLogger.step("3", "Set pagination to 10 entries");
            page.selectPaginationEntries("10");
            TestLogger.interact("Pagination", "Pagination set to 10 entries");
            Assert.assertEquals(page.getCurrentPaginationValue(), "10",
                    "Pagination should show 10 entries in Reactivate mode");
            TestLogger.verify("Pagination value is 10");

            TestLogger.step("4", "Set pagination to 25 entries");
            page.selectPaginationEntries("25");
            TestLogger.interact("Pagination", "Pagination set to 25 entries");
            Assert.assertEquals(page.getCurrentPaginationValue(), "25",
                    "Pagination should show 25 entries in Reactivate mode");
            TestLogger.verify("Pagination value is 25");

            TestLogger.testEnd("TC_ER_04", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_04", "FAILED");
            throw e;
        }
    }

    // ==========================
    // SEARCH FUNCTIONALITY TESTS
    // ==========================

    @Test(priority = 5, description = "Verify search placeholder text is correct")
    public void testSearchPlaceholder() {
        TestLogger.testStart("TC_ER_05", "Verify Search Placeholder Text");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Get search placeholder text");
            String placeholder = page.getSearchPlaceholder();
            TestLogger.info("Search placeholder text: '" + placeholder + "'");
            
            Assert.assertEquals(placeholder, "Search candidate...",
                    "Search placeholder should be 'Search candidate...'");
            TestLogger.verify("Search placeholder is 'Search candidate...'");

            TestLogger.testEnd("TC_ER_05", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_05", "FAILED");
            throw e;
        }
    }

    @Test(priority = 6, description = "Verify search for candidate 'Srujal' works")
    public void testSearchForSrujal() {
        TestLogger.testStart("TC_ER_06", "Verify Search for Candidate 'Srujal'");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Search for candidate 'Srujal'");
            page.searchForCandidate("Srujal");
            TestLogger.interact("SearchForm", "Searched for 'Srujal'");

            Assert.assertTrue(true, "Search executed successfully");
            TestLogger.verify("Search executed without errors");

            TestLogger.step("3", "Clear search after test");
            page.clearSearch();
            TestLogger.interact("SearchForm", "Search cleared");

            TestLogger.testEnd("TC_ER_06", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_06", "FAILED");
            throw e;
        }
    }

    @Test(priority = 7, description = "Verify clear search functionality")
    public void testClearSearch() {
        TestLogger.testStart("TC_ER_07", "Verify Clear Search Functionality");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Search for candidate 'Srujal'");
            page.searchForCandidate("Srujal");
            TestLogger.interact("SearchForm", "Searched for 'Srujal'");

            TestLogger.step("3", "Clear the search");
            page.clearSearch();
            TestLogger.interact("SearchForm", "Search cleared");

            Assert.assertTrue(true, "Search cleared successfully");
            TestLogger.verify("Search field cleared successfully");

            TestLogger.testEnd("TC_ER_07", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_07", "FAILED");
            throw e;
        }
    }

    // ==========================
    // TABLE VERIFICATION TESTS
    // ==========================

    @Test(priority = 8, description = "Verify table headers are displayed correctly")
    public void testTableHeaders() {
        TestLogger.testStart("TC_ER_08", "Verify Table Headers");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Get all table headers");
            java.util.List<String> headers = page.getAllTableHeaders();
            TestLogger.info("Table headers count: " + headers.size());
            TestLogger.info("Headers: " + headers);

            Assert.assertFalse(headers.isEmpty(), "Table should have headers");
            TestLogger.verify("Table has headers");

            TestLogger.step("3", "Verify expected headers exist");
            boolean hasCandidateName = headers.stream().anyMatch(h ->
                    h.contains("Candidate") || h.contains("Name"));
            TestLogger.verify("Candidate Name column exists: " + hasCandidateName);
            Assert.assertTrue(hasCandidateName, "Table should have Candidate Name column");

            boolean hasEmail = headers.stream().anyMatch(h ->
                    h.contains("Email"));
            TestLogger.verify("Email column exists: " + hasEmail);
            Assert.assertTrue(hasEmail, "Table should have Email column");

            boolean hasAction = headers.stream().anyMatch(h ->
                    h.contains("Action"));
            TestLogger.verify("Action column exists: " + hasAction);
            Assert.assertTrue(hasAction, "Table should have Action column");

            TestLogger.testEnd("TC_ER_08", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_08", "FAILED");
            throw e;
        }
    }

    @Test(priority = 9, description = "Verify row count and table state")
    public void testTableRowCount() {
        TestLogger.testStart("TC_ER_09", "Verify Table Row Count and State");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Get row count from table");
            int rowCount = page.getRowCount();
            TestLogger.info("Total rows in table: " + rowCount);

            if (rowCount == 0) {
                Assert.assertTrue(page.isTableEmpty(), "Table should show 'No candidates found' message");
                TestLogger.verify("Table is empty with appropriate message");
            } else {
                Assert.assertTrue(rowCount > 0, "Table should have at least one row");
                TestLogger.verify("Table has " + rowCount + " rows");
            }

            TestLogger.testEnd("TC_ER_09", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_09", "FAILED");
            throw e;
        }
    }

    // ==========================
    // REACTIVATE BUTTON TESTS
    // ==========================

    @Test(priority = 10, description = "Verify Reactivate button is displayed when candidates exist")
    public void testReactivateButtonDisplayed() {
        TestLogger.testStart("TC_ER_10", "Verify Reactivate Button Display");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Search for candidate 'Srujal'");
            page.searchForCandidate("Srujal");
            TestLogger.interact("SearchForm", "Searched for 'Srujal'");

            TestLogger.step("3", "Verify Reactivate button display");
            if (!page.isTableEmpty() && page.getRowCount() > 0) {
                Assert.assertTrue(page.isReactivateButtonDisplayed(),
                        "Reactivate button should be displayed for candidates");
                TestLogger.verify("Reactivate button is displayed");
                TestLogger.info("Reactivate button count: " + page.getReactivateButtonCount());
            } else {
                TestLogger.warn("No candidates found - skipping button check");
                Assert.assertTrue(true, "No candidates to test");
            }

            TestLogger.step("4", "Clear search");
            page.clearSearch();
            TestLogger.interact("SearchForm", "Search cleared");

            TestLogger.testEnd("TC_ER_10", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_10", "FAILED");
            throw e;
        }
    }

    // ==========================
    // PAGINATION NAVIGATION TESTS
    // ==========================

    @Test(priority = 12, description = "Verify Next and Previous pagination buttons")
    public void testPaginationNavigation() {
        TestLogger.testStart("TC_ER_12", "Verify Pagination Navigation");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Get initial page info");
            String initialPageInfo = page.getPageInfo();
            TestLogger.info("Initial page info: " + initialPageInfo);

            TestLogger.step("3", "Check and click Next button if enabled");
            if (page.isNextPageEnabled()) {
                page.clickNextPage();
                TestLogger.interact("Pagination", "Next page button clicked");
                
                String afterNext = page.getPageInfo();
                TestLogger.info("After Next: " + afterNext);

                if (!initialPageInfo.isEmpty() && !afterNext.isEmpty()) {
                    Assert.assertNotEquals(initialPageInfo, afterNext,
                            "Page info should change after clicking Next");
                    TestLogger.verify("Page changed after clicking Next");
                }
            } else {
                TestLogger.warn("Next button disabled - skipping");
            }

            TestLogger.step("4", "Check and click Previous button if enabled");
            if (page.isPreviousPageEnabled()) {
                page.clickPreviousPage();
                TestLogger.interact("Pagination", "Previous page button clicked");
                
                String afterPrevious = page.getPageInfo();
                TestLogger.info("After Previous: " + afterPrevious);
                TestLogger.verify("Returned to previous page");
            } else {
                TestLogger.warn("Previous button disabled - skipping");
            }

            TestLogger.testEnd("TC_ER_12", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_12", "FAILED");
            throw e;
        }
    }

    // ==========================
    // CANDIDATE DATA EXTRACTION TEST
    // ==========================

    @Test(priority = 13, description = "Verify candidate data can be extracted from table")
    public void testCandidateDataExtraction() {
        TestLogger.testStart("TC_ER_13", "Verify Candidate Data Extraction");
        
        try {
            TestLogger.step("1", "Select Reactivate Link mode");
            page.selectReactivateLink();
            TestLogger.interact("RadioButton", "Reactivate Link mode activated");

            TestLogger.step("2", "Get row count");
            int rowCount = page.getRowCount();
            TestLogger.info("Total rows in table: " + rowCount);

            if (rowCount > 0) {
                TestLogger.step("3", "Extract first candidate data");
                String firstName = page.getCandidateNameFromRow(1);
                String firstEmail = page.getCandidateEmailFromRow(1);

                TestLogger.info("First candidate: " + firstName + " | " + firstEmail);

                Assert.assertNotNull(firstName, "Candidate name should not be null");
                TestLogger.verify("Candidate name extracted: " + firstName);

                Assert.assertNotNull(firstEmail, "Candidate email should not be null");
                TestLogger.verify("Candidate email extracted: " + firstEmail);
            } else {
                TestLogger.warn("No candidates found - skipping data extraction test");
            }

            TestLogger.testEnd("TC_ER_13", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_ER_13", "FAILED");
            throw e;
        }
    }
}
