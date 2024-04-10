import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.chat.*;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;

public class ChatReconnectTest extends ChzzkTestBase {
    // to test memory leaks
    @Test
    void testingChatReconnect() throws IOException, InterruptedException {
        ChzzkChat chat = chzzk.chat("dfa51694131f7e970c7a62ccde3bc915")
                        .withChatListener(new ChatEventListener() {
                            @Override
                            public void onConnect(ChzzkChat chat, boolean isReconnecting) {
                                chat.reconnectAsync();
                            }
                        })
                        .build();
        chat.connectBlocking();
        Thread.sleep(700000);
        chat.closeBlocking();
    }
}
