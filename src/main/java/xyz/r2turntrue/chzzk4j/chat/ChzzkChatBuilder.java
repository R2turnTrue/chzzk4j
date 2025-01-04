package xyz.r2turntrue.chzzk4j.chat;

import xyz.r2turntrue.chzzk4j.ChzzkClient;

import java.io.IOException;
import java.util.ArrayList;

public class ChzzkChatBuilder {

    private String channelId;
    private ChzzkClient chzzk;
    private boolean autoReconnect = true;

    public ChzzkChatBuilder(ChzzkClient chzzk, String channelId) {
        this.chzzk = chzzk;
        this.channelId = channelId;
    }

    public ChzzkChatBuilder withAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;

        return this;
    }

    public ChzzkChat build() throws IOException {
        return new ChzzkChat(chzzk, channelId, autoReconnect);
    }

}
