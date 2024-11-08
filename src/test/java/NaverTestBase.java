
import xyz.r2turntrue.chzzk4j.naver.Naver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class NaverTestBase {

    protected Properties properties = new Properties();
    protected Naver naver;

    public NaverTestBase() {
        try {
            properties.load(new FileInputStream("env.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.naver = new Naver(properties.getProperty("NAVER_ID"), properties.getProperty("NAVER_PW"));
    }

}
