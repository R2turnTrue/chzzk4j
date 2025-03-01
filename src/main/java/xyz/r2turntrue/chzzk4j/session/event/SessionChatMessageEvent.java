package xyz.r2turntrue.chzzk4j.session.event;

import xyz.r2turntrue.chzzk4j.session.message.SessionChatMessage;

public class SessionChatMessageEvent extends SessionEvent {
    private SessionChatMessage message;

    public SessionChatMessageEvent(SessionChatMessage message) {
        this.message = message;
    }

    public SessionChatMessage getMessage() {
        return message;
    }
}
