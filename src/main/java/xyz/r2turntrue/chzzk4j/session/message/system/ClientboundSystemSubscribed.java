package xyz.r2turntrue.chzzk4j.session.message.system;

import xyz.r2turntrue.chzzk4j.session.ChzzkSessionSubscriptionType;

public class ClientboundSystemSubscribed {
    private String eventType;
    private String channelId;

    public ChzzkSessionSubscriptionType getEventType() {
        if (eventType.equals("SUBSCRIPTION")) {
            return ChzzkSessionSubscriptionType.CHANNEL_SUBSCRIBE;
        }
        return ChzzkSessionSubscriptionType.valueOf(eventType);
    }

    public String getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return "ClientboundSystemSubscribed{" +
                "eventType='" + eventType + '\'' +
                ", channelId='" + channelId + '\'' +
                '}';
    }
}
