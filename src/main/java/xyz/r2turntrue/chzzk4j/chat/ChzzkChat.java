package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkUser;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.AlreadyConnectedException;
import java.util.ArrayList;
import java.util.Arrays;

public class ChzzkChat {
    Chzzk chzzk;

    private boolean isConnected = false;
    private ChatWebsocketClient client;
    ArrayList<ChatEventListener> listeners = new ArrayList<>();

    String accessToken;
    String userId;
    String channelId;
    String chatId;

    public ChzzkChat(Chzzk chzzk) {
        this.chzzk = chzzk;
    }

    public void addListener(ChatEventListener listener) {
        listeners.add(listener);
    }

    public void requestRecentChat(int chatCount) {
        client.requestRecentChat(chatCount);
    }

    public void connectFromChannelId(String channelId) throws IOException {
        String chatId = RawApiUtils.getContentJson(chzzk.getHttpClient(),
                RawApiUtils.httpGetRequest(Chzzk.API_URL + "/service/v1/channels/" + channelId + "/live-detail").build())
                .getAsJsonObject()
                .get("chatChannelId")
                .getAsString();

        connectFromChatId(channelId, chatId);
    }

    void connectFromChatId(String channelId, String chatId) throws IOException {
        if (isConnected) {
            throw new AlreadyConnectedException();
        }

        isConnected = true;

        this.channelId = channelId;
        this.chatId = chatId;

        userId = "";
        try {
            ChzzkUser user = chzzk.getLoggedUser();
            userId = user.getUserId();
        } catch (NotLoggedInException e) {
        }

        String accessTokenUrl = Chzzk.GAME_API_URL +
                "/v1/chats/access-token?channelId=" + chatId +
                "&chatType=STREAMING";
        accessToken = RawApiUtils.getContentJson(
                chzzk.getHttpClient(),
                RawApiUtils.httpGetRequest(accessTokenUrl).build()
        ).getAsJsonObject().get("accessToken").getAsString();

        int serverId = 0;

        for (char i : channelId.toCharArray()) {
            serverId += Character.getNumericValue(i);
        }

        serverId = Math.abs(serverId) % 9 + 1;

        client = new ChatWebsocketClient(this,
                URI.create("wss://kr-ss" + serverId + ".chat.naver.com/chat"));

        client.connect();
    }

    public void close() {
        if (!isConnected) {
            throw new IllegalStateException("Not connected!");
        }

        client.close();
        isConnected = false;
    }
}
