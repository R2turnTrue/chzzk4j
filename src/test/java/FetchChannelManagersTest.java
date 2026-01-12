import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkOauthLoginAdapter;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelManager;

import java.util.Arrays;

public class FetchChannelManagersTest extends ChzzkTestBase {

    @Test
    public void testFetchChannelManagers() {
        Assertions.assertDoesNotThrow(() -> {
            var adapter = new ChzzkOauthLoginAdapter(8084);

            var client = new ChzzkClientBuilder(apiClientId, apiSecret)
                    .withDebugMode()
                    .withLoginAdapter(adapter)
                    .build();

            System.out.println("Please login via this URL: " + adapter.getAccountInterlockUrl(apiClientId, false));

            client.loginAsync().join();

            System.out.println("Logged in!");

            ChzzkChannelManager[] managers = client.fetchChannelManagers().get();

            System.out.println("Managers: " + Arrays.toString(managers));

            Assertions.assertNotNull(managers);
        });
    }
}
