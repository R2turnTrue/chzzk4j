package xyz.r2turntrue.chzzk4j.types.channel;

public class ChzzkChannelPersonalData {
    private ChzzkChannelFollowingData following;
    private boolean privateUserBlock;

    private ChzzkChannelPersonalData() {}

    /**
     * Get following status of the logged user about the channel.
     */
    public ChzzkChannelFollowingData getFollowing() {
        return following;
    }

    public boolean isPrivateUserBlock() {
        return privateUserBlock;
    }
}
