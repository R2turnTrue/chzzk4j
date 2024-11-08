package xyz.r2turntrue.chzzk4j.naver;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import xyz.r2turntrue.chzzk4j.util.Chrome;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Naver {

    private final @NotNull String id;
    private final @NotNull String password;
    private final @NotNull Map<Cookie, String> cookies;

    /**
     * Log in to Naver using ID and password.
     * @param id naver id
     * @param password password of the naver id
     */
    public Naver(@NotNull String id, @NotNull String password) {
        this.id = id;
        this.password = password;
        this.cookies = Maps.newConcurrentMap();
    }

    /**
     * Login to naver with id and password
     */
    public CompletableFuture<Void> login() {
        return CompletableFuture.runAsync(() -> {
            WebDriver driver = Chrome.getDriver();
            driver.get("https://nid.naver.com/nidlogin.login");
            try {
                // Write id and pw fields
                if (driver instanceof JavascriptExecutor js) {
                    js.executeScript(String.format("document.getElementById('id').value='%s';", id));
                    js.executeScript(String.format("document.getElementById('pw').value='%s';", password));
                }

                // Click login button
                WebElement loginBtn = driver.findElement(By.id("log.login"));
                loginBtn.click();

                // Find cookies
                cookies.clear();
                for (Cookie key : Cookie.values()) {
                    org.openqa.selenium.Cookie cookie = driver.manage().getCookieNamed(key.toString());
                    if (cookie != null) {
                        cookies.put(key, cookie.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                driver.quit();
            }
        });
    }

    public @NotNull String getId() {
        return id;
    }

    public @NotNull String getPassword() {
        return password;
    }

    /**
     * If logged into Naver, it returns the cookie value.
     * If not logged in, it returns an empty string.
     * @param key {@link Cookie}
     */
    public @NotNull String getCookie(@NotNull Naver.Cookie key) {
        return cookies.getOrDefault(key, "");
    }

    public enum Cookie {
        NID_AUT, NID_SES
    }

}
