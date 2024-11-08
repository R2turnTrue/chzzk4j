package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ChzzkLiveDetail extends ChzzkLiveStatus {

    private transient final @NotNull DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final transient @NotNull ZoneId zoneId = ZoneId.of("Asia/Seoul");

    private int liveId;
    private String liveImageUrl;
    private String defaultThumbnailImageUrl;
    private String openDate;
    private String closeDate;
    private ChzzkLiveChannel channel;

    public int getLiveId() {
        return liveId;
    }

    public @NotNull String getLiveImageUrl(@NotNull Resolution resolution) {
        return liveImageUrl.replace("{type}", resolution.getRawAsString());
    }

    public @NotNull Optional<String> getDefaultThumbnailImageUrl() {
        return Optional.ofNullable(defaultThumbnailImageUrl);
    }

    public @NotNull Optional<ZonedDateTime> getOpenDate() {
        if (openDate == null) {
            return Optional.empty();
        }
        ZonedDateTime date = LocalDateTime.parse(openDate, formatter).atZone(zoneId);
        return Optional.of(date);
    }

    public @NotNull Optional<ZonedDateTime> getCloseDate() {
        if (closeDate == null) {
            return Optional.empty();
        }
        ZonedDateTime date = LocalDateTime.parse(closeDate, formatter).atZone(zoneId);
        return Optional.of(date);
    }

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
