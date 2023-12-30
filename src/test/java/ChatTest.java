import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.chat.ChatEventListener;
import xyz.r2turntrue.chzzk4j.chat.ChatMessage;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ChatTest extends ChzzkTestBase {
    @Test
    void testingChat() throws IOException, InterruptedException {
        ChzzkChat chat = loginChzzk.chat();
        chat.connectFromChannelId("b2665a30ba249486bf2c134973cfc7a2");
        chat.addListener(new ChatEventListener() {
            @Override
            public void onConnect() {
                System.out.println("Connect received!");
                chat.requestRecentChat(50);
            }

            @Override
            public void onChat(ChatMessage msg) {
                System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
            }

            @Override
            public void onDonationChat(ChatMessage msg) {
                System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " [" + msg.getExtras().getPayAmount() + "원]");
            }
        });
        Thread.sleep(700000);
        chat.close();
    }
}