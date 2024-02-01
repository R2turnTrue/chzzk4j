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
        ChzzkChat chat = chzzk.chat();
        chzzk.isDebug = true;
        chat.connectFromChannelId("0b33823ac81de48d5b78a38cdbc0ab94");
        chat.addListener(new ChatEventListener() {
            @Override
            public void onConnect() {
                System.out.println("Connect received!");
                //chat.sendChat("ㅋㅋㅋㅋ");
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