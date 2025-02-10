package xyz.r2turntrue.chzzk4j.types.channel;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChannelPersonalData that = (ChzzkChannelPersonalData) o;
        return privateUserBlock == that.privateUserBlock && Objects.equals(following, that.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(following, privateUserBlock);
    }

    @Override
    public String toString() {
        return "ChzzkChannelPersonalData{" +
                "following=" + following +
                ", privateUserBlock=" + privateUserBlock +
                '}';
    }
}
