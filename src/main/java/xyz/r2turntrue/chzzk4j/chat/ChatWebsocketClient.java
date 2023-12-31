package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import xyz.r2turntrue.chzzk4j.exception.ChatFailedConnectException;

import java.net.URI;
import java.util.HashMap;

public class ChatWebsocketClient extends WebSocketClient {

    private ChzzkChat chat;
    private Gson gson;
    private String sid;

    public ChatWebsocketClient(ChzzkChat chat, URI websocketUri) {
        super(websocketUri);
        this.chat = chat;
        this.gson = new Gson()
                .newBuilder()
                .disableHtmlEscaping()
                .create();
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

        WsMessageServerboundConnect handshake = setupWsMessage(new WsMessageServerboundConnect());
        handshake.bdy = new WsMessageServerboundConnect.Body();
        handshake.bdy.accTkn = chat.accessToken;
        handshake.bdy.auth = chat.chzzk.isLoggedIn() ? "SEND" : "READ";
        handshake.bdy.uid = chat.userId;

        //System.out.println(gson.toJson(handshake));
        this.send(gson.toJson(handshake));
    }

    private void processChatMessage(ChatMessage msg) {
        for (ChatEventListener listener : chat.listeners) {
            //System.out.println("CC: " + msg.chatTypeCode);
            if (msg.chatTypeCode == WsMessageTypes.ChatTypes.DONATION || msg.getExtras().getPayAmount() > 0)
                listener.onDonationChat(msg);
            else
                listener.onChat(msg);
        }
    }

    @Override
    public void onMessage(String message) {
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
                for(ChatEventListener listener : chat.listeners) {
                    listener.onConnect();
                }
            } else {
                throw new ChatFailedConnectException(msg.retCode, msg.retMsg);
            }
        } else if (cmdId == WsMessageTypes.Commands.PING) {
            this.send(gson.toJson(new WsMessageServerboundPong()));
        } else if (messageClass == WsMessageClientboundRecentChat.class) {

            WsMessageClientboundRecentChat msg = gson.fromJson(parsedMessage, WsMessageClientboundRecentChat.class);

            for (WsMessageClientboundRecentChat.Body.RecentChat chat : msg.bdy.messageList) {
                processChatMessage(chat.toChatMessage());
            }

        } else if (cmdId == WsMessageTypes.Commands.CHAT ||
                cmdId == WsMessageTypes.Commands.DONATION ||
                cmdId == WsMessageTypes.Commands.NOTICE || // ??
                cmdId == WsMessageTypes.Commands.BLIND || // ??
                cmdId == WsMessageTypes.Commands.PENALTY || // ??
                cmdId == WsMessageTypes.Commands.EVENT) { // ??

            WsMessageClientboundChat msg = gson.fromJson(parsedMessage, WsMessageClientboundChat.class);

            //System.out.println(msg.bdy.length);

            for (WsMessageClientboundChat.Chat chat : msg.bdy) {
                processChatMessage(chat.toChatMessage());
                //System.out.println(chat.toChatMessage());
            }

        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        chat.isConnectedToWebsocket = false;
        for (ChatEventListener listener : chat.listeners) {
            listener.onConnectionClosed(code, reason, remote);
        }

        if (chat.chzzk.isDebug) {
            System.out.println("Websocket connection closed.");
            System.out.println("Code: " + code);
            System.out.println("Reason: " + reason);
            System.out.println("Remote Close: " + remote);
        }
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
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
