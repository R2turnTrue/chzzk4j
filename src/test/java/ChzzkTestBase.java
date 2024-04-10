import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.ChzzkBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ChzzkTestBase {
    Properties properties = new Properties();
    String currentUserId = "";
    Chzzk chzzk;
    Chzzk loginChzzk;

    public ChzzkTestBase() {
        try {
            properties.load(new FileInputStream("env.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentUserId = properties.getProperty("CURRENT_USER_ID");
        chzzk = new ChzzkBuilder()
                .withDebugMode()
                .build();
        loginChzzk = new ChzzkBuilder()
                .withDebugMode()
                .withAuthorization(properties.getProperty("NID_AUT"), properties.getProperty("NID_SES"))
                .build();
    }
}
