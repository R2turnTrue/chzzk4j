import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.naver.Naver;

public class NaverLoginTest extends NaverTestBase {

    @Test
    public void testNaverLogin() {
        Assertions.assertDoesNotThrow(() -> {
            naver.login().thenRun(() -> {
                for (Naver.Cookie value : Naver.Cookie.values()) {
                    System.out.println(value.toString() + ": " + naver.getCookie(value));
                }
            }).join();
        });
    }

}
