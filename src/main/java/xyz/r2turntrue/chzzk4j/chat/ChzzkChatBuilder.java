package xyz.r2turntrue.chzzk4j.chat;

import xyz.r2turntrue.chzzk4j.Chzzk;

import java.io.IOException;
import java.util.ArrayList;

public class ChzzkChatBuilder {

    private ArrayList<ChatEventListener> listeners = new ArrayList<>();
    private String channelId;
    private Chzzk chzzk;
    private boolean autoReconnect = true;

    public ChzzkChatBuilder(Chzzk chzzk, String channelId) {
        this.chzzk = chzzk;
        this.channelId = channelId;
    }

    public ChzzkChatBuilder withChatListener(ChatEventListener listener) {
        listeners.add(listener);

        return this;
    }

    public ChzzkChatBuilder withAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;

        return this;
    }

    public ChzzkChat build() throws IOException {
        ChzzkChat chat = new ChzzkChat(chzzk, channelId, autoReconnect);

        for (ChatEventListener listener : listeners) {
            chat.addListener(listener);
        }

        return chat;
    }

}
