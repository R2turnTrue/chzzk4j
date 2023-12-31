package xyz.r2turntrue.chzzk4j.chat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

class WsMessageServerboundSendChat extends WsMessageBase {
    static class Body {
        static class Extras {
            String chatType = "STREAMING";
            String osType = "PC";
            String streamingChannelId = "";
            String emojis = "";
        }

        String extras;
        String msg;
        long msgTime = System.currentTimeMillis();
        int msgTypeCode = WsMessageTypes.ChatTypes.TEXT;
    }

    public WsMessageServerboundSendChat() {
        super(WsMessageTypes.Commands.SEND_CHAT);
    }

    Body bdy = new Body();

    int tid = 3;
    String sid;
}
