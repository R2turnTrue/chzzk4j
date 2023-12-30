package xyz.r2turntrue.chzzk4j.chat;

import java.util.Date;

public class ChatMessage {
    public static class Extras {
        // todo: emoji implementation
        //public Emoji[] emojis;

        // todo: osType as enum
        String osType;

        int payAmount = -1;

        public String getOsType() {
            return osType;
        }

        public int getPayAmount() {
            return payAmount;
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
