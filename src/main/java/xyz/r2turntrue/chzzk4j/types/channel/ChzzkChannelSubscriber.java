package xyz.r2turntrue.chzzk4j.types.channel;

import java.util.Objects;

public class ChzzkChannelSubscriber {
    public enum SortBy {
        RECENT,
        LONGER
    }

    private String channelId;
    private String channelName;
    private int month;
    private int tierNo;
    private String createdDate;

    @Override
    public String toString() {
        return "ChzzkChannelSubscriber{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", month=" + month +
                ", tierNo=" + tierNo +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChannelSubscriber that = (ChzzkChannelSubscriber) o;
        return month == that.month && tierNo == that.tierNo && Objects.equals(channelId, that.channelId) && Objects.equals(channelName, that.channelName) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, channelName, month, tierNo, createdDate);
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public int getMonth() {
        return month;
    }

    public int getTierNo() {
        return tierNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
