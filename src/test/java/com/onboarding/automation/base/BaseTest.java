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
//            loginPage.loginAs("OMI-0076", "");
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

import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    protected static WebDriverWait wait;
    // Read credentials from environment or .env via EnvConfig (has sensible defaults)
    protected static final String VALID_USERNAME = EnvConfig.get("EMP_CODE", "OMI-0076");
    protected static final String VALID_PASSWORD = EnvConfig.get("EMP_PASSWORD", "Omfys@123");
    private static boolean isLoggedIn = false;
    private static boolean isNavigated = false;

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        if (driver != null) {
            return;
        }

        // Load .env if present and merge with system envs
        EnvConfig.load();

        WebDriverManager.chromedriver().setup();

ChromeOptions options = new ChromeOptions();
options.setPageLoadStrategy(PageLoadStrategy.EAGER);

// Read headless mode from .env (defaults to true for background execution)
boolean headless = Boolean.parseBoolean(EnvConfig.get("HEADLESS", "true"));
String windowSize = EnvConfig.get("WINDOW_SIZE", "1920,1080");

System.out.println("\n========== BROWSER SETUP ==========");
System.out.println("Headless Mode: " + headless);
System.out.println("Window Size: " + windowSize);
System.out.println("====================================\n");

// Common arguments for both headless and visible modes
options.addArguments(
        "--disable-blink-features=AutomationControlled",
        "--remote-allow-origins=*",
        "--log-level=3",
        "--no-first-run",
        "--disable-new-tab-first-run",
        "--disable-notifications",
        "--disable-popup-blocking",
        "--disable-extensions",
        "--disable-default-apps",
        "--disable-sync"
);

if (headless) {
    // Enable headless mode with multiple fallback options for compatibility
    options.addArguments(
            "--headless=new",  // Newer headless mode (Chrome 109+)
            "--headless",      // Fallback to older headless mode
            "--window-size=" + windowSize,
            "--disable-gpu",   // Disable GPU acceleration in headless mode
            "--no-sandbox",    // Important for CI/CD environments
            "--disable-dev-shm-usage"  // Prevent memory issues in headless mode
    );
    System.out.println("✅ HEADLESS MODE ENABLED - Browser running in background");
} else {
    // Visible browser mode for debugging
    options.addArguments(
            "--start-maximized"
    );
    System.out.println("⚠️  VISIBLE MODE ENABLED - Browser window will be displayed");
}

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("🚀 Browser initialized ONCE for all tests (headless=" + headless + ")");
        System.out.println("✅ WebDriver ready for automation");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("=========================================\n");

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
            loginPage.loginAs(VALID_USERNAME, VALID_PASSWORD);

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
            String baseUrl = EnvConfig.get("BASE_URL", "https://uat_mcdp_hcm.omfysgroup.com");
            driver.get(baseUrl + "/onboarding_admin");

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
