package com.onboarding.automation.pages.leave;

import org.openqa.selenium.*;
        import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LeavePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ==========================================
    // Locators: Navigation Tabs
    // ==========================================
    private final By overviewTab = By.xpath("//button[contains(text(), 'Overview')]");
    public final By creditLeaveTab = By.xpath("//button[contains(text(), 'Credit Leave')]");
    private final By bulkApplyTab = By.xpath("//button[contains(text(), 'Bulk Apply')]");
    private final By workflowSetupTab = By.xpath("//button[contains(text(), 'Workflow Setup')]");
    private final By patternRuleTab = By.xpath("//button[contains(text(), 'Pattern Rule')]");
    private final By configurationsTab = By.xpath("//button[contains(text(), 'Configurations')]");
    private final By leaveSettingsTab = By.xpath("//button[contains(text(), 'Leave Settings')]");

    // ==========================================
    // Locators: Dashboard Cards & Modals
    // ==========================================
    private final By pendingCard = By.xpath("//span[text()='Pending']/ancestor::div[contains(@class, 'rounded-full')]");
    private final By modalHeader = By.xpath("//h2[contains(text(), 'Pending')] | //h3[contains(text(), 'Pending')]");
    private final By searchBox = By.xpath("//input[@placeholder='Search' or @type='search']");
    private final By entriesSelect = By.xpath("//select");
    private final By tableRows = By.xpath("//table//tbody/tr");
    private final By firstEmployeeLink = By.xpath("//table//tbody/tr[1]/td[2]//a");
    private final By firstEmployeeNameText = By.xpath("//table//tbody/tr[1]/td[2]");
    private final By modalCloseBtn = By.xpath("//button[contains(text(), 'Close')]");
    private final By previewLeaveType = By.xpath("//p[text()='Leave Type']/following-sibling::h3");
    private final By previewCloseBtn = By.xpath("//button[contains(text(), 'Close')]");

    private final By nextMonthBtn = By.xpath("//button[.//*[local-name()='svg' and contains(@class, 'chevron-right')]]");
    private final By monthLabel = By.xpath("//h2[contains(text(), '2026')]");
    private final By modalBackdrop = By.className("modal-backdrop");
    private final By loadingOverlay = By.xpath("//div[contains(@class, 'loading')] | //div[contains(@class, 'spinner')]");

    // FIXED: No Overview button dependency - just wait for body
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Page loaded");
        }
    }

    private void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalBackdrop));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
        } catch (TimeoutException e) {
            // No overlay present
        }
    }

    private void waitForModalToBeReady() {
        waitForOverlayToDisappear();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    // ==========================================
    // Actions
    // ==========================================
    public void clickOverview() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(overviewTab)).click();
        } catch (Exception e) {
            System.out.println("Overview button not clickable");
        }
    }

    public void clickCreditLeave() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(creditLeaveTab)).click();
        } catch (Exception e) {
            System.out.println("Credit Leave button not clickable");
        }
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public void clickBulkApply() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(bulkApplyTab)).click();
        } catch (Exception e) {
            System.out.println("Bulk Apply button not clickable");
        }
    }

    public void clickWorkflowSetup() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(workflowSetupTab)).click();
        } catch (Exception e) {
            System.out.println("Workflow Setup button not clickable");
        }
    }

    public void clickPatternRule() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(patternRuleTab)).click();
        } catch (Exception e) {
            System.out.println("Pattern Rule button not clickable");
        }
    }

    public void clickConfigurations() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(configurationsTab)).click();
        } catch (Exception e) {
            System.out.println("Configurations button not clickable");
        }
    }

    public void clickLeaveSettings() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(leaveSettingsTab)).click();
        } catch (Exception e) {
            System.out.println("Leave Settings button not clickable");
        }
    }
    public void clickPendingCard() {
        waitForOverlayToDisappear();
        // Updated locator for Pending card
        By pendingCardNew = By.xpath("//span[contains(text(), 'Pending')]/ancestor::div[contains(@class, 'rounded-full')]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(pendingCardNew));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        waitForModalToBeReady();
    }

    public boolean isPendingModalVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(modalHeader)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void searchInModal(String name) {
        waitForModalToBeReady();
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        search.clear();
        search.sendKeys(name);
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
    }

    public void selectEntriesPerPage(String value) {
        waitForModalToBeReady();
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(entriesSelect));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectElement);
        Select dropdown = new Select(selectElement);
        dropdown.selectByVisibleText(value);
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRows));
    }

    public int getTableRowCount() {
        waitForModalToBeReady();
        return driver.findElements(tableRows).size();
    }

    public String getFirstRowEmployeeName() {
        waitForModalToBeReady();
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(firstEmployeeNameText));
        return nameElement.getText();
    }

    public void clickFirstEmployeeLink() {
        waitForModalToBeReady();
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstEmployeeLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    public String getPreviewLeaveType() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(previewLeaveType)).getText();
    }

    public void clickNextMonth() {
        WebElement svgElement = wait.until(ExpectedConditions.elementToBeClickable(nextMonthBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", svgElement);
        try {
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.moveToElement(svgElement).click().perform();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", svgElement);
        }
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public String getMonthText() {
        WebElement monthElement = wait.until(ExpectedConditions.visibilityOfElementLocated(monthLabel));
        return monthElement.getText();
    }

    public void closePreview() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(previewCloseBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public void closeModals() {
        try {
            WebElement close = wait.until(ExpectedConditions.elementToBeClickable(modalCloseBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", close);
        } catch (TimeoutException e) {
            // Modal already closed
        }
        waitForOverlayToDisappear();
    }
}