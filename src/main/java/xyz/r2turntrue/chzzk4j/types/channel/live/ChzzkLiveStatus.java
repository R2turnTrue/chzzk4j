package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChzzkLiveStatus {

    protected String liveTitle;
    protected String status;
    protected int concurrentUserCount;
    protected int accumulateCount;
    protected boolean paidPromotion;
    protected boolean adult;
    protected boolean clipActive;
    protected String chatChannelId;
    protected List<String> tags;
    protected String categoryType;
    protected String liveCategory;
    protected String liveCategoryValue;
    protected String livePollingStatusJson;
    protected Object faultStatus;
    protected Object userAdultStatus;
    protected boolean chatActive;
    protected String chatAvailableGroup;
    protected String chatAvailableCondition;
    protected int minFollowerMinute;
    protected boolean chatDonationRankingExposure;

    public @NotNull String getTitle() {
        return liveTitle;
    }

    public boolean isOnline() {
        return status.equalsIgnoreCase("open");
    }

    public int getUserCount() {
        return concurrentUserCount;
    }

    public int getAccumulateUserCount() {
        return accumulateCount;
    }

    public boolean hasPaidPromotion() {
        return paidPromotion;
    }

    public boolean isNSFW() {
        return adult;
    }

    public boolean isClipActive() {
        return clipActive;
    }

    public @NotNull String getChatChannelId() {
        return chatChannelId;
    }

    public @NotNull List<String> getTags() {
        return List.copyOf(tags);
    }

    public @NotNull String getCategoryType() {
        return categoryType;
    }

    public @NotNull String getLiveCategory() {
        return liveCategory;
    }

    public @NotNull String getLiveCategoryValue() {
        return liveCategoryValue;
    }

    public boolean isChatActive() {
        return chatActive;
    }

    public @NotNull String getChatAvailableGroup() {
        return chatAvailableGroup;
    }

    public @NotNull String getChatAvailableCondition() {
        return chatAvailableCondition;
    }

    public int getMinFollowerMinute() {
        return minFollowerMinute;
    }

    public boolean isChatDonationRankingExposure() {
        return chatDonationRankingExposure;
    }

    @Override
    public String toString() {
        return "ChzzkLiveStatusImpl{" +
                "liveTitle='" + liveTitle + '\'' +
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
