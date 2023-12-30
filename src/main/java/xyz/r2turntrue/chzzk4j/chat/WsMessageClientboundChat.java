package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.Gson;

import java.util.Date;

class WsMessageClientboundChat extends WsMessageBase {
    public WsMessageClientboundChat() {
        super(WsMessageTypes.Commands.CHAT);
    }

    static class Chat {
        public String uid;
        public String msg;
        public int messageTypeCode;
        public long ctime;
        public String extras;
        public String profile;

        public ChatMessage toChatMessage() {
            var msg = new ChatMessage();
            msg.content = this.msg;
            msg.chatTypeCode = messageTypeCode;
            msg.createTime = new Date(ctime);
            msg.extras = new Gson().fromJson(extras, ChatMessage.Extras.class);
            msg.profile = new Gson().fromJson(profile, ChatMessage.Profile.class);
            msg.userId = uid;
            return msg;
        }
    }

    public Chat[] bdy;
}
