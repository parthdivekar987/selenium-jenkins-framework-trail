package com.onboarding.automation.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        try {

            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Create screenshot folder if not present
            String folderPath = System.getProperty("user.dir") + "/screenshots";
            File folder = new File(folderPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Screenshot path
            String screenshotPath = folderPath + "/" + testName + ".png";

            File destFile = new File(screenshotPath);

            // Copy screenshot
            FileUtils.copyFile(srcFile, destFile);

            // Attach screenshot to Allure
            InputStream is = new FileInputStream(destFile);
            Allure.addAttachment(testName, is);

            return screenshotPath;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }
}