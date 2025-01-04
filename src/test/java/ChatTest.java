import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.chat.*;
import xyz.r2turntrue.chzzk4j.chat.event.*;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;

public class ChatTest extends ChzzkTestBase {
    @Test
    void testingChat() throws IOException, InterruptedException {
        ChzzkChat chat = new ChzzkChatBuilder(chzzk,
                "2086f44c7b09a17cef6786f21389db3b")
                .build();

        chat.on(ConnectEvent.class, (evt) -> {
            System.out.println("WebSocket connected! :)");
        });

        chat.on(ChatMessageEvent.class, (evt) -> {
            ChatMessage msg = evt.getMessage();

            System.out.println(msg);

            if (msg.getProfile() == null) {
                System.out.println("[Chat] 익명: " + msg.getContent());
                return;
            }

            System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
        });

        chat.connectBlocking();
        Thread.sleep(10000);
        chat.closeBlocking();
    }
}