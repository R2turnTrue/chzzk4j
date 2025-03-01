package xyz.r2turntrue.chzzk4j.session.event;

import xyz.r2turntrue.chzzk4j.session.ChzzkSessionSubscriptionType;

public class SessionUnsubscribedEvent extends SessionEvent {
    private ChzzkSessionSubscriptionType eventType;
    private String channelId;

    public SessionUnsubscribedEvent(ChzzkSessionSubscriptionType eventType, String channelId) {
        this.eventType = eventType;
        this.channelId = channelId;
    }

    public ChzzkSessionSubscriptionType getEventType() {
        return eventType;
    }

    public String getChannelId() {
        return channelId;
    }
}
