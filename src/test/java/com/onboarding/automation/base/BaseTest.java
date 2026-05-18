//package com.onboarding.automation.base;
//
//import com.onboarding.automation.pages.LoginPage;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import java.time.Duration;
//
//public class BaseTest {
//    public WebDriver driver;
//
//    @BeforeMethod
//    public void initializeDriver() {
//        WebDriverManager.chromedriver().setup();
//        ChromeOptions options = new ChromeOptions();
//
//        // REMOVED headless – run with visible browser for stability
//        // options.addArguments("--headless=new");
//
//        // Essential arguments for stability
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        options.addArguments("--start-maximized");
//        options.addArguments("--disable-extensions");
//        options.addArguments("--disable-popup-blocking");
//        options.addArguments("--disable-notifications");
//
//        // Disable Chrome's automatic password saving and other popups
//        options.addArguments("--disable-default-apps");
//        options.addArguments("--disable-sync");
//
//        // Set a fixed window size for consistent element positions
//        options.addArguments("--window-size=1920,1080");
//
//        // Increase log level to avoid unnecessary warnings (optional)
//        options.addArguments("--log-level=3");
//
//        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//
//        // Perform login only if not already logged in? We'll do it here for each test (simpler)
//        // But to avoid duplicate login overhead, we can use a static flag.
//        performLogin();
//    }
//
//    private void performLogin() {
//        try {
//            LoginPage loginPage = new LoginPage(driver);
//            loginPage.openLoginPage();
//            loginPage.loginAs("OMI-0076", "Omfys@123");
//            // Wait for successful login (URL no longer contains "login")
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
//        } catch (Exception e) {
//            System.out.println("Login failed: " + e.getMessage());
//            // Do not throw exception – let the test fail gracefully
//            // But if login fails, subsequent tests will likely fail anyway.
//            throw new RuntimeException("Login failed – cannot continue", e);
//        }
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            try {
//                driver.quit();
//            } catch (Exception e) {
//                System.out.println("Error closing driver: " + e.getMessage());
//            }
//        }
//    }
//}
package com.onboarding.automation.base;

import com.onboarding.automation.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    protected static WebDriverWait wait;
    private static boolean isLoggedIn = false;
    private static boolean isNavigated = false;

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        if (driver != null) {
            return;
        }

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        options.addArguments(
                "--start-maximized",
                "--disable-notifications",
                "--disable-popup-blocking",
                "--disable-extensions",
                "--disable-blink-features=AutomationControlled",
                "--remote-allow-origins=*",
                "--log-level=3",
                "--disable-new-tab-first-run",
                "--no-first-run"
        );

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("🚀 Browser initialized ONCE for all tests");

        // ✅ DO LOGIN AND NAVIGATION HERE (Before any test class runs)
        performLoginOnce();
        navigateToOnboardingModule();
    }

    private void performLoginOnce() {
        if (isLoggedIn) {
            return;
        }
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.openLoginPage();
            loginPage.loginAs("OMI-0076", "Omfys@123");

            wait.until(d -> !d.getCurrentUrl().contains("/login"));
            isLoggedIn = true;
            System.out.println("✅ Login successful ONCE");
        } catch (Exception e) {
            throw new RuntimeException("Login failed. Cannot continue.", e);
        }
    }

    private void navigateToOnboardingModule() {
        if (isNavigated) {
            System.out.println("✅ Already on Onboarding page");
            return;
        }
        try {
            driver.get("https://uat_mcdp_hcm.omfysgroup.com/onboarding_admin");

            // Wait for the Onboarding tabs to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='root']/div/div/main/div/div[2]/div/div/button")
            ));

            isNavigated = true;
            System.out.println("✅ Navigated to Onboarding module");
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Onboarding navigation issue - " + e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("🔚 Browser closed after all tests");
        }
    }
}