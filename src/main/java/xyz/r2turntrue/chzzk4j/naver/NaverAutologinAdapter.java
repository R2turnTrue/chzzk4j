package xyz.r2turntrue.chzzk4j.naver;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginAdapter;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginResult;
import xyz.r2turntrue.chzzk4j.util.Chrome;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class NaverAutologinAdapter implements ChzzkLoginAdapter {

    private final @NotNull String id;
    private final @NotNull String password;
    private final @NotNull Map<NaverAutologinAdapter.Cookie, String> cookies;

    /**
     * Log in to Naver using ID and password.
     * @param id naver id
     * @param password password of the naver id
     */
    public NaverAutologinAdapter(@NotNull String id, @NotNull String password) {
        this.id = id;
        this.password = password;
        this.cookies = Maps.newConcurrentMap();
    }

    @Override
    public CompletableFuture<ChzzkLoginResult> authorize(ChzzkClient client) {
        return CompletableFuture.supplyAsync(() -> {
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

                // Wait until the specific cookies are available
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(driver1 -> driver1.manage().getCookieNamed("NID_AUT") != null);

                // Find cookies
                cookies.clear();
                for (NaverAutologinAdapter.Cookie key : NaverAutologinAdapter.Cookie.values()) {
                    org.openqa.selenium.Cookie cookie = driver.manage().getCookieNamed(key.toString());
                    if (cookie != null) {
                        cookies.put(key, cookie.getValue());
                    }
                }

                System.out.println("NID_AUT: " + getCookie(Cookie.NID_AUT));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                driver.quit();
            }

            return new ChzzkLoginResult(
                    getCookie(Cookie.NID_AUT),
                    getCookie(Cookie.NID_SES),
                    null,
                    null,
                    -1);
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
     * @param key {@link NaverAutologinAdapter.Cookie}
     */
    public @NotNull String getCookie(@NotNull NaverAutologinAdapter.Cookie key) {
        return cookies.getOrDefault(key, "");
    }

    public enum Cookie {
        NID_AUT, NID_SES
    }
}
