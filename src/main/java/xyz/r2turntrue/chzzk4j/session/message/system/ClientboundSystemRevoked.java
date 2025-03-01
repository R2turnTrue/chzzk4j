package xyz.r2turntrue.chzzk4j.session.message.system;

import xyz.r2turntrue.chzzk4j.session.ChzzkSessionSubscriptionType;

public class ClientboundSystemRevoked {
    private String eventType;
    private String channelId;

    public ChzzkSessionSubscriptionType getEventType() {
        return ChzzkSessionSubscriptionType.valueOf(eventType);
    }

    public String getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return "ClientboundSystemRevoked{" +
                "eventType='" + eventType + '\'' +
                ", channelId='" + channelId + '\'' +
                '}';
    }
}
