package xyz.r2turntrue.chzzk4j.types.channel;

import org.jetbrains.annotations.Nullable;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.exception.NotExistsException;
import xyz.r2turntrue.chzzk4j.types.channel.emoticon.ChzzkChannelEmotePackData;

import java.io.IOException;

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
     * @return {@link ChzzkChannelRules} of the channel
     * @throws IOException if the request to API failed
     * @throws NotExistsException if the channel doesn't exists or the rules of the channel doesn't available
     */
    public ChzzkChannelRules getRules(Chzzk chzzk) throws IOException, NotExistsException {
        return chzzk.getChannelChatRules(channelId);
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
}
