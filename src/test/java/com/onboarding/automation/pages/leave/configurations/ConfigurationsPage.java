package com.onboarding.automation.pages.leave.configurations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ConfigurationsPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public ConfigurationsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final String CONFIG_URL = "https://uat_mcdp_hcm.omfysgroup.com/hcm/leave/configuration";

    public void navigateToConfigPage() {
        driver.get(CONFIG_URL);
        wait.until(ExpectedConditions.urlContains("configuration"));
    }

    // ==========================================
    // Tab buttons
    // ==========================================
    private final By leaveTypeTab = By.xpath("//button[contains(text(), 'Leave Type')]");
    private final By reasonTypeTab = By.xpath("//button[contains(text(), 'Reason Type')]");
    private final By policyYearTab = By.xpath("//button[contains(text(), 'Policy Year')]");

    public void clickLeaveTypeTab() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveTypeTab)).click();
    }

    public void clickReasonTypeTab() {
        wait.until(ExpectedConditions.elementToBeClickable(reasonTypeTab)).click();
    }

    public void clickPolicyYearTab() {
        wait.until(ExpectedConditions.elementToBeClickable(policyYearTab)).click();
    }

    public void ensureLeaveTypeTabActive() {
        clickLeaveTypeTab();
        wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTypeInput));
    }

    // ==========================================
    // Leave Type Tab – Form elements
    // ==========================================
    private final By leaveTypeInput = By.xpath("//label[contains(text(), 'Leave Type')]/following::input[1]");
    private final By reasonTypeInput = By.xpath("//label[contains(text(), 'Reason Type')]/following::input[1]");
    private final By policyYearInput = By.xpath("//label[contains(text(), 'Policy Year')]/following::input[1]");
    private final By balanceRequiredToggle = By.xpath("//label[contains(text(), 'Is Balance Required?')]/following::label[contains(@class, 'toggle')]");
    private final By submitButton = By.xpath("//button[contains(text(), 'Submit')]");

    public boolean isLeaveTypeFormPresent() {
        try {
            return driver.findElement(leaveTypeInput).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void enterLeaveType(String leaveType) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(leaveTypeInput));
        input.clear();
        input.sendKeys(leaveType);
    }

    public void enterReasonType(String reason) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(reasonTypeInput));
        input.clear();
        input.sendKeys(reason);
    }

    public void enterPolicyYear(String year) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(policyYearInput));
        input.clear();
        input.sendKeys(year);
    }

    public void clickBalanceRequiredToggle() {
        wait.until(ExpectedConditions.elementToBeClickable(balanceRequiredToggle)).click();
    }

    public boolean isSubmitButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(submitButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickSubmitButton() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    // ==========================================
    // Leave Configuration Table
    // ==========================================
    private final By tableRows = By.xpath("//table/tbody/tr");
    private final By tableSearchInput = By.xpath("//input[@placeholder='Search leave type...']");
    private final By paginationSelect = By.xpath("//div[contains(@class, 'pagination')]//select");
    private final By nextPageButton = By.xpath("//button[contains(text(), 'Next')]");
    private final By previousPageButton = By.xpath("//button[contains(text(), 'Previous')]");
    private final By editButtonFirstRow = By.xpath("(//table/tbody/tr[1]//button)[1]");
    private final By statusToggleFirstRow = By.xpath("(//table/tbody/tr[1]//label[contains(@class, 'toggle')])[last()]");
    private final By balanceToggleFirstRow = By.xpath("(//table/tbody/tr[1]//label[contains(@class, 'toggle')])[1]");
    private final By saveButton = By.xpath("//button[contains(text(), 'Save')]");

    public int getTableRowCount() {
        return driver.findElements(tableRows).size();
    }

    public void searchLeaveType(String searchText) {
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(tableSearchInput));
        search.clear();
        search.sendKeys(searchText);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public boolean isPaginationPresent() {
        try {
            return driver.findElement(paginationSelect).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectShowEntries(String value) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(paginationSelect));
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(value);
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
    }

    public void clickNextPage() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", next);
        next.click();
    }

    public void clickPreviousPage() {
        WebElement prev = wait.until(ExpectedConditions.elementToBeClickable(previousPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", prev);
        prev.click();
    }

    public void clickEditFirstRow() {
        wait.until(ExpectedConditions.elementToBeClickable(editButtonFirstRow)).click();
    }

    public void clickStatusToggleFirstRow() {
        wait.until(ExpectedConditions.elementToBeClickable(statusToggleFirstRow)).click();
    }

    public void clickBalanceToggleFirstRow() {
        wait.until(ExpectedConditions.elementToBeClickable(balanceToggleFirstRow)).click();
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    // ==========================================
    // Reason Type Tab – with fallbacks
    // ==========================================
    private final By reasonTypeToggle = By.xpath("//h3[contains(text(), 'Reason Type')]/following::label[contains(@class, 'toggle')][1]");
    private final By reasonTypeToggleFallback = By.xpath("//label[contains(@class, 'toggle')]");
    private final By reasonTypePaginationSelect = By.xpath("//h3[contains(text(), 'Reason Type')]/following::select[1]");
    private final By reasonTypeNext = By.xpath("(//button[contains(text(), 'Next')])[2]");
    private final By reasonTypePrevious = By.xpath("(//button[contains(text(), 'Previous')])[2]");

    public boolean isReasonTypeToggleClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(reasonTypeToggle)).isEnabled();
        } catch (TimeoutException e) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(reasonTypeToggleFallback)).isEnabled();
            } catch (TimeoutException e2) {
                return false;
            }
        }
    }

    public void clickReasonTypeToggle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(reasonTypeToggle)).click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(reasonTypeToggleFallback)).click();
        }
    }

    public boolean isReasonTypePaginationPresent() {
        try {
            return driver.findElement(reasonTypePaginationSelect).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectReasonTypeShowEntries(String value) {
        if (!isReasonTypePaginationPresent()) {
            System.out.println("Reason Type pagination not present – skipping");
            return;
        }
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(reasonTypePaginationSelect));
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(value);
    }

    public void clickReasonTypeNext() {
        if (!isReasonTypePaginationPresent()) return;
        wait.until(ExpectedConditions.elementToBeClickable(reasonTypeNext)).click();
    }

    public void clickReasonTypePrevious() {
        if (!isReasonTypePaginationPresent()) return;
        wait.until(ExpectedConditions.elementToBeClickable(reasonTypePrevious)).click();
    }

    // ==========================================
    // Policy Year Tab – robust selection
    // ==========================================
    private final By policyYearDropdown = By.xpath("//label[contains(text(), 'Policy Year')]/following::select[1]");
    private final By updateButton = By.xpath("//button[contains(text(), 'Update')]");

    public boolean isPolicyYearDropdownPresent() {
        try {
            return driver.findElement(policyYearDropdown).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectPolicyYearType(String targetOption) {
        WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(policyYearDropdown));
        Select dropdown = new Select(selectElement);
        wait.until(driver -> dropdown.getOptions().size() > 0); // wait for options

        try {
            dropdown.selectByVisibleText(targetOption);
        } catch (NoSuchElementException e) {
            // Try partial match
            for (WebElement opt : dropdown.getOptions()) {
                if (opt.getText().toLowerCase().contains(targetOption.toLowerCase())) {
                    opt.click();
                    return;
                }
            }
            // Print available options for debugging
            System.out.println("Available options: " + dropdown.getOptions().stream().map(WebElement::getText).toList());
            throw new NoSuchElementException("Option not found: " + targetOption);
        }
    }

    public boolean isUpdateButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(updateButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickUpdateButton() {
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();
    }
}