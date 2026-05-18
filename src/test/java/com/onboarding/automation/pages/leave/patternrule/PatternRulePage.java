package com.onboarding.automation.pages.leave.patternrule;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class PatternRulePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public PatternRulePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==========================================
    // Locators – Leave Type dropdown (already visible in Manual mode)
    // ==========================================
    private final By leaveTypeSelect = By.cssSelector("section:nth-child(3) div.space-y-4 > div > div:nth-child(1) > div > select");
    private final By priorDaysFullDay = By.cssSelector("section:nth-child(3) div.space-y-4 > div > div:nth-child(2) > div > input");
    private final By priorDaysHalfDay = By.xpath("(//input[@placeholder='Enter days'])[2]");
    private final By plusButton = By.cssSelector("section:nth-child(3) div.space-y-4 > div:nth-child(2) > div.flex.items-center.gap-3.md\\:justify-start.mt-\\[-10px\\] > button:nth-child(2) > svg");

    // ==========================================
    // Page Load Verification
    // ==========================================
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTypeSelect));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ==========================================
    // Leave Type Dropdown Methods
    // ==========================================
    public boolean isLeaveTypeDropdownPresent() {
        try {
            return driver.findElement(leaveTypeSelect).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectLeaveType(String leaveType) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(leaveTypeSelect));
        Select dropdown = new Select(selectElement);
        wait.until(driver -> dropdown.getOptions().size() > 0);
        dropdown.selectByVisibleText(leaveType);
    }

    public String getSelectedLeaveType() {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTypeSelect));
        Select dropdown = new Select(selectElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    public List<String> getAllLeaveTypes() {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTypeSelect));
        Select dropdown = new Select(selectElement);
        return dropdown.getOptions().stream().map(WebElement::getText).toList();
    }

    // ==========================================
    // Prior Days Methods
    // ==========================================
    public void enterPriorDaysFullDay(String days) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(priorDaysFullDay));
        input.clear();
        input.sendKeys(days);
    }

    public String getPriorDaysFullDay() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(priorDaysFullDay));
        return input.getAttribute("value");
    }

    public void enterPriorDaysHalfDay(String days) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(priorDaysHalfDay));
        input.clear();
        input.sendKeys(days);
    }

    public String getPriorDaysHalfDay() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(priorDaysHalfDay));
        return input.getAttribute("value");
    }

    // ==========================================
    // Plus Button Methods
    // ==========================================
    public boolean isPlusButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(plusButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickPlusButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(plusButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }
}