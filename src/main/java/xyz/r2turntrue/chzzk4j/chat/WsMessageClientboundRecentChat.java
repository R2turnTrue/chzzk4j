package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.Gson;

import java.util.Date;

class WsMessageClientboundRecentChat extends WsMessageBase {
    static class Body {
        static class RecentChat {
            public String userId;
            public String content;
            public int messageTypeCode;
            public long createTime;
            public String extras;
            public String profile;

            public ChatMessage toChatMessage() {
                var msg = new ChatMessage();
                msg.content = content;
                msg.chatTypeCode = messageTypeCode;
                msg.createTime = new Date(createTime);
                msg.extras = new Gson().fromJson(extras, ChatMessage.Extras.class);
                msg.profile = new Gson().fromJson(profile, ChatMessage.Profile.class);
                msg.userId = userId;
                return msg;
            }
        }

        public RecentChat[] messageList;
        public int userCount;
    }

    public WsMessageClientboundRecentChat() {
        super(WsMessageTypes.Commands.RECENT_CHAT);
    }

    public Body bdy;
}
