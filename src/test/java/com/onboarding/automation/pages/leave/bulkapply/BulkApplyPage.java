package com.onboarding.automation.pages.leave.bulkapply;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BulkApplyPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BulkApplyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Direct URL navigation
    private final String BULK_APPLY_URL = "https://uat_mcdp_hcm.omfysgroup.com/hcm/leave/bulk-apply";

    // Apply Leave button – using the selector provided by user (CSS)
    private final By applyLeaveButton = By.cssSelector("button.px-4.py-2.rounded-full.text-sm.font-semibold.transition-all.bg-\\[\\#4C4CAA\\].text-white.shadow-lg");

    // Search Employees dropdown (used by other tests)
    private final By searchEmployeesButton = By.xpath("//button[contains(., '-- Select --')]");

    // Employee search input
    private final By employeeSearchInput = By.xpath("//input[@placeholder='Search employee...']");
    private final By employeeCheckboxes = By.xpath("//input[@type='checkbox']");
    private final By selectedCount = By.xpath("//span[contains(text(), 'selected')]");

    // Day Duration dropdown (correct selector)
    private final By dayDurationDropdown = By.cssSelector("select.glass-select");

    // Leave Type dropdown (adjust if needed)
    private final By leaveTypeDropdown = By.xpath("//div[contains(text(), 'Leave Type')]/following::select[1]");

    // Date inputs
    private final By fromDateInput = By.xpath("//input[@placeholder='dd-mm-yyyy']");
    private final By toDateInput = By.xpath("(//input[@placeholder='dd-mm-yyyy'])[2]");

    // Reason dropdown
    private final By reasonDropdown = By.xpath("//div[contains(text(), 'Reason')]/following::select[1]");

    // Submit button
    private final By submitApplyButton = By.xpath("//button[contains(text(), 'Apply Leave')] | //button[@type='submit']");

    // CSV buttons
    private final By chooseCsvButton = By.xpath("//label[contains(text(), 'Choose CSV')] | //label[contains(@class, 'cursor-pointer')]");
    private final By importCsvButton = By.cssSelector("button.px-4.py-2.rounded-full.text-sm.font-semibold.transition-all.bg-\\[\\#4C4CAA\\].text-white.shadow-lg");

    // Validation messages
    private final By successMessage = By.xpath("//div[contains(@class, 'success')] | //div[contains(text(), 'Successfully')]");
    private final By errorMessage = By.xpath("//div[contains(@class, 'error')] | //div[contains(@class, 'text-red')]");
    private final By validationMessage = By.xpath("//span[contains(@class, 'error')] | //div[contains(@class, 'invalid')]");

    // ==========================================
    // Navigation Methods
    // ==========================================
    public void navigateToBulkApplyPageDirectly() {
        driver.get(BULK_APPLY_URL);
        wait.until(ExpectedConditions.urlContains("bulk-apply"));
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public boolean isBulkApplyPageLoaded() {
        return driver.getCurrentUrl().contains("bulk-apply");
    }

    public boolean isLoaded() {
        return isBulkApplyPageLoaded();
    }

    // ==========================================
    // CSV Upload Methods
    // ==========================================
    public boolean isChooseCsvButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(chooseCsvButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickChooseCsvButton() {
        wait.until(ExpectedConditions.elementToBeClickable(chooseCsvButton)).click();
    }

    public boolean isImportCsvButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(importCsvButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickImportCsvButton() {
        wait.until(ExpectedConditions.elementToBeClickable(importCsvButton)).click();
    }

    // ==========================================
    // Apply Leave Button Methods
    // ==========================================
    public boolean isApplyLeaveButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(applyLeaveButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Clicks the Apply Leave button. Does NOT wait for any form fields.
     * Use this for simple clickability tests.
     */
    public void clickApplyLeaveButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(applyLeaveButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    /**
     * Clicks the Apply Leave button and waits for the form to be visible.
     * Use this for tests that need to interact with the form fields.
     */
    public void clickApplyLeaveButtonAndWaitForForm() {
        clickApplyLeaveButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployeesButton));
    }

    // ==========================================
    // Search Employees Methods
    // ==========================================
    public boolean isSearchEmployeesDropdownPresent() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployeesButton)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickSearchEmployeesDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(searchEmployeesButton)).click();
    }

    public void searchEmployee(String employeeName) {
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(employeeSearchInput));
        search.clear();
        search.sendKeys(employeeName);
        wait.until(ExpectedConditions.presenceOfElementLocated(employeeCheckboxes));
    }

    public void selectEmployeeByIndex(int index) {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(employeeCheckboxes));
        if (index <= checkboxes.size()) {
            WebElement checkbox = checkboxes.get(index - 1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public void selectAllEmployees() {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(employeeCheckboxes));
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                checkbox.click();
            }
        }
    }

    public String getSelectedCount() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(selectedCount)).getText();
        } catch (TimeoutException e) {
            return "0";
        }
    }

    // ==========================================
    // Day Duration Methods
    // ==========================================
    public boolean isDayDurationDropdownPresent() {
        try {
            return driver.findElement(dayDurationDropdown).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectDayDuration(String duration) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(dayDurationDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectElement);
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(duration);
    }

    public String getSelectedDayDuration() {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dayDurationDropdown));
        Select dropdown = new Select(selectElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    // ==========================================
    // Leave Type Methods
    // ==========================================
    public boolean isLeaveTypeDropdownPresent() {
        try {
            return driver.findElement(leaveTypeDropdown).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectLeaveType(String leaveType) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(leaveTypeDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectElement);
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(leaveType);
    }

    public String getSelectedLeaveType() {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTypeDropdown));
        Select dropdown = new Select(selectElement);
        return dropdown.getFirstSelectedOption().getText();
    }

    public List<String> getAllLeaveTypes() {
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTypeDropdown));
        Select dropdown = new Select(selectElement);
        return dropdown.getOptions().stream().map(WebElement::getText).toList();
    }

    // ==========================================
    // Date Methods
    // ==========================================
    public boolean isFromDateInputPresent() {
        try {
            return driver.findElement(fromDateInput).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isToDateInputPresent() {
        try {
            return driver.findElement(toDateInput).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void enterFromDate(String date) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fromDateInput));
        input.clear();
        input.sendKeys(date);
    }

    public void enterToDate(String date) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(toDateInput));
        input.clear();
        input.sendKeys(date);
    }

    public String getFromDate() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(fromDateInput));
        return input.getAttribute("value");
    }

    public String getToDate() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(toDateInput));
        return input.getAttribute("value");
    }

    // ==========================================
    // Reason Methods
    // ==========================================
    public boolean isReasonDropdownPresent() {
        try {
            return driver.findElement(reasonDropdown).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectReason(String reason) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(reasonDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectElement);
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(reason);
    }

    // ==========================================
    // Submit Methods
    // ==========================================
    public boolean isSubmitApplyButtonPresent() {
        try {
            return driver.findElement(submitApplyButton).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isSubmitApplyButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(submitApplyButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickSubmitApplyButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitApplyButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    // ==========================================
    // Validation Methods
    // ==========================================
    public boolean isSuccessMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isValidationMessageDisplayed() {
        try {
            return driver.findElement(validationMessage).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getValidationMessageText() {
        try {
            return driver.findElement(validationMessage).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // ==========================================
    // Complete Flow Methods
    // ==========================================
    public void fillCompleteForm(String leaveType, String fromDate, String toDate, String reason, int employeeIndex) {
        selectLeaveType(leaveType);
        enterFromDate(fromDate);
        enterToDate(toDate);
        selectReason(reason);
        selectEmployeeByIndex(employeeIndex);
    }
}