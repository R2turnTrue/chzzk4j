import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.chat.*;
import xyz.r2turntrue.chzzk4j.chat.event.*;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ChatTest extends ChzzkTestBase {
    @Test
    void testingChat() throws IOException, InterruptedException, ExecutionException {
        ChzzkChat chat = new ChzzkChatBuilder(chzzk,
                "36ddb9bb4f17593b60f1b63cec86611d")
                .withAutoReconnect(true)
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


        chat.on(NormalDonationEvent.class, (evt) -> {
            DonationMessage msg = evt.getMessage();

            System.out.println(msg);

            if (msg.getProfile() == null) {
                System.out.println("[Donation] 익명: " + msg.getContent() + " - " + msg.getPayAmount());
                return;
            }

            System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " - " + msg.getPayAmount());
        });

        chat.connectBlocking();

        //chat.requestRecentChat(50);
        Thread.sleep(100000000);
        chat.closeBlocking();
    }
}