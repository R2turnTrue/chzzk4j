package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import xyz.r2turntrue.chzzk4j.exception.ChatFailedConnectException;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatWebsocketClient extends WebSocketClient {

    private ChzzkChat chat;
    private Gson gson;
    private String sid;
    private ScheduledExecutorService executor;
    private long lastSendPingTime;
    private long lastRecivedMessageTime;

    public ChatWebsocketClient(ChzzkChat chat, URI websocketUri) {
        super(websocketUri);
        this.chat = chat;
        this.gson = new Gson()
                .newBuilder()
                .disableHtmlEscaping()
                .create();
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    private HashMap<Integer, Class<?>> clientboundMessages = new HashMap<>() {{
        put(WsMessageTypes.Commands.CONNECTED, WsMessageClientboundConnected.class);
        put(WsMessageTypes.Commands.RECENT_CHAT, WsMessageClientboundRecentChat.class);
    }};

    @SuppressWarnings("unchecked")
    private Class<? extends WsMessageBase> getClientboundMessageClass(int id) {
        return (Class<? extends WsMessageBase>) clientboundMessages.get(id);
    }

    private <T extends WsMessageBase> T setupWsMessage(T wsMessage) {
        wsMessage.cid = chat.chatId;

        return wsMessage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        if (chat.chzzk.isDebug) System.out.println("Connected to websocket! Connecting to chat...");

        lastRecivedMessageTime = lastSendPingTime = System.currentTimeMillis();
        WsMessageServerboundConnect handshake = setupWsMessage(new WsMessageServerboundConnect());
        handshake.bdy = new WsMessageServerboundConnect.Body();
        handshake.bdy.accTkn = chat.accessToken;
        handshake.bdy.auth = chat.chzzk.isLoggedIn() ? "SEND" : "READ";
        handshake.bdy.uid = chat.userId;

        this.send(gson.toJson(handshake));
    }

    private void processChatMessage(ChatMessage msg) {
        for (ChatEventListener listener : chat.listeners) {
            //System.out.println("CC: " + msg.chatTypeCode);
            if (msg.msgTypeCode == WsMessageTypes.ChatTypes.DONATION || msg.getExtras().getPayAmount() > 0)
                listener.onDonationChat((DonationMessage) msg);
            else if (msg.msgTypeCode == WsMessageTypes.ChatTypes.SUBSCRIPTION)
                listener.onSubscriptionChat((SubscriptionMessage) msg);
            else
                listener.onChat(msg);
        }
    }

    @Override
    public void onMessage(String message) {

        try {
            if (chat.chzzk.isDebug) System.out.println("Message: " + message);

            JsonObject parsedMessage = JsonParser.parseString(message)
                    .getAsJsonObject();

            var cmdId = parsedMessage
                    .get("cmd")
                    .getAsInt();

            var messageClass = getClientboundMessageClass(cmdId);

            if (messageClass == WsMessageClientboundConnected.class) {
                // handle connected message
                WsMessageClientboundConnected msg = gson.fromJson(parsedMessage, WsMessageClientboundConnected.class);
                if (msg.retCode == 0) {
                    if (chat.chzzk.isDebug) System.out.println("Successfully connected!");
                    sid = msg.bdy.sid;
                    for (ChatEventListener listener : chat.listeners) {
                        listener.onConnect(chat, chat.reconnecting);
                    }

                    Runnable task = () -> {
                        if(System.currentTimeMillis() - lastSendPingTime >= 60000 || // 1 minutes from last ping time
                            System.currentTimeMillis() - lastRecivedMessageTime >= 20000) { // 20 seconds later from last message
                            if (chat.chzzk.isDebug) {
                                System.out.println("need client ping: current = " + (System.currentTimeMillis() / 1000) +
                                        ", ping = " + (lastSendPingTime / 1000)  +
                                        ", recived message = " + (lastRecivedMessageTime/1000));
                            }

                            if(isOpen()) {
                                this.send(gson.toJson(new WsMessageServerboundPing()));
                            }

                            lastRecivedMessageTime = lastSendPingTime = System.currentTimeMillis();
                        }
                    };

                    executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
                } else {
                    throw new ChatFailedConnectException(msg.retCode, msg.retMsg);
                }
            } else if (cmdId == WsMessageTypes.Commands.PING) {
                if (chat.chzzk.isDebug) {
                    System.out.println("pong");
                    System.out.println(gson.toJson(new WsMessageServerboundPong()));
                }
                this.send(gson.toJson(new WsMessageServerboundPong()));
            } else if (messageClass == WsMessageClientboundRecentChat.class) {

                WsMessageClientboundRecentChat msg = gson.fromJson(parsedMessage, WsMessageClientboundRecentChat.class);

                for (WsMessageClientboundRecentChat.Body.RecentChat chat : msg.bdy.messageList) {

                    if (chat.userId.equals("@OPEN")) continue;

                    Class<? extends ChatMessage> clazz = ChatMessage.class;

                    if (chat.messageTypeCode == WsMessageTypes.ChatTypes.DONATION) {
                        clazz = DonationMessage.class;
                    } else if (chat.messageTypeCode == WsMessageTypes.ChatTypes.SUBSCRIPTION) {
                        clazz = SubscriptionMessage.class;
                    }

                    try {
                        processChatMessage(chat.toChatMessage(clazz));
                    } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                }

            } else if (cmdId == WsMessageTypes.Commands.CHAT ||
                    cmdId == WsMessageTypes.Commands.DONATION) {
                lastRecivedMessageTime = System.currentTimeMillis();

                WsMessageClientboundChat msg = gson.fromJson(parsedMessage, WsMessageClientboundChat.class);

                //System.out.println(msg.bdy.length);

                for (WsMessageClientboundChat.Chat chat : msg.bdy) {
                    if (chat.uid.equals("@OPEN")) continue;

                    Class<? extends ChatMessage> clazz = ChatMessage.class;

                    if (chat.msgTypeCode == WsMessageTypes.ChatTypes.DONATION) {
                        clazz = DonationMessage.class;
                    } else if (chat.msgTypeCode == WsMessageTypes.ChatTypes.SUBSCRIPTION) {
                        clazz = SubscriptionMessage.class;
                    }

                    try {
                        processChatMessage(chat.toChatMessage(clazz));
                    } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e);
                    }
                    //System.out.println(chat.toChatMessage());
                }

            }

        } catch (Exception ex) {
            for (ChatEventListener listener : chat.listeners) {
                listener.onError(ex);
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        chat.isConnectedToWebsocket = false;

        boolean shouldReconnect = remote && chat.autoReconnect;

        for (ChatEventListener listener : chat.listeners) {
            listener.onConnectionClosed(code, reason, remote, shouldReconnect);
        }

        if (chat.chzzk.isDebug) {
            System.out.println("Websocket connection closed.");
            System.out.println("Code: " + code);
            System.out.println("Reason: " + reason);
            System.out.println("Remote Close: " + remote);
            System.out.println("Reconnect: " + shouldReconnect);
        }

        if (shouldReconnect) {
            chat.reconnectAsync();
        }

        executor.shutdownNow();
    }

    @Override
    public void onError(Exception ex) {
        for (ChatEventListener listener : chat.listeners) {
            listener.onError(ex);
        }
    }

    public void sendChat(String content) {
        var msg = setupWsMessage(new WsMessageServerboundSendChat());
        msg.sid = sid;
        var extras = new WsMessageServerboundSendChat.Body.Extras();
        extras.streamingChannelId = chat.channelId;
        msg.bdy.extras = gson.toJson(extras);
        msg.bdy.msg = content;
        msg.bdy.msgTypeCode = WsMessageTypes.ChatTypes.TEXT;

        send(gson.toJson(msg));
    }

    public void requestRecentChat(int chatCount) {
        var msg = setupWsMessage(new WsMessageServerboundRequestRecentChat());
        msg.tid = 2;
        msg.bdy.recentMessageCount = chatCount;
        msg.sid = sid;
        //System.out.println(gson.toJson(msg));
        send(gson.toJson(msg));
    }
}
