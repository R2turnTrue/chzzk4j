package xyz.r2turntrue.chzzk4j.types.channel;

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
}
