package xyz.r2turntrue.chzzk4j.types;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class ChzzkUser {
    boolean hasProfile;
    String userIdHash;
    String nickname;
    String profileImageUrl;
    Object[] penalties; // unknown
    boolean officialNotiAgree;
    String officialNotiAgreeUpdatedDate;
    boolean verifiedMark;
    boolean loggedIn;

    public ChzzkUser() {}


    /**
     * Get the user has profile.
     */
    public boolean isHasProfile() {
        return hasProfile;
    }

    /**
     * Get the user's id.
     */
    public String getUserId() {
        return userIdHash;
    }

    /**
     * Get the nickname of the user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get url of the user's profile image.
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    /**
     * Get user agreed to official notification.
     */
    public boolean isOfficialNotiAgree() {
        return officialNotiAgree;
    }

    /**
     * Get when user agreed to official notification in ISO-8601 format.
     */
    @Nullable
    public String getOfficialNotiAgreeUpdatedDate() {
        return officialNotiAgreeUpdatedDate;
    }

    /**
     * Get user has verified mark.
     */
    public boolean isVerifiedMark() {
        return verifiedMark;
    }

    public void _setUserId(String userId) {
        userIdHash = userId;
    }

    public void _setHasProfile(boolean value) {
        hasProfile = value;
    }

    public void _setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void _setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "ChzzkUser{" +
                "hasProfile=" + hasProfile +
                ", userIdHash='" + userIdHash + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", penalties=" + Arrays.toString(penalties) +
                ", officialNotiAgree=" + officialNotiAgree +
                ", officialNotiAgreeUpdatedDate='" + officialNotiAgreeUpdatedDate + '\'' +
                ", verifiedMark=" + verifiedMark +
                ", loggedIn=" + loggedIn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkUser chzzkUser = (ChzzkUser) o;
        return hasProfile == chzzkUser.hasProfile && officialNotiAgree == chzzkUser.officialNotiAgree && verifiedMark == chzzkUser.verifiedMark && loggedIn == chzzkUser.loggedIn && Objects.equals(userIdHash, chzzkUser.userIdHash) && Objects.equals(nickname, chzzkUser.nickname) && Objects.equals(profileImageUrl, chzzkUser.profileImageUrl) && Objects.deepEquals(penalties, chzzkUser.penalties) && Objects.equals(officialNotiAgreeUpdatedDate, chzzkUser.officialNotiAgreeUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasProfile, userIdHash, nickname, profileImageUrl, Arrays.hashCode(penalties), officialNotiAgree, officialNotiAgreeUpdatedDate, verifiedMark, loggedIn);
    }
}
