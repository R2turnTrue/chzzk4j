package xyz.r2turntrue.chzzk4j.chat;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

class WsMessageClientboundChat extends WsMessageBase {
    public WsMessageClientboundChat() {
        super(WsMessageTypes.Commands.CHAT);
    }

    static class Chat {
        public String uid;
        public String msg;
        public int msgTypeCode;
        public long ctime;
        public String extras;
        public String profile;
        public String msgStatusType;

        public <T extends ChatMessage> T toChatMessage(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
            var msg = (T) clazz.getConstructors()[0].newInstance();

            msg.rawJson = new Gson().toJson(this);

            msg.content = this.msg;
            msg.msgTypeCode = msgTypeCode;
            msg.createTime = new Date(ctime);
            msg.extras = new Gson().fromJson(extras, ChatMessage.Extras.class);
            msg.profile = new Gson().fromJson(profile, ChatMessage.Profile.class);
            msg.userId = uid;
            return msg;
        }
    }

    public Chat[] bdy;
}
