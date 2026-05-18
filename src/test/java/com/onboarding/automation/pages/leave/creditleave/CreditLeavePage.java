package com.onboarding.automation.pages.leave.creditleave;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CreditLeavePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public CreditLeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==========================================
    // Locators - Manual Credit Leave Page
    // ==========================================

    // Buttons
    private final By autoButton = By.xpath("//button[contains(text(), 'Auto')]");
    private final By manualButton = By.xpath("//button[contains(text(), 'Manual')]");
    private final By reportsButton = By.xpath("//button[contains(text(), 'Reports')]");

    // Employee Status Dropdown
    private final By employeeStatusButton = By.cssSelector("button.glass-input.w-full.text-left.pl-10.pr-8");
    private final By employeeStatusValue = By.cssSelector("div.w-full.overflow-x-auto.whitespace-nowrap.text-xs");

    // Credit Month Input
    private final By creditMonthInput = By.cssSelector("div:nth-child(2) > div > input");

    // Leave Type Dropdown
    private final By leaveTypeSelect = By.tagName("select");

    // Leave Count Input
    private final By leaveCountInput = By.cssSelector("input.glass-input[step='0.5']");

    // Search Employee
    private final By employeeSearchBox = By.cssSelector("div.flex.items-center.gap-2.rounded-2xl.border-2.border-\\[\\#dfe3ff\\].bg-white\\/80.px-3\\.5.py-1\\.5.text-sm.text-gray-500.shadow-\\[0_10px_20px_rgba\\(76\\,76\\,170\\,0\\.08\\)\\] > input");

    // Employee Table & Checkboxes
    private final By employeeTableRows = By.cssSelector("table tbody tr");
    private final By employeeCheckbox = By.cssSelector("td:nth-child(1) input");

    // Process Button
    private final By processButton = By.cssSelector("div.mt-4.flex.justify-end > button");

    // Pagination
    private final By showEntriesSelect = By.xpath("//div[contains(text(), 'Show:')]/following::select[1]");
    private final By nextPageButton = By.xpath("//button[contains(text(), 'Next')]");
    private final By previousPageButton = By.xpath("//button[contains(text(), 'Previous')]");

    // Success/Validation Messages
    private final By successMessage = By.xpath("//div[contains(@class, 'success')] | //div[contains(text(), 'Successfully')]");
    private final By validationMessage = By.xpath("//div[contains(@class, 'error')] | //div[contains(@class, 'text-red')]");

    // Reports Section
    private final By reportsTable = By.xpath("//div[contains(@class, 'report')]//table");
    private final By reportsRows = By.xpath("//div[contains(@class, 'report')]//table/tbody/tr");
    private final By reportsSearchBox = By.xpath("//input[@placeholder='Search report data...']");

    // ==========================================
    // Navigation Methods
    // ==========================================

    public void clickAutoButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(autoButton)).click();
        } catch (Exception e) {
            System.out.println("Auto button not clickable: " + e.getMessage());
        }
    }

    public void clickManualButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(manualButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            button.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(employeeStatusButton));
        } catch (Exception e) {
            System.out.println("Manual button not clickable: " + e.getMessage());
        }
    }

    public void clickReportsButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(reportsButton)).click();
        } catch (Exception e) {
            System.out.println("Reports button not clickable: " + e.getMessage());
        }
    }

    public boolean isManualButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(manualButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isAutoButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(autoButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isReportsButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(reportsButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ==========================================
    // Employee Status Methods - ROBUST
    // ==========================================

    public boolean isEmployeeStatusDropdownPresent() {
        try {
            return driver.findElement(employeeStatusButton).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickEmployeeStatusDropdown() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(employeeStatusButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        dropdown.click();
    }

    public void selectEmployeeStatus(String status) {
        clickEmployeeStatusDropdown();
        // Try multiple locator strategies
        By[] statusOptions = {
                By.xpath("//div[contains(text(), '" + status + "')]"),
                By.xpath("//span[contains(text(), '" + status + "')]"),
                By.xpath("//div[@role='option' and contains(text(), '" + status + "')]"),
                By.xpath("//*[normalize-space()='" + status + "']")
        };
        for (By option : statusOptions) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                return;
            } catch (Exception e) {
                // try next
            }
        }
        System.out.println("Could not find employee status option: " + status);
    }

    public String getSelectedEmployeeStatus() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeStatusValue));
        String text = element.getText();
        return text.replace("Selected: ", "");
    }

    // ==========================================
    // Credit Month Methods - FIXED: Use short month format
    // ==========================================

    public boolean isCreditMonthInputPresent() {
        try {
            return driver.findElement(creditMonthInput).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Enter credit month - accepts formats like "Apr, 2026" or "April, 2026"
     * Will convert full month names to short form if needed
     */
    public void enterCreditMonth(String month) {
        // Convert full month names to short form if needed (because calendar uses "Apr" not "April")
        String convertedMonth = convertToShortMonth(month);

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(creditMonthInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        input.clear();
        input.sendKeys(convertedMonth);
    }

    private String convertToShortMonth(String month) {
        // Map of full month names to short forms (3 letters)
        if (month.contains("April")) {
            return month.replace("April", "Apr");
        } else if (month.contains("January")) {
            return month.replace("January", "Jan");
        } else if (month.contains("February")) {
            return month.replace("February", "Feb");
        } else if (month.contains("March")) {
            return month.replace("March", "Mar");
        } else if (month.contains("May")) {
            return month.replace("May", "May");
        } else if (month.contains("June")) {
            return month.replace("June", "Jun");
        } else if (month.contains("July")) {
            return month.replace("July", "Jul");
        } else if (month.contains("August")) {
            return month.replace("August", "Aug");
        } else if (month.contains("September")) {
            return month.replace("September", "Sep");
        } else if (month.contains("October")) {
            return month.replace("October", "Oct");
        } else if (month.contains("November")) {
            return month.replace("November", "Nov");
        } else if (month.contains("December")) {
            return month.replace("December", "Dec");
        }
        return month; // return as is if already short or unknown
    }

    public String getCreditMonthValue() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(creditMonthInput));
        return input.getAttribute("value");
    }

    // ==========================================
    // Leave Type Methods
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
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectElement);
        Select dropdown = new Select(selectElement);
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
    // Leave Count Methods
    // ==========================================

    public boolean isLeaveCountInputPresent() {
        try {
            return driver.findElement(leaveCountInput).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void enterLeaveCount(String count) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(leaveCountInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        input.clear();
        input.sendKeys(count);
    }

    public String getLeaveCountValue() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(leaveCountInput));
        return input.getAttribute("value");
    }

    // ==========================================
    // Employee List & Search Methods
    // ==========================================

    public boolean isEmployeeListPresent() {
        try {
            return driver.findElements(employeeTableRows).size() > 0;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public int getEmployeeCount() {
        return driver.findElements(employeeTableRows).size();
    }

    public void searchEmployee(String searchText) {
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(employeeSearchBox));
        search.clear();
        search.sendKeys(searchText);
        wait.until(ExpectedConditions.presenceOfElementLocated(employeeTableRows));
    }

    public void clearEmployeeSearch() {
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(employeeSearchBox));
        search.clear();
    }

    public void selectEmployeeByIndex(int index) {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(employeeCheckbox));
        if (index <= checkboxes.size()) {
            WebElement checkbox = checkboxes.get(index - 1);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public void selectAllEmployees() {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(employeeCheckbox));
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
                checkbox.click();
            }
        }
    }

    // ==========================================
    // Pagination Methods
    // ==========================================

    public void selectShowEntries(String value) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(showEntriesSelect));
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(value);
        wait.until(ExpectedConditions.presenceOfElementLocated(employeeTableRows));
    }

    public void clickNextPage() {
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", next);
        next.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(employeeTableRows));
    }

    public void clickPreviousPage() {
        WebElement prev = wait.until(ExpectedConditions.elementToBeClickable(previousPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", prev);
        prev.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(employeeTableRows));
    }

    // ==========================================
    // Process Button Methods
    // ==========================================

    public boolean isProcessButtonPresent() {
        try {
            return driver.findElement(processButton).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isProcessButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(processButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickProcessButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(processButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    // ==========================================
    // Reports Section Methods
    // ==========================================

    public boolean isReportTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(reportsTable)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public int getReportRowCount() {
        try {
            return driver.findElements(reportsRows).size();
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public void searchInReports(String searchText) {
        try {
            WebElement search = wait.until(ExpectedConditions.elementToBeClickable(reportsSearchBox));
            search.clear();
            search.sendKeys(searchText);
        } catch (TimeoutException e) {
            System.out.println("Search box not found in reports section");
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isValidationMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(validationMessage)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isLoaded() {
        return isManualButtonClickable();
    }
}