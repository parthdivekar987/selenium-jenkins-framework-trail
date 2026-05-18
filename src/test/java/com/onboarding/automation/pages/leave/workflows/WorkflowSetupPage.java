package com.onboarding.automation.pages.leave.workflows;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WorkflowSetupPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public WorkflowSetupPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==========================================
    // Locators – using CSS selectors where possible
    // ==========================================

    // Sub-tabs (XPath still used because they may not have stable CSS)
    private final By roleMappingTab = By.xpath("//*[contains(text(), 'Role Mapping')]");
    private final By reportingStructureTab = By.xpath("//*[contains(text(), 'Reporting Structure')]");
    private final By leaveWorkflowTab = By.xpath("//*[contains(text(), 'Leave Workflow')]");

    // Add Role button – CSS selector from user
    private final By addRoleButton = By.cssSelector("button.glass-btn.px-6");

    // Table rows
    private final By rolesTableRows = By.cssSelector("table tbody tr");

    // Pagination – using a stable CSS selector (select element inside pagination div)
    private final By showEntriesSelect = By.cssSelector("div.flex.flex-col.sm\\:flex-row.justify-between.items-center.gap-3.mt-4.text-xs > div:nth-child(1) > select");
    private final By previousPageButton = By.xpath("//button[contains(text(), 'Previous')]");
    private final By nextPageButton = By.xpath("//button[contains(text(), 'Next')]");

    // ==========================================
    // Page Load Verification
    // ==========================================
    public boolean isLoaded() {
        try {
            // Wait for URL to contain "workflow-setup" OR the Add Role button to be visible
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("workflow-setup"),
                    ExpectedConditions.visibilityOfElementLocated(addRoleButton)
            ));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Workflow Setup page load timeout. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    // ==========================================
    // Tab Click Methods
    // ==========================================
    public void clickRoleMappingTab() {
        wait.until(ExpectedConditions.elementToBeClickable(roleMappingTab)).click();
    }

    public void clickReportingStructureTab() {
        wait.until(ExpectedConditions.elementToBeClickable(reportingStructureTab)).click();
    }

    public void clickLeaveWorkflowTab() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveWorkflowTab)).click();
    }

    // ==========================================
    // Add Role Button Methods
    // ==========================================
    public boolean isAddRoleButtonClickable() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(addRoleButton)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickAddRoleButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addRoleButton)).click();
    }

    // ==========================================
    // Table Methods
    // ==========================================
    public int getRolesTableRowCount() {
        return driver.findElements(rolesTableRows).size();
    }

    // ==========================================
    // Pagination Methods (only if the select exists)
    // ==========================================
    public boolean isPaginationPresent() {
        try {
            return driver.findElement(showEntriesSelect).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectShowEntries(String value) {
        if (!isPaginationPresent()) {
            System.out.println("Pagination select not present – skipping");
            return;
        }
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(showEntriesSelect));
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(value);
        wait.until(ExpectedConditions.presenceOfElementLocated(rolesTableRows));
    }

    public void clickPreviousPage() {
        if (!isPaginationPresent()) {
            System.out.println("Pagination previous button not present – skipping");
            return;
        }
        WebElement prev = wait.until(ExpectedConditions.elementToBeClickable(previousPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", prev);
        prev.click();
    }

    public void clickNextPage() {
        if (!isPaginationPresent()) {
            System.out.println("Pagination next button not present – skipping");
            return;
        }
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(nextPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", next);
        next.click();
    }
}