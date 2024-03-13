import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.chat.ChatEventListener;
import xyz.r2turntrue.chzzk4j.chat.ChatMessage;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ChatTest extends ChzzkTestBase {
    @Test
    void testingChat() throws IOException, InterruptedException {
        ChzzkChat chat = chzzk.chat();
        chzzk.isDebug = true;

        System.out.println(new Gson().toJson(RawApiUtils.getContentJson(chzzk.getHttpClient(),
                RawApiUtils.httpGetRequest("https://api.chzzk.naver.com/service/v2/channels/dc7fb0d085cfbbe90e11836e3b85b784/live-detail").build(), chzzk.isDebug)));
        chat.connectFromChannelId("7ce8032370ac5121dcabce7bad375ced");
        chat.addListener(new ChatEventListener() {
            @Override
            public void onConnect() {
                System.out.println("Connect received!");
                //chat.sendChat("ㅋㅋㅋㅋ");
                chat.requestRecentChat(50);
            }

            @Override
            public void onChat(ChatMessage msg) {
                if (msg.getProfile() == null) {
                    System.out.println("[Chat] 익명: " + msg.getContent());
                    return;
                }

                System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
            }

            @Override
            public void onDonationChat(ChatMessage msg) {
                if (msg.getProfile() == null) {
                    System.out.println("[Chat] 익명: " + msg.getContent());
                    return;
                }

                System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " [" + msg.getExtras().getPayAmount() + "원]");
            }
        });
        Thread.sleep(700000);
        chat.close();
    }
}