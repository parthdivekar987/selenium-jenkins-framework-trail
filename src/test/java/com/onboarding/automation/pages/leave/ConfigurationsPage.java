package com.onboarding.automation.pages.leave;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConfigurationsPage extends LeavePage {
    public ConfigurationsPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        // Log detected: https://uat_mcdp_hcm.omfysgroup.com/hcm/leave/configuration
        return wait.until(ExpectedConditions.urlContains("leave/configuration"));
    }
}