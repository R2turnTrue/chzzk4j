package xyz.r2turntrue.chzzk4j.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Chrome {

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public static JavascriptExecutor getDriverAsJavascriptExecutor() {
        return (JavascriptExecutor) getDriver();
    }

}
