package xyz.r2turntrue.chzzk4j.chat;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class ChatMessage {
    public static class Extras {
        // todo: emoji implementation
        //public Emoji[] emojis;

        // todo: osType as enum
        String osType;

        int payAmount = -1;

        int month = 0;
        String tierName = "";

        public String getOsType() {
            return osType;
        }

        public int getPayAmount() {
            return payAmount;
        }

        public int getSubscriptionMonth() {
            return month;
        }

        public String getSubscriptionTierName() {
            return tierName;
        }

        @Override
        public String toString() {
            return "Extras{" +
                    "osType='" + osType + '\'' +
                    ", payAmount=" + payAmount +
                    '}';
        }
    }

    public static class Profile {
        String nickname;
        String profileImageUrl;
        String userRoleCode;
        boolean verifiedMark;

        public String getNickname() {
            return nickname;
        }

        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        public String getUserRoleCode() {
            return userRoleCode;
        }

        public boolean isVerifiedMark() {
            return verifiedMark;
        }

        @Override
        public String toString() {
            return "Profile{" +
                    "nickname='" + nickname + '\'' +
                    ", profileImageUrl='" + profileImageUrl + '\'' +
                    ", userRoleCode='" + userRoleCode + '\'' +
                    ", verifiedMark=" + verifiedMark +
                    '}';
        }
    }

    int chatTypeCode = 0;
    String userId;
    String content;
    Date createTime;
    Extras extras = new Extras();
    Profile profile = new Profile();

    public int getChatTypeCode() {
        return chatTypeCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Extras getExtras() {
        return extras;
    }

    /**
     * Returns profile of sender of the message.
     * @return nullable {@link Profile}
     */
    @Nullable
    public Profile getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "userId='" + userId + '\'' +
                ", chatTypeCode='" + chatTypeCode + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", extras=" + extras +
                ", profile=" + profile +
                '}';
    }
}
