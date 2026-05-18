package com.onboarding.automation.pages.leave;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LeaveSettingsPage extends LeavePage {
    public LeaveSettingsPage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        // Log detected: https://uat_mcdp_hcm.omfysgroup.com/hcm/leave/camunda
        return wait.until(ExpectedConditions.urlContains("leave/camunda"));
    }
}