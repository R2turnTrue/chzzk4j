package xyz.r2turntrue.chzzk4j.chat.event;

import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;

public class ConnectEvent extends ChzzkEvent {
    private ChzzkChat chat;
    private boolean reconnecting;

    public ConnectEvent(ChzzkChat chat, boolean reconnecting) {
        this.chat = chat;
        this.reconnecting = reconnecting;
    }

    public ChzzkChat getChat() {
        return chat;
    }

    public boolean isReconnecting() {
        return reconnecting;
    }
}
