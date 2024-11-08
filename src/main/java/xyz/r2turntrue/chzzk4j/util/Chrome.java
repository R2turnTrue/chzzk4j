package xyz.r2turntrue.chzzk4j.util;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Chrome {

    /**
     * Explicitly sets the properties for the chrome driver.
     */
    public static void setDriverProperty(@NotNull String path) {
        System.setProperty("webdriver.chrome.driver", path);
    }

    /**
     * Get chrome web driver.
     */
    public static WebDriver getDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return driver;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JavascriptExecutor getDriverAsJavascriptExecutor() {
        return (JavascriptExecutor) getDriver();
    }

}
