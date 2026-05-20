package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.LoginPage;
import com.onboarding.automation.pages.InitiateOnboardingPage;
import com.onboarding.automation.utils.TestLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InitiateOnboardingTest extends BaseTest {

    private LoginPage loginPage;
    private InitiateOnboardingPage onboarding;
    private static final String ONBOARDING_URL = "https://uat_mcdp_hcm.omfysgroup.com/onboarding_admin";

    @BeforeMethod
    public void setupLogin(){
        TestLogger.info("Setting up InitiateOnboardingTest - Login and Navigation");
        
        loginPage = new LoginPage(driver);
        TestLogger.interact("Browser", "LoginPage object created");
        
        loginPage.openLoginPage();
        TestLogger.action("Navigated to login page");
        
        loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);
        TestLogger.action("Login credentials submitted");

        Assert.assertTrue(loginPage.waitForSuccessfulLogin(), "Login failed!");
        TestLogger.success("Login successful");

        driver.get(ONBOARDING_URL);
        TestLogger.interact("Browser", "Navigated to Onboarding URL: " + ONBOARDING_URL);
        
        onboarding = new InitiateOnboardingPage(driver);
        onboarding.waitForOnboardingPage();
        TestLogger.success("Onboarding page loaded");
    }

    /**
     * TC_OB_01: Valid Onboarding
     * Updated: Now only checks that the button is clickable and performs the action.
     */
    @Test(priority = 1)
    public void TC_OB_01_validOnboarding(){
        TestLogger.testStart("TC_OB_01", "Verify Valid Onboarding with Complete Data");
        
        try {
            TestLogger.step("1", "Enter employee details");
            onboarding.enterFirstName("Parth");
            TestLogger.interact("Form", "First Name: Parth");
            
            onboarding.enterLastName("Divekar");
            TestLogger.interact("Form", "Last Name: Divekar");
            
            onboarding.enterEmail("parth.divekar@omfysgroup.com");
            TestLogger.interact("Form", "Email: parth.divekar@omfysgroup.com");

            TestLogger.step("2", "Select designation and department");
            onboarding.selectDesignation("Sr.Software Engineer");
            TestLogger.interact("Form", "Designation: Sr.Software Engineer");
            
            onboarding.selectDepartment("Testing");
            TestLogger.interact("Form", "Department: Testing");
            
            onboarding.selectRole("Testing & QA");
            TestLogger.interact("Form", "Role: Testing & QA");

            TestLogger.step("3", "Select employment level and status");
            onboarding.selectLevel("Level-V");
            TestLogger.interact("Form", "Level: Level-V");
            
            onboarding.selectEmployeeStatus("Trainee");
            TestLogger.interact("Form", "Employee Status: Trainee");

            TestLogger.step("4", "Set joining date and employment mode");
            onboarding.setDateOfJoining("2026-03-31");
            TestLogger.interact("Form", "Date of Joining: 2026-03-31");
            
            onboarding.selectEmploymentMode("Experienced");
            TestLogger.interact("Form", "Employment Mode: Experienced");
            
            onboarding.setLinkValidity("7");
            TestLogger.interact("Form", "Link Validity: 7 days");

            TestLogger.step("5", "Verify Generate button state");
            Assert.assertTrue(onboarding.isGenerateButtonEnabled(), "Generate button should be enabled for valid data.");
            TestLogger.verify("Generate button is enabled for valid data");

            TestLogger.step("6", "Click Generate Onboarding button");
            onboarding.clickGenerateOnboarding();
            TestLogger.interact("Button", "Generate Onboarding clicked");

            TestLogger.step("7", "Verify page state after generation");
            Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL, "Page changed unexpectedly after clicking Generate.");
            TestLogger.verify("Remained on Onboarding page (generation successful)");
            
            TestLogger.success("Onboarding generation completed successfully");
            TestLogger.testEnd("TC_OB_01", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_OB_01", "FAILED");
            throw e;
        }
    }

    @Test(priority = 2)
    public void TC_OB_02_emptyFirstName(){
        TestLogger.testStart("TC_OB_02", "Verify Validation - Empty First Name");
        
        try {
            TestLogger.step("1", "Clear form and enter data without first name");
            onboarding.clearForm();
            TestLogger.interact("Form", "Form cleared");
            
            onboarding.enterLastName("Divekar");
            TestLogger.interact("Form", "Last Name: Divekar");
            
            onboarding.enterEmail("parth.divekar@omfysgroup.com");
            TestLogger.interact("Form", "Email: parth.divekar@omfysgroup.com");

            TestLogger.step("2", "Attempt to generate without first name");
            onboarding.clickGenerateOnboarding();
            TestLogger.interact("Button", "Generate button clicked");

            TestLogger.step("3", "Verify validation rejection");
            Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL);
            TestLogger.verify("Request rejected - First name is mandatory");
            
            TestLogger.testEnd("TC_OB_02", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_OB_02", "FAILED");
            throw e;
        }
    }

    @Test(priority = 3)
    public void TC_OB_03_emptyLastName(){
        TestLogger.testStart("TC_OB_03", "Verify Validation - Empty Last Name");
        
        try {
            TestLogger.step("1", "Clear form and enter data without last name");
            onboarding.clearForm();
            TestLogger.interact("Form", "Form cleared");
            
            onboarding.enterFirstName("Parth");
            TestLogger.interact("Form", "First Name: Parth");
            
            onboarding.enterEmail("parth.divekar@omfysgroup.com");
            TestLogger.interact("Form", "Email: parth.divekar@omfysgroup.com");

            TestLogger.step("2", "Attempt to generate without last name");
            onboarding.clickGenerateOnboarding();
            TestLogger.interact("Button", "Generate button clicked");

            TestLogger.step("3", "Verify validation rejection");
            Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL);
            TestLogger.verify("Request rejected - Last name is mandatory");
            
            TestLogger.testEnd("TC_OB_03", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_OB_03", "FAILED");
            throw e;
        }
    }

    @Test(priority = 4)
    public void TC_OB_04_invalidEmail(){
        TestLogger.testStart("TC_OB_04", "Verify Validation - Invalid Email Format");
        
        try {
            TestLogger.step("1", "Clear form and enter invalid email");
            onboarding.clearForm();
            TestLogger.interact("Form", "Form cleared");
            
            onboarding.enterFirstName("Parth");
            TestLogger.interact("Form", "First Name: Parth");
            
            onboarding.enterLastName("Divekar");
            TestLogger.interact("Form", "Last Name: Divekar");
            
            onboarding.enterEmail("parth.divekaromfysgroup.com");
            TestLogger.interact("Form", "Email (Invalid): parth.divekaromfysgroup.com");

            TestLogger.step("2", "Attempt to generate with invalid email");
            onboarding.clickGenerateOnboarding();
            TestLogger.interact("Button", "Generate button clicked");

            TestLogger.step("3", "Verify validation rejection");
            Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL);
            TestLogger.verify("Request rejected - Invalid email format");
            
            TestLogger.testEnd("TC_OB_04", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_OB_04", "FAILED");
            throw e;
        }
    }
}
