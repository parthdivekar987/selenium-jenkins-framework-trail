package com.onboarding.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ManageWorkforcePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By workforceTab = By.xpath("//div[@id='root']/div/div/main/div/div[2]/div/div/button[4]");

    // The download PDF button (from your Katalon script)
    private By downloadPdfButton = By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Download PDF'])[1]/preceding::*[name()='svg'][1]");

    // A specific detail view button or element
    private By viewDetailsButton = By.xpath("//div[@id='root']/div/div/main/div/div[3]/div/table/tbody/tr/td[6]/button");

    public ManageWorkforcePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickWorkforceTab() {
        wait.until(ExpectedConditions.elementToBeClickable(workforceTab)).click();
    }

    public boolean isDownloadPdfVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(downloadPdfButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickViewDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(viewDetailsButton)).click();
    }
}