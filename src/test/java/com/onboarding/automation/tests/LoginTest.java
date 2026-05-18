package com.onboarding.automation.tests;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final String VALID_LOGIN_ID = "OMI-0076";
    private static final String VALID_PASSWORD = "Omfys@123";
    private static final String INVALID_PASSWORD = "wrong123";

    private static final String LOGIN_URL =
            "https://uat_mcdp_hcm.omfysgroup.com/login";

    private LoginPage loginPage;


    @BeforeMethod
    public void setupLoginPage(){

        loginPage = new LoginPage(driver);

        driver.get(LOGIN_URL);

        loginPage.waitForLoginPageToLoad();
    }


    @Test(priority = 1)
    public void TC_LG_01_verifySuccessfulLogin(){

        loginPage.loginAs(VALID_LOGIN_ID, VALID_PASSWORD);

        Assert.assertTrue(
                loginPage.waitForSuccessfulLogin(),
                "Login failed with valid credentials");
    }


    @Test(priority = 2)
    public void TC_LG_02_invalidPassword(){

        loginPage.loginAs(VALID_LOGIN_ID, INVALID_PASSWORD);

        Assert.assertTrue(
                loginPage.isInvalidLoginMessageDisplayed(),
                "Error message not displayed for invalid password");
    }


    @Test(priority = 3)
    public void TC_LG_03_forgotPasswordRedirect(){

        loginPage.clickForgotPassword();

        Assert.assertTrue(
                loginPage.isForgotPasswordPageLoaded(),
                "Forgot password page not loaded");
    }


    @Test(priority = 4)
    public void TC_LG_04_showPasswordToggle(){

        loginPage.enterPassword("Test123");

        Assert.assertTrue(loginPage.isPasswordMasked());

        loginPage.clickShowPasswordToggle();

        Assert.assertTrue(loginPage.isPasswordVisible());
    }


    @Test(priority = 5)
    public void TC_LG_05_emptyFields(){

        loginPage.clearLoginFields();

        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.isValidationMessageVisible() ||
                        loginPage.isStillOnLoginPage());
    }


    @Test(priority = 6)
    public void TC_LG_06_onlyLoginId(){

        loginPage.enterLoginId(VALID_LOGIN_ID);

        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isStillOnLoginPage());
    }


    @Test(priority = 7)
    public void TC_LG_07_onlyPassword(){

        loginPage.enterPassword(VALID_PASSWORD);

        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isStillOnLoginPage());
    }


    @Test(priority = 8)
    public void TC_LG_08_invalidLoginFormats(){

        String[] invalidIds = {"OMI", "123", "abc"};

        for(String id : invalidIds){

            driver.get(LOGIN_URL);

            loginPage.waitForLoginPageToLoad();

            loginPage.enterLoginId(id);

            loginPage.enterPassword(VALID_PASSWORD);

            loginPage.clickLogin();

            Assert.assertTrue(loginPage.isStillOnLoginPage());
        }
    }


    @Test(priority = 9)
    public void TC_LG_09_loginButtonState(){

        Assert.assertTrue(loginPage.isLoginIdFieldEmpty());
        Assert.assertTrue(loginPage.isPasswordFieldEmpty());

        loginPage.enterLoginId(VALID_LOGIN_ID);
        loginPage.enterPassword(VALID_PASSWORD);

        Assert.assertTrue(loginPage.isLoginButtonEnabled());
    }


    @Test(priority = 10)
    public void TC_LG_10_loginPageAccessAfterLogin(){

        loginPage.loginAs(VALID_LOGIN_ID, VALID_PASSWORD);

        Assert.assertTrue(loginPage.waitForSuccessfulLogin());

        driver.get(LOGIN_URL);

        Assert.assertTrue(
                loginPage.isRedirectedToHomeOrNonLoginPage()
                        || loginPage.isStillOnLoginPage(),
                "Unexpected redirect behaviour");
    }


    @Test(priority = 11)
    public void TC_LG_11_dashboardRedirect(){

        loginPage.loginAs(VALID_LOGIN_ID, VALID_PASSWORD);

        Assert.assertTrue(loginPage.waitForSuccessfulLogin());
    }
}