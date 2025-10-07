package xyz.r2turntrue.chzzk4j.session.message;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

public class SessionChatMessage {
    public static class Profile {
        public static class Badge {
            private String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Badge badge = (Badge) o;
                return Objects.equals(imageUrl, badge.imageUrl);
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(imageUrl);
            }

            @Override
            public String toString() {
                return "Badge{" +
                        "imageUrl='" + imageUrl + '\'' +
                        '}';
            }
        }

        private String nickname;
        private boolean verifiedMark;
        private Badge[] badges;

        public String getNickname() {
            return nickname;
        }

        public boolean isVerifiedMark() {
            return verifiedMark;
        }

        public Badge[] getBadges() {
            return badges;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Profile profile = (Profile) o;
            return verifiedMark == profile.verifiedMark && Objects.equals(nickname, profile.nickname) && Objects.deepEquals(badges, profile.badges);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nickname, verifiedMark, Arrays.hashCode(badges));
        }

        @Override
        public String toString() {
            return "Profile{" +
                    "nickname='" + nickname + '\'' +
                    ", verifiedMark=" + verifiedMark +
                    ", badges=" + Arrays.toString(badges) +
                    '}';
        }
    }

    private String channelId;
    private String senderChannelId;

    private Profile profile;
    private String content;
    private Map<String, String> emojis;
    private long messageTime;

    public String getReceivedChannelId() {
        return channelId;
    }

    public String getSenderChannelId() {
        return senderChannelId;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getContent() {
        return content;
    }

    @Nullable
    public String getEmojiImgUrl(String emojiId) {
        return emojis.get(emojiId);
    }

    public Map<String, String> getEmojis() {
        return emojis;
    }

    public LocalDateTime getMessageTime() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(messageTime),
                TimeZone.getDefault().toZoneId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionChatMessage that = (SessionChatMessage) o;
        return messageTime == that.messageTime && Objects.equals(channelId, that.channelId) && Objects.equals(senderChannelId, that.senderChannelId) && Objects.equals(profile, that.profile) && Objects.equals(content, that.content) && Objects.equals(emojis, that.emojis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, senderChannelId, profile, content, emojis, messageTime);
    }

    @Override
    public String toString() {
        return "SessionChatMessage{" +
                "channelId='" + channelId + '\'' +
                ", senderChannelId='" + senderChannelId + '\'' +
                ", profile=" + profile +
                ", content='" + content + '\'' +
                ", emojis=" + emojis +
                ", messageTime=" + messageTime +
                '}';
    }
}
