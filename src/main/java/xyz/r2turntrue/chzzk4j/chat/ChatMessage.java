package xyz.r2turntrue.chzzk4j.chat;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class ChatMessage {
    public enum OsType
    {
        PC,
        AOS,
        IOS
    }

    public static class Extras {
        // todo: emoji parsing implementation
        //public Emoji[] emojis;

        String donationType;
        String osType;

        int payAmount = -1;

        // Subscription
        int month = 0;
        String tierName = "";

        // Mission
        int durationTime;
        String missionDonationId;
        String missionCreatedTime;
        String missionEndTime;
        String missionText;
        String status;
        boolean success;

        public OsType getOsType() {
            return OsType.valueOf(osType);
        }

        public int getPayAmount() {
            return payAmount;
        }

        //public int getSubscriptionMonth() {
        //    return month;
        //}

        //public String getSubscriptionTierName() {
        //    return tierName;
        //}

        @Override
        public String toString() {
            return "Extras{" +
                    "osType='" + osType + '\'' +
                    ", payAmount=" + payAmount + '\'' +
                    ", month=" + month + '\'' +
                    ", tierName='" + tierName +
                    '}';
        }
    }

    public static class Profile {
        String nickname;
        String profileImageUrl;
        String userRoleCode;
        boolean verifiedMark;

        ActivityBadge[] activityBadges;
        StreamingProperty streamingProperty;

        public static class StreamingProperty {
            Subscription subscription;

            public static class Subscription {
                int accmulativeMonth;
                int tier;
                PartialBadge badge;

                public int getAccmulativeMonth() {
                    return accmulativeMonth;
                }

                public int getTier() {
                    return tier;
                }

                public PartialBadge getBadge() {
                    return badge;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (o == null || getClass() != o.getClass()) return false;
                    Subscription that = (Subscription) o;
                    return accmulativeMonth == that.accmulativeMonth && tier == that.tier && Objects.equals(badge, that.badge);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(accmulativeMonth, tier, badge);
                }

                @Override
                public String toString() {
                    return "Subscription{" +
                            "accmulativeMonth=" + accmulativeMonth +
                            ", tier=" + tier +
                            ", badge=" + badge +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "StreamingProperty{" +
                        "subscription=" + subscription +
                        '}';
            }
        }

        public static class PartialBadge {
            String imageUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PartialBadge that = (PartialBadge) o;
                return Objects.equals(imageUrl, that.imageUrl);
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(imageUrl);
            }

            @Override
            public String toString() {
                return "PartialBadge{" +
                        "imageUrl='" + imageUrl + '\'' +
                        '}';
            }
        }

        public static class ActivityBadge extends PartialBadge {
            int badgeNo;
            String badgeId;
            boolean activated;

            public int getBadgeNo() {
                return badgeNo;
            }

            public String getBadgeId() {
                return badgeId;
            }

            public boolean isActivated() {
                return activated;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ActivityBadge that = (ActivityBadge) o;
                return badgeNo == that.badgeNo && activated == that.activated && Objects.equals(badgeId, that.badgeId) && Objects.equals(imageUrl, that.imageUrl);
            }

            @Override
            public int hashCode() {
                return Objects.hash(badgeNo, badgeId, imageUrl, activated);
            }

            @Override
            public String toString() {
                return "ActivityBadge{" +
                        "badgeNo=" + badgeNo +
                        ", badgeId='" + badgeId + '\'' +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", activated=" + activated +
                        '}';
            }
        }

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

        public ActivityBadge[] getActivityBadges() {
            return activityBadges;
        }

        @Nullable
        public StreamingProperty.Subscription getSubscription() {
            return streamingProperty.subscription;
        }

        public boolean hasSubscription() {
            return streamingProperty.subscription != null;
        }

        @Override
        public String toString() {
            return "Profile{" +
                    "nickname='" + nickname + '\'' +
                    ", profileImageUrl='" + profileImageUrl + '\'' +
                    ", userRoleCode='" + userRoleCode + '\'' +
                    ", verifiedMark=" + verifiedMark +
                    ", activityBadges=" + Arrays.toString(activityBadges) +
                    ", streamingProperty=" + streamingProperty +
                    '}';
        }
    }

    int msgTypeCode = 0;
    String userId;
    String content;
    Date createTime;
    Extras extras = new Extras();
    Profile profile = new Profile();

    String rawJson;

    public String getRawJson() { return rawJson; }

    public int getChatTypeCode() {
        return msgTypeCode;
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

    public boolean hasProfile() {
        return profile != null;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "userId='" + userId + '\'' +
                ", msgTypeCode='" + msgTypeCode + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", extras=" + extras +
                ", profile=" + profile +
                '}';
    }
}
