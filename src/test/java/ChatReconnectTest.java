import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.chat.*;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;

public class ChatReconnectTest extends ChzzkTestBase {
    // to test memory leaks
    @Test
    void testingChatReconnect() throws IOException, InterruptedException {
        ChzzkChat chat = chzzk.chat("e23a2e05c02e77a3516f14f3f4f4312b")
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
