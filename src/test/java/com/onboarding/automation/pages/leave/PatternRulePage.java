package com.onboarding.automation.pages.leave;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PatternRulePage extends LeavePage {
    public PatternRulePage(WebDriver driver) { super(driver); }

    public boolean isLoaded() {
        // Log detected: https://uat_mcdp_hcm.omfysgroup.com/hcm/leave/pattern
        return wait.until(ExpectedConditions.urlContains("leave/pattern"));
    }
}