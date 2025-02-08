import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkOauthLoginAdapter;
import xyz.r2turntrue.chzzk4j.naver.NaverAutologinAdapter;

import java.util.Arrays;

public class BothLoginTest extends ChzzkTestBase {

    String naverId;
    String naverPw;

    public BothLoginTest() {
        naverId = properties.getProperty("NAVER_ID");
        naverPw = properties.getProperty("NAVER_PW");
    }

    @Test
    public void testBothLogin() {
        Assertions.assertDoesNotThrow(() -> {
            var oauthAdapter = new ChzzkOauthLoginAdapter(5000);
            var naverAdapter = new NaverAutologinAdapter(naverId, naverPw);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(oauthAdapter)
                    .withLoginAdapter(naverAdapter)
                    .build();

            System.out.println(oauthAdapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            client.refreshTokenAsync().join();

            System.out.println(client.fetchLoggedUser());
            System.out.println(Arrays.toString(client.fetchFollowingChannels()));
        });
    }

}