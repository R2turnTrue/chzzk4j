import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.naver.NaverAutologinAdapter;
import xyz.r2turntrue.chzzk4j.util.Chrome;

import java.io.IOException;
import java.util.concurrent.CompletionException;

public class NaverLoginTest extends ChzzkTestBase {

    String naverId;
    String naverPw;

    public NaverLoginTest() {
        naverId = properties.getProperty("NAVER_ID");
        naverPw = properties.getProperty("NAVER_PW");
    }

    @Test
    public void testNaverLogin() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new NaverAutologinAdapter(
                    naverId,
                    naverPw
            );

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            client.loginAsync().join();

            System.out.println(client.fetchLoggedUser());
        });
    }

    @Test
    public void testNaverLoginFailed() {
        Assertions.assertThrowsExactly(CompletionException.class, () -> {
            Chrome.setDriverProperty("");
            var adapter = new NaverAutologinAdapter(
                    naverId,
                    naverPw
            );

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            client.loginAsync().join();
        });
    }

}
