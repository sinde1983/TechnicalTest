package com.identity.test.helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class BasePage {

    public static WebDriver driver;
    public static FluentWait<WebDriver> wait;
    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);

    TestContext testContext = TestContext.getInstance();

    public void initialiseDriver() {
        if(driver!=null){
            return;
        }
        getLocalDriver();
    }

    private void getLocalDriver() {
        String browserType = testContext.getProperty("browser");
        switch (browserType.toUpperCase()) {
            case "CHROME":
                startChromeDriver();
                break;
            case "FIREFOX":
                startFireFoxDriver();
                break;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static void startFireFoxDriver() {
        FirefoxOptions options = getFireFoxOptions();
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }

    private static void startChromeDriver() {
        ChromeOptions options = getChromeOptions();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    private static ChromeOptions getChromeOptions() {
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.DRIVER, Level.OFF);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("allow-running-insecure-content");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.setCapability("acceptSslCerts", true);
        chromeOptions.setCapability("use_subprocess", true);
        return chromeOptions;
    }

    private static FirefoxOptions getFireFoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("network.cookie.cookieBehavior", 1);
        profile.setPreference("startup.homepage_welcome_url.additional", "");
        profile.setPreference("network.proxy.type", 0);
        options.setCapability(FirefoxDriver.PROFILE, profile);
        options.setCapability("marionette", true);
        options.setCapability("platform", "ANY");
        options.setCapability("disable-restore-session-state", true);
        options.setCapability("acceptInsecureCerts", true);
        return options;
    }

    public boolean isAlertPresent()
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException Ex)
        {
            return false;
        }
    }

    public void timeUnitWait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            return;
        }
    }

    public void waitForPage() {
        LOG.info("Running : waiting for page to load");
        new WebDriverWait(driver, 60).until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                        .equals("complete"));
    }

    public WebElement waitForElementToBeDisplay(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public List<WebElement> waitForAllElementsToBeDisplay(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public WebElement waitForPresenceOfElement(WebElement element) {
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
        return element;
    }
}
