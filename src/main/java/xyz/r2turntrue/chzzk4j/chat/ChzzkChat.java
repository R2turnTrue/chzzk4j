package xyz.r2turntrue.chzzk4j.chat;

import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkUser;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.AlreadyConnectedException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ChzzkChat {
    boolean reconnecting;
    Chzzk chzzk;

    boolean isConnectedToWebsocket = false;
    private ChatWebsocketClient client;
    ArrayList<ChatEventListener> listeners = new ArrayList<>();

    String accessToken;
    String userId;
    String channelId;
    String chatId;

    boolean autoReconnect = false;

    public boolean isConnectedToChat() {
        return isConnectedToWebsocket;
    }

    public boolean shouldAutoReconnect() {
        return autoReconnect;
    }

    ChzzkChat(Chzzk chzzk, String channelId, boolean autoReconnect) {
        this.chzzk = chzzk;
        this.channelId = channelId;
        this.autoReconnect = autoReconnect;
    }

    /**
     * Connects to the chat. This method doesn't block.
     */
    public CompletableFuture<Void> connectAsync() {
        return connectFromChannelId(channelId, autoReconnect);
    }

    /**
     * Connects to the chat. This method blocks.
     */
    public void connectBlocking() {
        connectFromChannelId(channelId, autoReconnect).join();
    }

    @Deprecated
    /**
     * @deprecated Please add listeners when build {@link ChzzkChat} by {@link ChzzkChatBuilder}
     */
    public void addListener(ChatEventListener listener) {
        listeners.add(listener);
    }

    public void requestRecentChat(int chatCount) {
        if (!isConnectedToWebsocket) {
            throw new IllegalStateException("Connect to request recent chats!");
        }

        client.requestRecentChat(chatCount);
    }

    public void sendChat(String content) {
        if (!isConnectedToWebsocket) {
            throw new IllegalStateException("Connect to send chat!");
        }

        client.sendChat(content);
    }

    /**
     * Connect to chatting by the channel id
     *
     * @param channelId channel id to connect.
     * @param autoReconnect should reconnect automatically when disconnected by the server.
     * @throws IOException when failed to connect to the chat
     */
    private CompletableFuture<Void> connectFromChannelId(String channelId, boolean autoReconnect) {
        return CompletableFuture.runAsync(() -> {
            try {
                String chatId = RawApiUtils.getContentJson(chzzk.getHttpClient(),
                                RawApiUtils.httpGetRequest(Chzzk.API_URL + "/service/v2/channels/" + channelId + "/live-detail").build(), chzzk.isDebug)
                        .getAsJsonObject()
                        .get("chatChannelId")
                        .getAsString();

                connectFromChatId(channelId, chatId, autoReconnect).join();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private CompletableFuture<Void> connectFromChatId(String channelId, String chatId, boolean autoReconnect) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (isConnectedToWebsocket) {
                    throw new AlreadyConnectedException();
                }

                reconnecting = false;

                this.autoReconnect = autoReconnect;

                isConnectedToWebsocket = true;

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
                        RawApiUtils.httpGetRequest(accessTokenUrl).build(),
                        chzzk.isDebug
                ).getAsJsonObject().get("accessToken").getAsString();

                int serverId = 0;

                for (char i : channelId.toCharArray()) {
                    serverId += Character.getNumericValue(i);
                }

                serverId = Math.abs(serverId) % 9 + 1;

                client = new ChatWebsocketClient(this,
                        URI.create("wss://kr-ss" + serverId + ".chat.naver.com/chat"));

                client.connectBlocking();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    int reconnectCount = 0;

    public CompletableFuture<Void> reconnectAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                if (client == null) {
                    throw new IllegalStateException("Client not initalized to reconnect!");
                }

                URI chatUri = client.getURI();

                if (!client.isClosed() && !client.isClosing()) {
                    try {
                        client.closeBlocking();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                reconnecting = true;

                reconnectCount++;

                System.out.println("Reconnecting " + reconnectCount);

                client.reconnectBlocking();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void reconnectSync() {
        reconnectAsync().join();
    }

    public CompletableFuture<Void> closeAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                if (!isConnectedToWebsocket) {
                    throw new IllegalStateException("Not connected!");
                }

                client.closeBlocking();
                isConnectedToWebsocket = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void closeBlocking() {
        closeAsync().join();
    }
}
