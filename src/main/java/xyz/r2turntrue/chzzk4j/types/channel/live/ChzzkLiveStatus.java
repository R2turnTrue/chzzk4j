package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ChzzkLiveStatus {

    @NotNull String getTitle();

    boolean isOnline();

    int getUserCount();

    int getAccumulateUserCount();

    boolean hasPaidPromotion();

    boolean isNSFW();

    boolean isClipActive();

    @NotNull String getChatChannelId();

    @NotNull List<String> getTags();

    @NotNull String getCategoryType();

    @NotNull String getLiveCategory();

    @NotNull String getLiveCategoryValue();

    boolean isChatActive();

    @NotNull String getChatAvailableGroup();

    @NotNull String getChatAvailableCondition();

    int getMinFollowerMinute();

    boolean isChatDonationRankingExposure();

}
