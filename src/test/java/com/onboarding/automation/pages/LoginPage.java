package com.onboarding.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==========================
    // Locators
    // ==========================

    private final By loginIdField = By.id("loginId");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[@type='submit']");

    private final By forgotPasswordButton =
            By.xpath("//button[contains(text(),'Forgot')]");

    private final By omiCodeField = By.id("omiCode");

    private final By errorMessage =
            By.xpath("//*[contains(text(),'Invalid')]");

    private final By validationMessage =
            By.xpath("//form//p");

    private final By showPasswordToggle =
            By.xpath("//input[@id='password']/following::*[name()='svg'][1]");

    // ==========================
    // Page open / load
    // ==========================

    public void openLoginPage() {
        driver.get("https://uat_mcdp_hcm.omfysgroup.com/login");
        waitForLoginPageToLoad();
    }

    // Inside LoginPage.java
    public void waitForLoginPageToLoad() {
        // Increase to 30 seconds
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        longWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginId")));
    }
    // ==========================
    // Actions
    // ==========================

    public void enterLoginId(String loginId) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginIdField));
        element.clear();
        element.sendKeys(loginId);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void clearLoginFields() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginIdField)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).clear();
    }

    public void loginAs(String loginId, String password) {
        enterLoginId(loginId);
        enterPassword(password);
        clickLogin();
    }

    // ==========================
    // Login result
    // ==========================

    public boolean waitForSuccessfulLogin() {
        try {
            wait.until(ExpectedConditions.not(
                    ExpectedConditions.urlContains("/login")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ==========================
    // Forgot password
    // ==========================

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordButton)).click();
    }

    public boolean isForgotPasswordPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(omiCodeField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Password visibility
    // ==========================

    public void clickShowPasswordToggle() {
        wait.until(ExpectedConditions.elementToBeClickable(showPasswordToggle)).click();
    }

    public boolean isPasswordVisible() {
        try {
            String type = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(passwordField))
                    .getAttribute("type");
            return "text".equalsIgnoreCase(type);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordMasked() {
        try {
            String type = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(passwordField))
                    .getAttribute("type");
            return "password".equalsIgnoreCase(type);
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Validations
    // ==========================

    public boolean isInvalidLoginMessageDisplayed() {
        try {
            return wait.until(
                            ExpectedConditions.visibilityOfElementLocated(errorMessage))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidationMessageVisible() {
        try {
            return wait.until(
                            ExpectedConditions.visibilityOfElementLocated(validationMessage))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ==========================
    // Page state helpers
    // ==========================

    public boolean isStillOnLoginPage() {
        return driver.getCurrentUrl().contains("/login");
    }

    public boolean isRedirectedToHomeOrNonLoginPage() {
        return !driver.getCurrentUrl().contains("/login");
    }

    public boolean isLoginButtonEnabled() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginButtonDisabled() {
        return !isLoginButtonEnabled();
    }

    public boolean isLoginIdFieldEmpty() {
        try {
            String value = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(loginIdField))
                    .getAttribute("value");
            return value == null || value.trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordFieldEmpty() {
        try {
            String value = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(passwordField))
                    .getAttribute("value");
            return value == null || value.trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}