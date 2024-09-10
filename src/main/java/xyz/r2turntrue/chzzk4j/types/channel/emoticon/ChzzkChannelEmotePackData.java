package xyz.r2turntrue.chzzk4j.types.channel.emoticon;

import java.util.List;

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
}
