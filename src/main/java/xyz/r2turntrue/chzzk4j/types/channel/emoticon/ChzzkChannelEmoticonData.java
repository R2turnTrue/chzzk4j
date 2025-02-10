package xyz.r2turntrue.chzzk4j.types.channel.emoticon;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "ChzzkChannelEmoticonData{" +
                "emojiId='" + emojiId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkChannelEmoticonData that = (ChzzkChannelEmoticonData) o;
        return Objects.equals(emojiId, that.emojiId) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emojiId, imageUrl);
    }
}
