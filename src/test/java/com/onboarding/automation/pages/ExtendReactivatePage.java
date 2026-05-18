package com.onboarding.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ExtendReactivatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By extendReactivateTab = By.xpath("//button[contains(.,'Extend') or contains(.,'Reactivate')]");
    private final By allRadioButtons = By.cssSelector("input[type='radio'][class*='accent']");
    private final By extendLinkRadio = By.xpath("(//input[@type='radio' and contains(@class,'accent')])[1]");
    private final By reactivateLinkRadio = By.xpath("(//input[@type='radio' and contains(@class,'accent')])[2]");
    private final By searchInput = By.xpath("//input[@placeholder='Search candidate...' or contains(@id,'_r_')]");
    private final By reactivateButton = By.xpath("//button[contains(.,'Reactivate') and contains(@class,'bg-[#4C4CAA]')]");
    private final By cancelButton = By.xpath("//button[contains(.,'Cancel')]");
    private final By paginationDropdown = By.cssSelector("select.h-9, select.border-gray-300");
    private final By tableRows = By.xpath("//table/tbody/tr");
    private final By nextPageButton = By.xpath("//button[contains(.,'Next')]");
    private final By previousPageButton = By.xpath("//button[contains(.,'Previous')]");
    private final By pageInfo = By.xpath("//*[contains(.,'Page') and contains(.,'of')]");
    private final By noCandidatesMessage = By.xpath("//*[contains(.,'No candidates found')]");
    private final By tableHeaders = By.xpath("//table/thead/tr/th");
    private final By toastMessage = By.xpath("//*[contains(@class, 'toast') or contains(@class, 'Toastify')]");

    public ExtendReactivatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Increased from 8 to 10
    }

    public void clickExtendReactivateTab() {
        wait.until(ExpectedConditions.elementToBeClickable(extendReactivateTab)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(allRadioButtons));
        System.out.println("✅ Clicked on Extend/Reactivate tab");
    }

    public boolean isExtendReactivatePageLoaded() {
        try {
            return !driver.findElements(allRadioButtons).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectExtendLinkValidity() {
        wait.until(ExpectedConditions.elementToBeClickable(extendLinkRadio)).click();
    }

    public void selectReactivateLink() {
        wait.until(ExpectedConditions.elementToBeClickable(reactivateLinkRadio)).click();
    }

    public boolean isExtendLinkRadioSelected() {
        List<WebElement> radios = driver.findElements(allRadioButtons);
        return !radios.isEmpty() && radios.get(0).isSelected();
    }

    public boolean isReactivateLinkRadioSelected() {
        List<WebElement> radios = driver.findElements(allRadioButtons);
        return radios.size() > 1 && radios.get(1).isSelected();
    }

    public void searchForCandidate(String name) {
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        search.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        search.sendKeys(Keys.BACK_SPACE);
        if (name != null && !name.isBlank()) {
            search.sendKeys(name);
        }
    }

    public void clearSearch() {
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        search.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        search.sendKeys(Keys.BACK_SPACE);
    }

    public String getSearchPlaceholder() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput))
                .getAttribute("placeholder");
    }

    public void clickReactivateButtonForFirstRow() {
        wait.until(ExpectedConditions.elementToBeClickable(reactivateButton)).click();
    }

    public boolean isReactivateButtonDisplayed() {
        try {
            return !driver.findElements(reactivateButton).isEmpty()
                    && driver.findElement(reactivateButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getReactivateButtonCount() {
        return driver.findElements(reactivateButton).size();
    }

    public void clickCancelInReactivateModal() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    public boolean isModalDisplayed() {
        try {
            return driver.findElement(cancelButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectPaginationEntries(String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(paginationDropdown));
        new Select(dropdown).selectByVisibleText(value);
    }

    public String getCurrentPaginationValue() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(paginationDropdown));
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    public void clickNextPage() {
        wait.until(ExpectedConditions.elementToBeClickable(nextPageButton)).click();
    }

    public void clickPreviousPage() {
        wait.until(ExpectedConditions.elementToBeClickable(previousPageButton)).click();
    }

    public boolean isNextPageEnabled() {
        return driver.findElement(nextPageButton).isEnabled();
    }

    public boolean isPreviousPageEnabled() {
        return driver.findElement(previousPageButton).isEnabled();
    }

    public String getPageInfo() {
        try {
            return driver.findElement(pageInfo).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public int getRowCount() {
        return driver.findElements(tableRows).size();
    }

    public boolean isTableEmpty() {
        try {
            return driver.findElement(noCandidatesMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCandidateNameFromRow(int rowNumber) {
        return driver.findElement(By.xpath("//table/tbody/tr[" + rowNumber + "]/td[1]")).getText();
    }

    public String getCandidateEmailFromRow(int rowNumber) {
        return driver.findElement(By.xpath("//table/tbody/tr[" + rowNumber + "]/td[2]")).getText();
    }

    public List<String> getAllTableHeaders() {
        return driver.findElements(tableHeaders).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isToastMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getToastMessageText() {
        try {
            return driver.findElement(toastMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }
}