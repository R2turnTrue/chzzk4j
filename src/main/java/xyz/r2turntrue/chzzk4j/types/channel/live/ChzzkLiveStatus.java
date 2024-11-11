package xyz.r2turntrue.chzzk4j.types.channel.live;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

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

    /**
     * Get title of the live stream.
     */
    public @NotNull String getTitle() {
        return liveTitle;
    }

    /**
     * Get live stream is opened.
     */
    public boolean isOnline() {
        return status.equalsIgnoreCase("open");
    }

    /**
     * Get current number of viewers watching the live stream.
     */
    public int getUserCount() {
        return concurrentUserCount;
    }

    /**
     * Get cumulative count of viewers.
     */
    public int getAccumulateUserCount() {
        return accumulateCount;
    }

    /**
     * Indicates the presence of paid promotions.
     */
    public boolean hasPaidPromotion() {
        return paidPromotion;
    }

    /**
     * Indicates whether the channel is adult-only.
     */
    public boolean isNSFW() {
        return adult;
    }

    /**
     * Indicates whether clips are enabled.
     */
    public boolean isClipActive() {
        return clipActive;
    }

    /**
     * Get unique ID number of the chat room.
     */
    public @NotNull String getChatChannelId() {
        return chatChannelId;
    }

    /**
     * Get tags of the live stream.
     */
    public @NotNull List<String> getTags() {
        return List.copyOf(tags);
    }

    /**
     * Get main category of the broadcast.
     * Typically, it returns "GAME" for game broadcasts, "ETC" for others,
     * and "null" if no category is set.
     */
    public @NotNull Optional<String> getCategoryType() {
        return Optional.ofNullable(categoryType);
    }

    /**
     * Get subcategory of the live stream.
     */
    public @NotNull Optional<String> getLiveCategory() {
        return Optional.ofNullable(liveCategory);
    }

    /**
     * Get display name of the subcategory.
     */
    public @NotNull String getLiveCategoryValue() {
        return liveCategoryValue;
    }

    /**
     * Get chat activation state of the live stream.
     */
    public boolean isChatActive() {
        return chatActive;
    }

    /**
     * Get group of viewers who are allowed to send chat messages.
     */
    public @NotNull String getChatAvailableGroup() {
        return chatAvailableGroup;
    }

    /**
     * Get conditions that viewers must meet to be able to send chat messages.
     */
    public @NotNull String getChatAvailableCondition() {
        return chatAvailableCondition;
    }

    /**
     * Get minimum follow time required to send chat messages.
     */
    public int getMinFollowerMinute() {
        return minFollowerMinute;
    }

    /**
     * Indicates whether the chat donation ranking is displayed.
     */
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
