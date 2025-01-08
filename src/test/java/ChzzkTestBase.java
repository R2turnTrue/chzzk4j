import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLegacyLoginAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChzzkTestBase {
    Properties properties = new Properties();
    String currentUserId = "";
    ChzzkClient chzzk;
    ChzzkClient loginChzzk;

    String apiClientId;
    String apiSecret;

    public ChzzkTestBase() {
        try {
            properties.load(new FileInputStream("env.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentUserId = properties.getProperty("CURRENT_USER_ID");

        apiClientId = properties.getProperty("API_CLIENT_ID");
        apiSecret = properties.getProperty("API_SECRET");
        ChzzkLegacyLoginAdapter adapter = new ChzzkLegacyLoginAdapter(
                properties.getProperty("NID_AUT"),
                properties.getProperty("NID_SES")
        );

        chzzk = new ChzzkClientBuilder(apiClientId, apiSecret)
                .withDebugMode()
                .build();
        loginChzzk = new ChzzkClientBuilder(apiClientId, apiSecret)
                .withDebugMode()
                .withLoginAdapter(adapter)
                .build();
        loginChzzk.loginAsync();
    }
}
