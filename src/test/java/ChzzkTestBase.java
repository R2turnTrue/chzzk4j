import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.ChzzkClientBuilder;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLegacyLoginAdapter;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginAdapter;
import xyz.r2turntrue.chzzk4j.naver.NaverAutologinAdapter;

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
        this(false);
    }

    public ChzzkTestBase(boolean naverLogin) {
        try {
            properties.load(new FileInputStream("env.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Setup...");

        currentUserId = properties.getProperty("CURRENT_USER_ID");

        apiClientId = properties.getProperty("API_CLIENT_ID");
        apiSecret = properties.getProperty("API_SECRET");

        /*
        ChzzkLegacyLoginAdapter adapter = new ChzzkLegacyLoginAdapter(
                properties.getProperty("NID_AUT"),
                properties.getProperty("NID_SES")
        );
         */

        ChzzkLoginAdapter adapter;

        if (naverLogin) {
            var naverId = properties.getProperty("NAVER_ID");
            var naverPw = properties.getProperty("NAVER_PW");
            adapter = new NaverAutologinAdapter(
                    naverId,
                    naverPw
            );
        } else {
            adapter = new ChzzkLegacyLoginAdapter(
                    properties.getProperty("NID_AUT"),
                    properties.getProperty("NID_SES")
            );
        }

        chzzk = new ChzzkClientBuilder(apiClientId, apiSecret)
                .withDebugMode()
                .build();
        loginChzzk = new ChzzkClientBuilder(apiClientId, apiSecret)
                .withDebugMode()
                .withLoginAdapter(adapter)
                .build();
        loginChzzk.loginAsync().join();
    }
}
