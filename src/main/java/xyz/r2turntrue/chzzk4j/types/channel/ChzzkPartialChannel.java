package xyz.r2turntrue.chzzk4j.types.channel;

import org.jetbrains.annotations.Nullable;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.exception.NotExistsException;
import xyz.r2turntrue.chzzk4j.types.channel.emoticon.ChzzkChannelEmotePackData;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ChzzkPartialChannel {
    private String channelId;
    private String channelName;
    private String channelImageUrl;
    private boolean verifiedMark;
    private ChzzkChannelPersonalData personalData;
    private ChzzkChannelEmotePackData emotePackData;

    ChzzkPartialChannel() {}

    /**
     * Get this channel's {@link ChzzkChannelRules}.
     *
     * @return {@link CompletableFuture<ChzzkChannelRules>} of the channel
     * @throws IOException if the request to API failed
     * @throws NotExistsException if the channel doesn't exists or the rules of the channel doesn't available
     */
    public CompletableFuture<ChzzkChannelRules> getRules(ChzzkClient chzzk) throws IOException, NotExistsException {
        return chzzk.fetchChannelChatRules(channelId);
    }

    /**
     * Get the channel's id.
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Get the name of the channel.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Get url of the channel's image.
     */
    @Nullable
    public String getChannelImageUrl() {
        return channelImageUrl;
    }

    /**
     * Get is the channel verified.
     */
    public boolean isVerifiedMark() {
        return verifiedMark;
    }

    /**
     * Get personal data of logged user about the channel.
     * If not logged in, returns null.
     */
    @Nullable
    public ChzzkChannelPersonalData getPersonalData() {
        return personalData;
    }

    /**
     * Get the emoticon pack data of the channel.
     */
    @Nullable
    public ChzzkChannelEmotePackData getEmotePackData() {
        return emotePackData;
    }
    @Override
    public String toString() {
        return "ChzzkPartialChannel{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelImageUrl='" + channelImageUrl + '\'' +
                ", verifiedMark=" + verifiedMark +
                ", personalData=" + personalData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkPartialChannel that = (ChzzkPartialChannel) o;
        return verifiedMark == that.verifiedMark && Objects.equals(channelId, that.channelId) && Objects.equals(channelName, that.channelName) && Objects.equals(channelImageUrl, that.channelImageUrl) && Objects.equals(personalData, that.personalData) && Objects.equals(emotePackData, that.emotePackData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, channelName, channelImageUrl, verifiedMark, personalData, emotePackData);
    }
}
