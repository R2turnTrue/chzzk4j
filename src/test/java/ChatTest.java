import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.chat.*;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ChatTest extends ChzzkTestBase {
    @Test
    void testingChat() throws IOException, InterruptedException {
        ChzzkChat chat = loginChzzk.chat("17aa057a8248b53affe30512a91481f5")
                .withChatListener(new ChatEventListener() {
                    @Override
                    public void onConnect(ChzzkChat chat, boolean isReconnecting) {
                        System.out.println("Connect received!");
                        //chat.sendChat("ㅋㅋㅋㅋ");

                        if (!isReconnecting)
                            chat.requestRecentChat(50);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }

                    @Override
                    public void onChat(ChatMessage msg) {

                        System.out.println(msg);

                        if (msg.getProfile() == null) {
                            System.out.println("[Chat] 익명: " + msg.getContent());
                            return;
                        }

                        System.out.println("[Chat] " + msg.getProfile().getNickname() + ": " + msg.getContent());
                    }

                    @Override
                    public void onDonationChat(DonationMessage msg) {
                        if (msg.getProfile() == null) {
                            System.out.println("[Donation] 익명: " + msg.getContent() + ": " + msg.getContent() + " [" + msg.getPayAmount() + "원]");
                            return;
                        }

                        System.out.println("[Donation] " + msg.getProfile().getNickname() + ": " + msg.getContent() + " [" + msg.getPayAmount() + "원]");
                    }

                    @Override
                    public void onSubscriptionChat(SubscriptionMessage msg) {
                        if (msg.getProfile() == null) {
                            System.out.println("[Subscription] 익명: " + msg.getContent() + ": [" + msg.getSubscriptionMonth() + "개월 " + msg.getSubscriptionTierName() + "]");
                            return;
                        }

                        System.out.println("[Subscription] " + msg.getProfile().getNickname() + ": [" + msg.getSubscriptionMonth() + "개월 " + msg.getSubscriptionTierName() + "]");
                    }

                    @Override
                    public void onMissionDonationChat(MissionDonationMessage msg) {
                        if (msg.getProfile() == null) {
                            System.out.println("[Mission] 익명: " + msg.getMissionText() + ": [" + msg.getPayAmount() + "원]");
                            return;
                        }

                        System.out.println("[Mission] 익명: " + msg.getMissionText() + ": [" + msg.getPayAmount() + "원]");
                    }
                })
                .build();

        System.out.println(new Gson().toJson(RawApiUtils.getContentJson(chzzk.getHttpClient(),
                RawApiUtils.httpGetRequest("https://api.chzzk.naver.com/service/v2/channels/dc7fb0d085cfbbe90e11836e3b85b784/live-detail").build(), chzzk.isDebug)));
        chat.connectBlocking();
        Thread.sleep(700000);
        chat.closeBlocking();
    }
}