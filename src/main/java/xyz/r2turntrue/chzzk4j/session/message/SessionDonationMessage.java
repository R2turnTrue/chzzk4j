package xyz.r2turntrue.chzzk4j.session.message;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class SessionDonationMessage {
    public enum DonationType {
        CHAT,
        VIDEO
    }

    private String donationType;
    private String channelId;
    private String donatorChannelId;
    private String donatorNickname;
    private int payAmount;
    private String donationText;
    public Map<String, String> emojis;

    public DonationType getDonationType() {
        return DonationType.valueOf(donationType);
    }

    public String getReceivedChannelId() {
        return channelId;
    }

    public String getDonatorChannelId() {
        return donatorChannelId;
    }

    public String getDonatorNickname() {
        return donatorNickname;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public String getDonationText() {
        return donationText;
    }

    @Nullable
    public String getEmojiImgUrl(String emojiId) {
        return emojis.get(emojiId);
    }

    public Map<String, String> getEmojis() {
        return emojis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDonationMessage that = (SessionDonationMessage) o;
        return payAmount == that.payAmount && Objects.equals(donationType, that.donationType) && Objects.equals(channelId, that.channelId) && Objects.equals(donatorChannelId, that.donatorChannelId) && Objects.equals(donatorNickname, that.donatorNickname) && Objects.equals(donationText, that.donationText) && Objects.equals(emojis, that.emojis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(donationType, channelId, donatorChannelId, donatorNickname, payAmount, donationText, emojis);
    }

    @Override
    public String toString() {
        return "SessionDonationMessage{" +
                "donationType='" + donationType + '\'' +
                ", channelId='" + channelId + '\'' +
                ", donatorChannelId='" + donatorChannelId + '\'' +
                ", donatorNickname='" + donatorNickname + '\'' +
                ", payAmount=" + payAmount +
                ", donationText='" + donationText + '\'' +
                ", emojis=" + emojis +
                '}';
    }
}
