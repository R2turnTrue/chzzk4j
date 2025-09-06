package xyz.r2turntrue.chzzk4j.session.message;

import java.util.Objects;

public class SessionNewSubscriberMessage {
    private String channelId;
    private String subscriberChannelId;
    private String subscriberNickname;
    private int tierNo;
    private String tierName;
    private int month;

    public String getChannelId() {
        return channelId;
    }

    public String getSubscriberChannelId() {
        return subscriberChannelId;
    }

    public String getSubscriberNickname() {
        return subscriberNickname;
    }

    public int getTierNo() {
        return tierNo;
    }

    public String getTierName() {
        return tierName;
    }

    public int getMonth() {
        return month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionNewSubscriberMessage that = (SessionNewSubscriberMessage) o;
        return tierNo == that.tierNo && month == that.month && Objects.equals(channelId, that.channelId) && Objects.equals(subscriberChannelId, that.subscriberChannelId) && Objects.equals(subscriberNickname, that.subscriberNickname) && Objects.equals(tierName, that.tierName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, subscriberChannelId, subscriberNickname, tierNo, tierName, month);
    }

    @Override
    public String toString() {
        return "SessionSubscriptionMessage{" +
                "channelId='" + channelId + '\'' +
                ", subscriberChannelId='" + subscriberChannelId + '\'' +
                ", subscriberNickname='" + subscriberNickname + '\'' +
                ", tierNo=" + tierNo +
                ", tierName='" + tierName + '\'' +
                ", month=" + month +
                '}';
    }
}
