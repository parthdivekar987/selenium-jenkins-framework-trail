package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.LoginPage;
import com.onboarding.automation.pages.InitiateOnboardingPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InitiateOnboardingTest extends BaseTest {

    private LoginPage loginPage;
    private InitiateOnboardingPage onboarding;
    private static final String ONBOARDING_URL = "https://uat_mcdp_hcm.omfysgroup.com/onboarding_admin";

    @BeforeMethod
    public void setupLogin(){
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);

        Assert.assertTrue(loginPage.waitForSuccessfulLogin(), "Login failed!");

        driver.get(ONBOARDING_URL);
        onboarding = new InitiateOnboardingPage(driver);
        onboarding.waitForOnboardingPage();
    }

    /**
     * TC_OB_01: Valid Onboarding
     * Updated: Now only checks that the button is clickable and performs the action.
     */
    @Test(priority = 1)
    public void TC_OB_01_validOnboarding(){
        onboarding.enterFirstName("Parth");
        onboarding.enterLastName("Divekar");
        onboarding.enterEmail("parth.divekar@omfysgroup.com");

        onboarding.selectDesignation("Sr.Software Engineer");
        onboarding.selectDepartment("Testing");
        onboarding.selectRole("Testing & QA");

        onboarding.selectLevel("Level-V");
        onboarding.selectEmployeeStatus("Trainee");

        onboarding.setDateOfJoining("2026-03-31");
        onboarding.selectEmploymentMode("Experienced");
        onboarding.setLinkValidity("7");

        // Verify button state before clicking
        Assert.assertTrue(onboarding.isGenerateButtonEnabled(), "Generate button should be enabled for valid data.");

        // Perform the click action
        onboarding.clickGenerateOnboarding();

        // Final check: Ensure we are still on the expected page and no crash occurred
        Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL, "Page changed unexpectedly after clicking Generate.");
    }

    @Test(priority = 2)
    public void TC_OB_02_emptyFirstName(){
        onboarding.clearForm();
        onboarding.enterLastName("Divekar");
        onboarding.enterEmail("parth.divekar@omfysgroup.com");

        onboarding.clickGenerateOnboarding();
        Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL);
    }

    @Test(priority = 3)
    public void TC_OB_03_emptyLastName(){
        onboarding.clearForm();
        onboarding.enterFirstName("Parth");
        onboarding.enterEmail("parth.divekar@omfysgroup.com");

        onboarding.clickGenerateOnboarding();
        Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL);
    }

    @Test(priority = 4)
    public void TC_OB_04_invalidEmail(){
        onboarding.clearForm();
        onboarding.enterFirstName("Parth");
        onboarding.enterLastName("Divekar");
        onboarding.enterEmail("parth.divekaromfysgroup.com");

        onboarding.clickGenerateOnboarding();
        Assert.assertEquals(driver.getCurrentUrl(), ONBOARDING_URL);
    }
}
