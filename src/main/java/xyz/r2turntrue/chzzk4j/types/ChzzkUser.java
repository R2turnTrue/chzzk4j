package xyz.r2turntrue.chzzk4j.types;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ChzzkUser {
    private boolean hasProfile;
    private String userIdHash;
    private String nickname;
    private String profileImageUrl;
    private Object[] penalties; // unknown
    private boolean officialNotiAgree;
    private String officialNotiAgreeUpdatedDate;
    private boolean verifiedMark;
    private boolean loggedIn;

    private ChzzkUser() {}

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
}
