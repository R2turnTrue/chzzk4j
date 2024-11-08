package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

public class ChzzkLiveChannelImpl implements ChzzkLiveChannel {

    private String channelId;
    private String channelName;
    private String channelImageUrl;
    private boolean verifiedMark;

    @Override
    public @NotNull String getId() {
        return channelId;
    }

    @Override
    public @NotNull String getName() {
        return channelName;
    }

    @Override
    public @NotNull String getImageUrl() {
        return channelImageUrl;
    }

    @Override
    public boolean hasVerifiedMark() {
        return verifiedMark;
    }

    @Override
    public String toString() {
        return "ChzzkLiveChannelImpl{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelImageUrl='" + channelImageUrl + '\'' +
                ", verifiedMark=" + verifiedMark +
                '}';
    }

}
