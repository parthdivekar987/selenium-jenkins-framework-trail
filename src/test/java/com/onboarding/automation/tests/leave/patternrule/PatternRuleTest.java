package com.onboarding.automation.tests.leave.patternrule;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.leave.LeavePage;
import com.onboarding.automation.pages.leave.patternrule.PatternRulePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PatternRuleTest extends BaseTest {
    private LeavePage leavePage;
    private PatternRulePage patternRulePage;

    @BeforeMethod
    public void setup() {
        driver.get("https://uat_mcdp_hcm.omfysgroup.com/hcm/leave");
        leavePage = new LeavePage(driver);
        leavePage.clickPatternRule();
        patternRulePage = new PatternRulePage(driver);
    }

    @Test(priority = 1, description = "Verify Pattern Rule page loads")
    public void testPatternRulePageLoads() {
        Assert.assertTrue(patternRulePage.isLoaded(), "Pattern Rule page should load");
    }

    @Test(priority = 2, description = "Verify Leave Type dropdown is present")
    public void testLeaveTypeDropdownPresent() {
        Assert.assertTrue(patternRulePage.isLeaveTypeDropdownPresent(), "Leave Type dropdown should be visible");
    }

    @Test(priority = 3, description = "Verify Leave Type dropdown has at least one option")
    public void testLeaveTypeDropdownHasOptions() {
        Assert.assertTrue(patternRulePage.getAllLeaveTypes().size() > 0, "Leave Type dropdown should have options");
    }

    //test case check
    @Test(priority = 4, description = "Verify selecting SL leave type works")
    public void testSelectLeaveTypeSL() {
        patternRulePage.selectLeaveType("SL");
        Assert.assertEquals(patternRulePage.getSelectedLeaveType(), "SL", "Selected leave type should be SL");
    }

    @Test(priority = 5, description = "Verify selecting ML leave type works")
    public void testSelectLeaveTypeML() {
        patternRulePage.selectLeaveType("ML");
        Assert.assertEquals(patternRulePage.getSelectedLeaveType(), "ML", "Selected leave type should be ML");
    }

    // ==========================================
    // Prior Days (Full Day) Tests
    // ==========================================

    @Test(priority = 6, description = "Verify Prior Days (Full Day) input accepts positive integer")
    public void testPriorDaysFullDayPositiveInteger() {
        patternRulePage.enterPriorDaysFullDay("5");
        Assert.assertEquals(patternRulePage.getPriorDaysFullDay(), "5", "Full day prior days should be 5");
    }

    @Test(priority = 7, description = "Verify Prior Days (Full Day) input accepts zero")
    public void testPriorDaysFullDayZero() {
        patternRulePage.enterPriorDaysFullDay("0");
        Assert.assertEquals(patternRulePage.getPriorDaysFullDay(), "0", "Full day prior days should accept 0");
    }

    @Test(priority = 8, description = "Verify Prior Days (Full Day) input accepts decimal value")
    public void testPriorDaysFullDayDecimal() {
        patternRulePage.enterPriorDaysFullDay("1.5");
        Assert.assertEquals(patternRulePage.getPriorDaysFullDay(), "1.5", "Full day prior days should accept decimal");
    }

    // ==========================================
    // Prior Days (Half Day) Tests
    // ==========================================

    @Test(priority = 9, description = "Verify Prior Days (Half Day) input accepts positive integer")
    public void testPriorDaysHalfDayPositiveInteger() {
        patternRulePage.enterPriorDaysHalfDay("3");
        Assert.assertEquals(patternRulePage.getPriorDaysHalfDay(), "3", "Half day prior days should be 3");
    }

    @Test(priority = 10, description = "Verify Prior Days (Half Day) input accepts zero")
    public void testPriorDaysHalfDayZero() {
        patternRulePage.enterPriorDaysHalfDay("0");
        Assert.assertEquals(patternRulePage.getPriorDaysHalfDay(), "0", "Half day prior days should accept 0");
    }

    @Test(priority = 11, description = "Verify Prior Days (Half Day) input accepts decimal")
    public void testPriorDaysHalfDayDecimal() {
        patternRulePage.enterPriorDaysHalfDay("2.5");
        Assert.assertEquals(patternRulePage.getPriorDaysHalfDay(), "2.5", "Half day prior days should accept decimal");
    }

    // ==========================================
    // Negative / Boundary Tests
    // ==========================================

    @Test(priority = 12, description = "Verify Prior Days (Full Day) input handles negative number")
    public void testPriorDaysFullDayNegative() {
        patternRulePage.enterPriorDaysFullDay("-1");
        String value = patternRulePage.getPriorDaysFullDay();
        System.out.println("Full day negative input result: " + value);
        Assert.assertTrue(value.equals("-1") || value.isEmpty() || value.equals("0"), "Negative input handled gracefully");
    }

    @Test(priority = 13, description = "Verify Prior Days (Half Day) input handles negative number")
    public void testPriorDaysHalfDayNegative() {
        patternRulePage.enterPriorDaysHalfDay("-1");
        String value = patternRulePage.getPriorDaysHalfDay();
        System.out.println("Half day negative input result: " + value);
        Assert.assertTrue(value.equals("-1") || value.isEmpty() || value.equals("0"), "Negative input handled gracefully");
    }
}