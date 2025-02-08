package xyz.r2turntrue.chzzk4j.types.channel.emoticon;

import java.util.List;
import java.util.Objects;

public class ChzzkChannelEmotePackData {
    private String emojiPackId;
    private String emojiPackName;
    private String emojiPackImageUrl;
    private boolean emojiPackLocked;
    private List<ChzzkChannelEmoticonData> emojis;

    private ChzzkChannelEmotePackData() {}

    /**
     * Get the pack's id.
     */
    public String getPackId() {
        return emojiPackId;
    }

    /**
     * Get the name of the pack.
     */
    public String getPackName() {
        return emojiPackName;
    }

    /**
     * Get url of the pack's image.
     */
    public String getPackImageUrl() {
        return emojiPackImageUrl;
    }

    /**
     * Get the emoticons data of the pack.
     */

    public List<ChzzkChannelEmoticonData> getEmojis() {
        return emojis;
    }

    @Override
    public String toString() {
        return "ChzzkChannelEmotePackData{" +
                "emojiPackId='" + emojiPackId + '\'' +
                ", emojiPackName='" + emojiPackName + '\'' +
                ", emojiPackImageUrl='" + emojiPackImageUrl + '\'' +
                ", emojiPackLocked=" + emojiPackLocked +
                ", emojis=" + emojis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChannelEmotePackData that = (ChzzkChannelEmotePackData) o;
        return emojiPackLocked == that.emojiPackLocked && Objects.equals(emojiPackId, that.emojiPackId) && Objects.equals(emojiPackName, that.emojiPackName) && Objects.equals(emojiPackImageUrl, that.emojiPackImageUrl) && Objects.equals(emojis, that.emojis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emojiPackId, emojiPackName, emojiPackImageUrl, emojiPackLocked, emojis);
    }
}
