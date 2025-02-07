import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkOauthLoginAdapter;
import xyz.r2turntrue.chzzk4j.naver.NaverAutologinAdapter;

public class OauthLoginTest extends ChzzkTestBase {
    @Test
    public void testOauthLogin() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter();

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println(adapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            System.out.println(client.fetchLoggedUser());
        });
    }

}