package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ChzzkLiveDetail extends ChzzkLiveStatus {

    int getLiveId();

    @NotNull String getLiveImageUrl(@NotNull Resolution resolution);

    @NotNull String getDefaultThumbnailImageUrl();

    @NotNull Optional<LocalDateTime> getOpenDate();

    @NotNull Optional<LocalDateTime> getCloseDate();

    @NotNull ChzzkLiveChannel getLiveChannel();

}
