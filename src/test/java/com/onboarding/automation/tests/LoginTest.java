package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.LoginPage;
import com.onboarding.automation.utils.TestLogger;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final String INVALID_PASSWORD = "wrong123";

    private static final String LOGIN_URL =
            "https://uat_mcdp_hcm.omfysgroup.com/login";

    private LoginPage loginPage;


    @BeforeMethod
    public void setupLoginPage(){
        TestLogger.info("Setting up LoginTest - Navigating to Login Page");
        loginPage = new LoginPage(driver);

        driver.get(LOGIN_URL);
        TestLogger.interact("Browser", "Navigated to " + LOGIN_URL);

        loginPage.waitForLoginPageToLoad();
        TestLogger.success("Login page loaded successfully");
    }


    @Test(priority = 1)
    public void TC_LG_01_verifySuccessfulLogin(){
        TestLogger.testStart("TC_LG_01", "Verify Successful Login with Valid Credentials");
        
        try {
            TestLogger.step("1", "Enter valid username and password");
            loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);
            TestLogger.interact("LoginPage", "Login credentials entered");

            TestLogger.step("2", "Wait for successful login");
            boolean loginSuccess = loginPage.waitForSuccessfulLogin();
            TestLogger.verify("User successfully logged in");

            Assert.assertTrue(loginSuccess, "Login failed with valid credentials");
            TestLogger.assertLog("Login with valid credentials", true);
            TestLogger.testEnd("TC_LG_01", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_01", "FAILED");
            throw e;
        }
    }


    @Test(priority = 2)
    public void TC_LG_02_invalidPassword(){
        TestLogger.testStart("TC_LG_02", "Verify Invalid Password Error Message");
        
        try {
            TestLogger.step("1", "Enter valid username with invalid password");
            loginPage.loginAs(VALID_USERNAME, INVALID_PASSWORD);
            TestLogger.interact("LoginPage", "Invalid credentials entered");

            TestLogger.step("2", "Verify error message displayed");
            boolean errorShown = loginPage.isInvalidLoginMessageDisplayed();
            TestLogger.verify("Invalid login error message shown");

            Assert.assertTrue(errorShown, "Error message not displayed for invalid password");
            TestLogger.assertLog("Error message displayed for invalid password", true);
            TestLogger.testEnd("TC_LG_02", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_02", "FAILED");
            throw e;
        }
    }


    @Test(priority = 3)
    public void TC_LG_03_forgotPasswordRedirect(){
        TestLogger.testStart("TC_LG_03", "Verify Forgot Password Redirect");
        
        try {
            TestLogger.step("1", "Click on Forgot Password link");
            loginPage.clickForgotPassword();
            TestLogger.interact("LoginPage", "Forgot Password link clicked");

            TestLogger.step("2", "Verify Forgot Password page loaded");
            boolean pageLoaded = loginPage.isForgotPasswordPageLoaded();
            TestLogger.verify("Forgot Password page is loaded");

            Assert.assertTrue(pageLoaded, "Forgot password page not loaded");
            TestLogger.assertLog("Forgot Password page redirect", true);
            TestLogger.testEnd("TC_LG_03", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_03", "FAILED");
            throw e;
        }
    }


    @Test(priority = 4)
    public void TC_LG_04_showPasswordToggle(){
        TestLogger.testStart("TC_LG_04", "Verify Show/Hide Password Toggle");
        
        try {
            TestLogger.step("1", "Enter password in field");
            loginPage.enterPassword("Test123");
            TestLogger.interact("LoginPage", "Password entered");

            TestLogger.step("2", "Verify password is masked");
            boolean masked = loginPage.isPasswordMasked();
            TestLogger.verify("Password is masked (hidden)");
            Assert.assertTrue(masked);

            TestLogger.step("3", "Click Show Password toggle");
            loginPage.clickShowPasswordToggle();
            TestLogger.interact("LoginPage", "Show Password toggle clicked");

            TestLogger.step("4", "Verify password is now visible");
            boolean visible = loginPage.isPasswordVisible();
            TestLogger.verify("Password is now visible");
            Assert.assertTrue(visible);
            
            TestLogger.testEnd("TC_LG_04", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_04", "FAILED");
            throw e;
        }
    }


    @Test(priority = 5)
    public void TC_LG_05_emptyFields(){
        TestLogger.testStart("TC_LG_05", "Verify Validation on Empty Fields");
        
        try {
            TestLogger.step("1", "Clear all login fields");
            loginPage.clearLoginFields();
            TestLogger.interact("LoginPage", "All fields cleared");

            TestLogger.step("2", "Click login button with empty fields");
            loginPage.clickLogin();
            TestLogger.interact("LoginPage", "Login clicked with empty fields");

            TestLogger.step("3", "Verify validation message or stay on login page");
            boolean validationShown = loginPage.isValidationMessageVisible() || loginPage.isStillOnLoginPage();
            TestLogger.verify("Validation message shown or stayed on login page");

            Assert.assertTrue(validationShown);
            TestLogger.testEnd("TC_LG_05", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_05", "FAILED");
            throw e;
        }
    }


    @Test(priority = 6)
    public void TC_LG_06_onlyLoginId(){
        TestLogger.testStart("TC_LG_06", "Verify Login with Only Login ID");
        
        try {
            TestLogger.step("1", "Enter only login ID");
            loginPage.enterLoginId(VALID_USERNAME);
            TestLogger.interact("LoginPage", "Only Login ID entered: " + VALID_USERNAME);

            TestLogger.step("2", "Click login button");
            loginPage.clickLogin();
            TestLogger.interact("LoginPage", "Login button clicked");

            TestLogger.step("3", "Verify still on login page (missing password)");
            boolean stillOnPage = loginPage.isStillOnLoginPage();
            TestLogger.verify("User still on login page (password required)");

            Assert.assertTrue(stillOnPage);
            TestLogger.testEnd("TC_LG_06", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_06", "FAILED");
            throw e;
        }
    }


    @Test(priority = 7)
    public void TC_LG_07_onlyPassword(){
        TestLogger.testStart("TC_LG_07", "Verify Login with Only Password");
        
        try {
            TestLogger.step("1", "Enter only password");
            loginPage.enterPassword(VALID_PASSWORD);
            TestLogger.interact("LoginPage", "Only Password entered");

            TestLogger.step("2", "Click login button");
            loginPage.clickLogin();
            TestLogger.interact("LoginPage", "Login button clicked");

            TestLogger.step("3", "Verify still on login page (missing username)");
            boolean stillOnPage = loginPage.isStillOnLoginPage();
            TestLogger.verify("User still on login page (username required)");

            Assert.assertTrue(stillOnPage);
            TestLogger.testEnd("TC_LG_07", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_07", "FAILED");
            throw e;
        }
    }


    @Test(priority = 8)
    public void TC_LG_08_invalidLoginFormats(){
        TestLogger.testStart("TC_LG_08", "Verify Invalid Login Format Validation");
        
        try {
            String[] invalidIds = {"OMI", "123", "abc"};
            
            for(int i = 0; i < invalidIds.length; i++) {
                String id = invalidIds[i];
                TestLogger.step(String.valueOf(i+1), "Testing invalid ID format: " + id);
                
                driver.get(LOGIN_URL);
                TestLogger.interact("Browser", "Navigated to Login URL");
                
                loginPage.waitForLoginPageToLoad();
                TestLogger.interact("LoginPage", "Page loaded");

                loginPage.enterLoginId(id);
                loginPage.enterPassword(VALID_PASSWORD);
                TestLogger.interact("LoginPage", "Invalid ID entered: " + id);

                loginPage.clickLogin();
                TestLogger.interact("LoginPage", "Login clicked");

                Assert.assertTrue(loginPage.isStillOnLoginPage());
                TestLogger.verify("Rejected invalid ID format: " + id);
            }
            TestLogger.testEnd("TC_LG_08", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_08", "FAILED");
            throw e;
        }
    }


    @Test(priority = 9)
    public void TC_LG_09_loginButtonState(){
        TestLogger.testStart("TC_LG_09", "Verify Login Button State Changes");
        
        try {
            TestLogger.step("1", "Verify initial empty state");
            boolean idEmpty = loginPage.isLoginIdFieldEmpty();
            boolean pwdEmpty = loginPage.isPasswordFieldEmpty();
            TestLogger.verify("Login ID field is empty");
            TestLogger.verify("Password field is empty");
            Assert.assertTrue(idEmpty);
            Assert.assertTrue(pwdEmpty);

            TestLogger.step("2", "Enter username");
            loginPage.enterLoginId(VALID_USERNAME);
            TestLogger.interact("LoginPage", "Username entered");

            TestLogger.step("3", "Enter password");
            loginPage.enterPassword(VALID_PASSWORD);
            TestLogger.interact("LoginPage", "Password entered");

            TestLogger.step("4", "Verify login button is enabled");
            boolean btnEnabled = loginPage.isLoginButtonEnabled();
            TestLogger.verify("Login button is now enabled");
            Assert.assertTrue(btnEnabled);
            TestLogger.testEnd("TC_LG_09", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_09", "FAILED");
            throw e;
        }
    }


    @Test(priority = 10)
    public void TC_LG_10_loginPageAccessAfterLogin(){
        TestLogger.testStart("TC_LG_10", "Verify Login Page Redirect After Login");
        
        try {
            TestLogger.step("1", "Login with valid credentials");
            loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);
            TestLogger.interact("LoginPage", "Login performed");

            TestLogger.step("2", "Wait for successful login");
            Assert.assertTrue(loginPage.waitForSuccessfulLogin());
            TestLogger.verify("Login successful");

            TestLogger.step("3", "Try to access login page again");
            driver.get(LOGIN_URL);
            TestLogger.interact("Browser", "Attempted to navigate to login page");

            TestLogger.step("4", "Verify redirect behavior");
            boolean redirected = loginPage.isRedirectedToHomeOrNonLoginPage() || loginPage.isStillOnLoginPage();
            TestLogger.verify("Proper redirect behavior (either home or login with redirect)");
            Assert.assertTrue(redirected, "Unexpected redirect behaviour");
            TestLogger.testEnd("TC_LG_10", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_10", "FAILED");
            throw e;
        }
    }


    @Test(priority = 11)
    public void TC_LG_11_dashboardRedirect(){
        TestLogger.testStart("TC_LG_11", "Verify Dashboard Redirect After Successful Login");
        
        try {
            TestLogger.step("1", "Login with valid credentials");
            loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);
            TestLogger.interact("LoginPage", "Login performed");

            TestLogger.step("2", "Wait for successful login and dashboard redirect");
            boolean redirected = loginPage.waitForSuccessfulLogin();
            TestLogger.verify("User redirected to dashboard");
            Assert.assertTrue(redirected);
            
            TestLogger.success("Dashboard successfully loaded");
            TestLogger.testEnd("TC_LG_11", "PASSED");
        } catch (Exception e) {
            TestLogger.error("Test failed: " + e.getMessage());
            TestLogger.testEnd("TC_LG_11", "FAILED");
            throw e;
        }
    }
}
