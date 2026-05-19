package com.onboarding.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class InitiateOnboardingPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public InitiateOnboardingPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By firstName = By.name("firstName");
    private By lastName = By.name("lastName");
    private By email = By.name("email");

    private By designationDropdown = By.xpath("//div[4]//div[1]//select[1]");
    private By departmentDropdown = By.xpath("//div[5]//div[1]//select[1]");
    private By roleDropdown = By.xpath("//div[6]//div[1]//select[1]");

    private By levelDropdown = By.name("grade");
    private By employeeStatusDropdown = By.name("employeeStatusId");

    private By dateOfJoining = By.name("date_of_joining");
    private By employmentModeDropdown = By.name("employmentMode");
    private By linkValidity = By.name("days");

    private By generateButton =
            By.xpath("//button[contains(@class,'onboarding_next_btn')]");


    public void waitForOnboardingPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    }


    public void enterFirstName(String value){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        element.clear();
        element.sendKeys(value);
    }

    public void enterLastName(String value){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        element.clear();
        element.sendKeys(value);
    }

    public void enterEmail(String value){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        element.clear();
        element.sendKeys(value);
    }

    public void selectDesignation(String value){
        selectDropdownValue(designationDropdown, value);
    }

    public void selectDepartment(String value){
        selectDropdownValue(departmentDropdown, value);
    }

    public void selectRole(String value){
        selectDropdownValue(roleDropdown, value);
    }

    public void selectLevel(String value){
        selectDropdownValue(levelDropdown, value);
    }

    public void selectEmployeeStatus(String value){
        selectDropdownValue(employeeStatusDropdown, value);
    }

    public void setDateOfJoining(String value){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfJoining));
        element.clear();
        element.sendKeys(value);
    }

    public void selectEmploymentMode(String value){
        selectDropdownValue(employmentModeDropdown, value);
    }

    public void setLinkValidity(String value){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(linkValidity));
        element.clear();
        element.sendKeys(value);
    }

    private void selectDropdownValue(By dropdownLocator, String preferredValue) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
        Select dropdown = new Select(element);
        wait.until(driver -> dropdown.getOptions().size() > 0);

        try {
            dropdown.selectByVisibleText(preferredValue);
            return;
        } catch (NoSuchElementException ignored) {
            // Fallback to a partial match or the first real option.
        }

        List<WebElement> options = dropdown.getOptions();
        for (WebElement option : options) {
            String text = option.getText().trim();
            if (text.isEmpty()) {
                continue;
            }
            if (text.equalsIgnoreCase(preferredValue)
                    || text.toLowerCase().contains(preferredValue.toLowerCase())
                    || preferredValue.toLowerCase().contains(text.toLowerCase())) {
                option.click();
                return;
            }
        }

        for (WebElement option : options) {
            String text = option.getText().trim();
            if (!text.isEmpty()
                    && !text.toLowerCase().contains("select")
                    && !text.equals("--")) {
                option.click();
                return;
            }
        }

        throw new NoSuchElementException("No selectable option found for '" + preferredValue + "'");
    }

    /*
    Check if generate button is enabled
    */
    public boolean isGenerateButtonEnabled(){

        try{
            WebElement button = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(generateButton)
            );
            return button.isEnabled();
        }
        catch(Exception e){
            return false;
        }
    }


    /*
    Click only if enabled
    */
    public void clickGenerateOnboarding(){
        // Standard visibility wait
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(generateButton));
        button.click();
    }


    public void clearForm(){
        // Using a loop or individual clears ensures fields are ready for the next test
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(linkValidity)).clear();
        // Added refresh to clear dropdowns to their default state
        driver.navigate().refresh();
        waitForOnboardingPage();
    }


    public boolean isValidationMessageDisplayed(String text){

        try {

            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'" + text + "')]")
            )).isDisplayed();

        }
        catch (Exception e){
            return false;
        }
    }
}
