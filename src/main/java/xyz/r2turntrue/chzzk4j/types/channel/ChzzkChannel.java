package xyz.r2turntrue.chzzk4j.types.channel;

import java.util.Objects;

public class ChzzkChannel extends ChzzkPartialChannel {
    private String channelDescription;
    private int followerCount;
    private boolean openLive;

    private ChzzkChannel() {
        super();
    }

    /**
     * Get description of the channel.
     */
    public String getChannelDescription() {
        return channelDescription;
    }

    /**
     * Get the count of the channel's followers.
     */
    public int getFollowerCount() {
        return followerCount;
    }

    /**
     * Get is the channel broadcasting.
     */
    public boolean isBroadcasting() {
        return openLive;
    }

    @Override
    public String toString() {
        return "ChzzkChannel{" +
                "parent=" + super.toString() +
                ", channelDescription='" + channelDescription + '\'' +
                ", followerCount=" + followerCount +
                ", openLive=" + openLive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChzzkChannel that = (ChzzkChannel) o;
        return followerCount == that.followerCount && openLive == that.openLive && Objects.equals(channelDescription, that.channelDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), channelDescription, followerCount, openLive);
    }
}
