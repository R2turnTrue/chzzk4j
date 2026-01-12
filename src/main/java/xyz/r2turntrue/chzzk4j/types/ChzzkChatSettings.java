package xyz.r2turntrue.chzzk4j.types;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChzzkChatSettings {
    public enum ChatAvailableCondition {
        NONE,
        REAL_NAME
    }

    public enum ChatAvailableGroup {
        ALL,
        FOLLOWER,
        MANAGER,
        SUBSCRIBER
    }

    public enum MinFollowerMinute {
        M_0(0),
        M_5(5),
        M_10(10),
        M_30(30),
        M_60(60),
        M_1440(1440),
        M_10080(10080),
        M_43200(43200),
        M_86400(86400),
        M_129600(129600),
        M_172800(172800),
        M_216000(216000),
        M_259200(259200);

        private final int minutes;
        private static final String ALLOWED_MINUTES_STRING = Arrays.stream(values()).mapToInt(MinFollowerMinute::getMinutes).sorted().mapToObj(String::valueOf).collect(Collectors.joining(", "));

        MinFollowerMinute(int minutes) {
            this.minutes = minutes;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getDays() {
            return minutes / 1440;
        }

        public static MinFollowerMinute fromMinutes(int minutes) {
            for (MinFollowerMinute value : values()) {
                if (value.minutes == minutes) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Invalid minFollowerMinute value: " + minutes + ". Allowed values: " + ALLOWED_MINUTES_STRING);
        }
    }

    public enum ChatSlowModeSec {
        S_0,
        S_3,
        S_5,
        S_10,
        S_30,
        S_60,
        S_120,
        S_300
    }

    private String chatAvailableCondition;
    private String chatAvailableGroup;
    private int minFollowerMinute;
    private boolean allowSubscriberInFollowerMode;
    private int chatSlowModeSec;
    private boolean chatEmojiMode;

    public ChatAvailableCondition getChatAvailableCondition() {
        return ChatAvailableCondition.valueOf(chatAvailableCondition);
    }

    public ChatAvailableGroup getChatAvailableGroup() {
        return ChatAvailableGroup.valueOf(chatAvailableGroup);
    }

    public MinFollowerMinute getMinFollowerMinute() {
        return MinFollowerMinute.fromMinutes(minFollowerMinute);
    }

    public boolean isAllowSubscriberInFollowerMode() {
        return allowSubscriberInFollowerMode;
    }

    public int getChatSlowModeSec() {
        return chatSlowModeSec;
    }

    public boolean isChatEmojiMode() {
        return chatEmojiMode;
    }

    public void setChatAvailableCondition(ChatAvailableCondition chatAvailableCondition) {
        this.chatAvailableCondition = chatAvailableCondition.toString();
    }

    public void setChatAvailableGroup(ChatAvailableGroup chatAvailableGroup) {
        this.chatAvailableGroup = chatAvailableGroup.toString();
    }

    public void setMinFollowerMinute(MinFollowerMinute minFollowerMinute) {
        this.minFollowerMinute = minFollowerMinute.getMinutes();
    }

    public void setAllowSubscriberInFollowerMode(boolean allowSubscriberInFollowerMode) {
        this.allowSubscriberInFollowerMode = allowSubscriberInFollowerMode;
    }

    public void setChatEmojiMode(boolean chatEmojiMode) {
        this.chatEmojiMode = chatEmojiMode;
    }

    public void setChatSlowModeSec(ChatSlowModeSec sec) {
        this.chatSlowModeSec = Integer.parseInt(sec.toString().replace("S_", ""));
    }

    @Override
    public String toString() {
        return "ChzzkChatSettings{" +
                "chatAvailableCondition='" + chatAvailableCondition + '\'' +
                ", chatAvailableGroup='" + chatAvailableGroup + '\'' +
                ", minFollowerMinute=" + minFollowerMinute +
                ", allowSubscriberInFollowerMode=" + allowSubscriberInFollowerMode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChatSettings that = (ChzzkChatSettings) o;
        return minFollowerMinute == that.minFollowerMinute && allowSubscriberInFollowerMode == that.allowSubscriberInFollowerMode && Objects.equals(chatAvailableCondition, that.chatAvailableCondition) && Objects.equals(chatAvailableGroup, that.chatAvailableGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatAvailableCondition, chatAvailableGroup, minFollowerMinute, allowSubscriberInFollowerMode);
    }
}
