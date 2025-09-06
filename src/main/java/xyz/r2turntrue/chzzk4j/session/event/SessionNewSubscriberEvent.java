package xyz.r2turntrue.chzzk4j.session.event;

import xyz.r2turntrue.chzzk4j.session.message.SessionNewSubscriberMessage;

public class SessionNewSubscriberEvent extends SessionEvent {
    private SessionNewSubscriberMessage message;

    public SessionNewSubscriberEvent(SessionNewSubscriberMessage message) {
        this.message = message;
    }

    public SessionNewSubscriberMessage getMessage() {
        return message;
    }
}
