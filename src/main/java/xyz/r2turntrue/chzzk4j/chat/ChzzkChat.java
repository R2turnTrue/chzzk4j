package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.JsonElement;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.chat.event.ChzzkEvent;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkUser;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.AlreadyConnectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class ChzzkChat {
    boolean reconnecting;
    ChzzkClient chzzk;

    boolean isConnectedToWebsocket = false;
    private ChatWebsocketClient client;

    HashMap<Class<? extends ChzzkEvent>, ArrayList<Consumer<? extends ChzzkEvent>>> handlerMap = new HashMap<>();

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

    public String getChatId() {
        return chatId;
    }

    public String getChannelId() {
        return channelId;
    }

    ChzzkChat(ChzzkClient chzzk, String channelId, boolean autoReconnect) {
        this.chzzk = chzzk;
        this.channelId = channelId;
        this.autoReconnect = autoReconnect;
    }

    public <T extends ChzzkEvent> void on(Class<T> clazz, Consumer<T> action) {
        if (!handlerMap.containsKey(clazz)) {
            handlerMap.put(clazz, new ArrayList<>());
        }

        handlerMap.get(clazz).add(action);
    }

    public <T extends ChzzkEvent> void emit(Class<T> clazz, T obj) {
        if (handlerMap.containsKey(clazz)) {
            for (Consumer<? extends ChzzkEvent> handler : handlerMap.get(clazz)) {
                @SuppressWarnings("unchecked")
                Consumer<T> specificHandler = (Consumer<T>) handler;
                specificHandler.accept(obj);
            }
        }
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
    public void connectBlocking() throws ExecutionException, InterruptedException {
        connectFromChannelId(channelId, autoReconnect).get();
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
     * @throws UnsupportedOperationException when failed to fetch chatChannelId! (Try to put NID_SES/NID_AUT when create {@link ChzzkClient}, because it's mostly caused by age restriction)
     */
    private CompletableFuture<Void> connectFromChannelId(String channelId, boolean autoReconnect) {
        return CompletableFuture.runAsync(() -> {
            try {
                JsonElement chatIdRaw = RawApiUtils.getContentJson(chzzk.getHttpClient(),
                                RawApiUtils.httpGetRequest(ChzzkClient.API_URL + "/service/v3/channels/" + channelId + "/live-detail").build(), chzzk.isDebug)
                        .getAsJsonObject()
                        .get("chatChannelId");

                if (chatIdRaw.isJsonNull()) {
                    throw new UnsupportedOperationException("Failed to fetch chatChannelId! (Try to put NID_SES/NID_AUT, because it's mostly caused by age restriction)");
                }

                connectFromChatId(channelId, chatIdRaw.getAsString(), autoReconnect).get();
            } catch (IOException | ExecutionException | InterruptedException e) {
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
                    ChzzkUser user = chzzk.fetchLoggedUser().get();
                    userId = user.getUserId();
                } catch (NotLoggedInException e) {
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }

                String accessTokenUrl = ChzzkClient.GAME_API_URL +
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

                client.reconnectBlocking();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void reconnectSync() throws ExecutionException, InterruptedException {
        reconnectAsync().get();
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

    public void closeBlocking() throws ExecutionException, InterruptedException {
        closeAsync().get();
    }
}
