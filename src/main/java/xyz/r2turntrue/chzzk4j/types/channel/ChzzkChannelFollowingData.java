package xyz.r2turntrue.chzzk4j.types.channel;

import java.util.Objects;

public class ChzzkChannelFollowingData {
    private boolean following;
    private boolean notification;
    private String followDate;

    private ChzzkChannelFollowingData() {}

    /**
     * Get is me following the channel.
     */
    public boolean isFollowing() {
        return following;
    }

    /**
     * Get is me enabled the channel notification.
     */
    public boolean isEnabledNotification() {
        return notification;
    }

    /**
     * Get when me followed the channel in yyyy-mm-dd HH:mm:ss format.
     */
    public String getFollowDate() {
        return followDate;
    }

    @Override
    public String toString() {
        return "ChzzkChannelFollowingData{" +
                "following=" + following +
                ", notification=" + notification +
                ", followDate='" + followDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChannelFollowingData that = (ChzzkChannelFollowingData) o;
        return following == that.following && notification == that.notification && Objects.equals(followDate, that.followDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(following, notification, followDate);
    }
}
