package com.onboarding.automation.listeners;

import com.onboarding.automation.base.BaseTest;
import com.onboarding.automation.utils.ScreenshotUtil;

import org.testng.ITestListener;
import org.testng.ITestResult;

import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        // Get test class object
        Object testClass = result.getInstance();

        // Get driver from BaseTest
        WebDriver driver = ((BaseTest) testClass).driver;

        // Capture screenshot
        ScreenshotUtil.captureScreenshot(driver, result.getName());
    }
}