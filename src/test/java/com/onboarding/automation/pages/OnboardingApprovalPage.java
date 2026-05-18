package com.onboarding.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class OnboardingApprovalPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By approvalTab = By.xpath("//div[@id='root']/div/div/main/div/div[2]/div/div/button[3]");

    // Search input for "Medhaj" (ID extracted from your script: _r_q_)
    private By searchInput = By.id("_r_q_");

    // Action button in the table (View/Approve - from your script)
    private By actionButtonInRow = By.xpath("//div[@id='root']/div/div/main/div/div[3]/div/table/tbody/tr/td[6]/button");

    // Pagination Dropdown
    private By paginationDropdown = By.xpath("//div[@id='root']/div/div/main/div/div[3]/div[2]/div/div/select");

    // Next Page Button
    private By nextPageButton = By.xpath("//div[@id='root']/div/div/main/div/div[3]/div[2]/div/div[2]/div/button[2]");

    public OnboardingApprovalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickApprovalTab() {
        wait.until(ExpectedConditions.elementToBeClickable(approvalTab)).click();
    }

    public void searchForCandidate(String name) {
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        search.clear();
        search.sendKeys(name);
        // Trigger search if there's a button, or just submit
        search.submit();
    }

    public boolean isActionButtonClickable() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(actionButtonInRow));
            return button.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickActionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(actionButtonInRow)).click();
    }

    public void selectPaginationEntries(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(paginationDropdown));
        new Select(dropdown).selectByVisibleText(value);
    }

    public void clickNextPage() {
        wait.until(ExpectedConditions.elementToBeClickable(nextPageButton)).click();
    }
}