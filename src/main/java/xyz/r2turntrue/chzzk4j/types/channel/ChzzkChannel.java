package xyz.r2turntrue.chzzk4j.types.channel;

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
}
