package xyz.r2turntrue.chzzk4j.types.channel;

import java.util.Objects;

public class ChzzkChannelFollower {
    private String channelId;
    private String channelName;
    private String createdDate;

    public String getChannelId() {
        return channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChannelFollower that = (ChzzkChannelFollower) o;
        return Objects.equals(channelId, that.channelId) && Objects.equals(channelName, that.channelName) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, channelName, createdDate);
    }

    @Override
    public String toString() {
        return "ChzzkChannelFollower{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
