package xyz.r2turntrue.chzzk4j.types.channel.emoticon;

public class ChzzkChannelEmoticonData {
    private String emojiId;
    private String imageUrl;

    private ChzzkChannelEmoticonData() {}

    /**
     * Get the emoticon's id.
     */
    public String getEmoticonId() {
        return emojiId;
    }

    /**
     * Get url of the emoticon's image.
     */
    public String getEmoticonImageUrl() {
        return imageUrl;
    }
}
