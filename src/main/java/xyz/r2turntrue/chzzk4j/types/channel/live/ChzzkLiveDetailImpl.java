package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

public class ChzzkLiveDetailImpl extends ChzzkLiveStatusImpl implements ChzzkLiveDetail {

    private int liveId;
    private String liveImageUrl;
    private String defaultThumbnailImageUrl;
    private String openDate;
    private String closeDate;
    private ChzzkLiveChannel channel;

    @Override
    public int getLiveId() {
        return liveId;
    }

    @Override
    public @NotNull String getLiveImageUrl(@NotNull Resolution resolution) {
        return liveImageUrl.replace("{type}", resolution.getRawAsString());
    }

    @Override
    public @NotNull String getDefaultThumbnailImageUrl() {
        return defaultThumbnailImageUrl;
    }

    @Override
    public @NotNull Optional<LocalDateTime> getOpenDate() {
        if (openDate == null) {
            return Optional.empty();
        }
        return Optional.of(LocalDateTime.parse(openDate));
    }

    @Override
    public @NotNull Optional<LocalDateTime> getCloseDate() {
        if (closeDate == null) {
            return Optional.empty();
        }
        return Optional.of(LocalDateTime.parse(closeDate));
    }

    @Override
    public @NotNull ChzzkLiveChannel getLiveChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "ChzzkLiveDetailImpl{" +
                "liveId=" + liveId +
                ", liveImageUrl='" + liveImageUrl + '\'' +
                ", defaultThumbnailImageUrl='" + defaultThumbnailImageUrl + '\'' +
                ", openDate='" + openDate + '\'' +
                ", closeDate='" + closeDate + '\'' +
                ", channel=" + channel +
                ", liveTitle='" + liveTitle + '\'' +
                ", status='" + status + '\'' +
                ", concurrentUserCount=" + concurrentUserCount +
                ", accumulateCount=" + accumulateCount +
                ", paidPromotion=" + paidPromotion +
                ", adult=" + adult +
                ", clipActive=" + clipActive +
                ", chatChannelId='" + chatChannelId + '\'' +
                ", tags=" + tags +
                ", categoryType='" + categoryType + '\'' +
                ", liveCategory='" + liveCategory + '\'' +
                ", liveCategoryValue='" + liveCategoryValue + '\'' +
                ", livePollingStatusJson='" + livePollingStatusJson + '\'' +
                ", faultStatus=" + faultStatus +
                ", userAdultStatus=" + userAdultStatus +
                ", chatActive=" + chatActive +
                ", chatAvailableGroup='" + chatAvailableGroup + '\'' +
                ", chatAvailableCondition='" + chatAvailableCondition + '\'' +
                ", minFollowerMinute=" + minFollowerMinute +
                ", chatDonationRankingExposure=" + chatDonationRankingExposure +
                '}';
    }
}
