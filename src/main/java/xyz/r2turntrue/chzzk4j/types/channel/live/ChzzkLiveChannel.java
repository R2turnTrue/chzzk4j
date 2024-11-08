package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

public interface ChzzkLiveChannel {

    @NotNull String getId();

    @NotNull String getName();

    @NotNull String getImageUrl();

    boolean hasVerifiedMark();

}
