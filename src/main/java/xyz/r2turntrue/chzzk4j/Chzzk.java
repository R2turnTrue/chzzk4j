package xyz.r2turntrue.chzzk4j;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChatBuilder;
import xyz.r2turntrue.chzzk4j.exception.ChannelNotExistsException;
import xyz.r2turntrue.chzzk4j.exception.NotExistsException;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkFollowingStatusResponse;
import xyz.r2turntrue.chzzk4j.types.ChzzkUser;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;
import xyz.r2turntrue.chzzk4j.types.channel.emoticon.ChzzkChannelEmotePackData;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelFollowingData;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelRules;
import xyz.r2turntrue.chzzk4j.types.channel.recommendation.ChzzkRecommendationChannels;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.util.List;

public class Chzzk {
    public static String API_URL = "https://api.chzzk.naver.com";
    public static String GAME_API_URL = "https://comm-api.game.naver.com/nng_main";

    public boolean isDebug = false;

    private String nidAuth;
    private String nidSession;
    private boolean isAnonymous;

    private OkHttpClient httpClient;
    private Gson gson;

    Chzzk(ChzzkBuilder chzzkBuilder) {
        this.nidAuth = chzzkBuilder.nidAuth;
        this.nidSession = chzzkBuilder.nidSession;
        this.isAnonymous = chzzkBuilder.isAnonymous;
        this.gson = new Gson();

        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();

        if (!chzzkBuilder.isAnonymous) {
            httpBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request authorized = original.newBuilder()
                        .addHeader("Cookie",
                                "NID_AUT=" + chzzkBuilder.nidAuth + "; " +
                                "NID_SES=" + chzzkBuilder.nidSession)
                        .build();

                return chain.proceed(authorized);
            });
        }

        httpBuilder.addInterceptor(chain -> {
            Request original = chain.request();
            Request authorized = original.newBuilder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0")
                    .build();

            return chain.proceed(authorized);
        });

        httpClient = httpBuilder.build();
    }

    /**
     * Get this {@link Chzzk} logged in.
     */
    public boolean isLoggedIn() {
        return !isAnonymous;
    }

    /**
     * Get new an instance of {@link ChzzkChat} with this {@link Chzzk}.
     */
    public ChzzkChatBuilder chat(String channelId) {
        return new ChzzkChatBuilder(this, channelId);
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Get {@link ChzzkChannel} by the channel id.
     *
     * @param channelId ID of {@link ChzzkChannel} that to get.
     * @return {@link ChzzkChannel} to get
     * @throws IOException if the request to API failed
     * @throws ChannelNotExistsException if the channel doesn't exists
     */
    public ChzzkChannel getChannel(String channelId) throws IOException, ChannelNotExistsException {
        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v1/channels/" + channelId).build(),
                isDebug);

        ChzzkChannel channel = gson.fromJson(
                contentJson,
                ChzzkChannel.class);
        if (channel.getChannelId() == null) {
            throw new ChannelNotExistsException("The channel does not exists!");
        }

        return channel;
    }

    /**
     * Get channel's {@link ChzzkChannelRules} by the channel id.
     *
     * @param channelId ID of {@link ChzzkChannel}
     * @return {@link ChzzkChannelRules} of the channel
     * @throws IOException        if the request to API failed
     * @throws NotExistsException if the channel doesn't exists or the rules of the channel doesn't available
     */
    public ChzzkChannelRules getChannelChatRules(String channelId) throws IOException, NotExistsException {
        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v1/channels/" + channelId + "/chat-rules").build(),
                isDebug);

        ChzzkChannelRules rules = gson.fromJson(
                contentJson,
                ChzzkChannelRules.class);

        if (rules.getUpdatedDate() == null) {
            throw new NotExistsException("The channel or rules of the channel does not exists!");
        }

        return rules;
    }

    public ChzzkChannelEmotePackData getChannelEmotePackData(String channelId) throws IOException {
        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v1/channels/" + channelId + "/emoji-packs").build(),
                isDebug);
        ChzzkChannelEmotePackData emoticons = null;
        List<JsonElement> emoteElements =  contentJson.getAsJsonObject().asMap().get("subscriptionEmojiPacks").getAsJsonArray().asList();
        for (JsonElement emoteElement : emoteElements) {
            if (emoteElement.getAsJsonObject().asMap().get("emojiPackId").getAsString().equals("\""+channelId+ "\"")) {
                continue;
            }
            emoticons= gson.fromJson(
                    emoteElement,
                    ChzzkChannelEmotePackData.class);
        }
        return emoticons;
    }

    /**
     * Get following status about channel.
     *
     * @param channelId ID of {@link ChzzkChannel} to get following status
     * @return user's {@link ChzzkChannelFollowingData} of the channel
     * @throws IOException if the request to API failed
     * @throws NotLoggedInException if this {@link Chzzk} didn't log in
     * @throws ChannelNotExistsException if the channel doesn't exists
     */
    public ChzzkChannelFollowingData getFollowingStatus(String channelId) throws IOException, NotLoggedInException, ChannelNotExistsException {
        if (isAnonymous) {
            throw new NotLoggedInException("Can't get following status without logging in!");
        }

        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v1/channels/" + channelId + "/follow").build(),
                isDebug);

        ChzzkFollowingStatusResponse followingDataResponse = gson.fromJson(
                contentJson,
                ChzzkFollowingStatusResponse.class);

        if (followingDataResponse.channel.getChannelId() == null) {
            throw new NotExistsException("The channel does not exists!");
        }

        return followingDataResponse.channel.getPersonalData().getFollowing();
    }

    /**
     * Get {@link ChzzkRecommendationChannels}
     *
     * @return recommendation channels - {@link ChzzkRecommendationChannels}
     * @throws IOException if the request to API failed
     */
    public ChzzkRecommendationChannels getRecommendationChannels() throws IOException {
        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v1/home/recommendation-channels").build(),
                isDebug);

        ChzzkRecommendationChannels channels = gson.fromJson(
                contentJson,
                ChzzkRecommendationChannels.class);

        return channels;
    }

    /**
     * Get {@link ChzzkUser} that the {@link Chzzk} logged in.
     *
     * @return {@link ChzzkUser} that current logged in
     * @throws IOException if the request to API failed
     * @throws NotLoggedInException if this {@link Chzzk} didn't log in
     */
    public ChzzkUser getLoggedUser() throws IOException, NotLoggedInException {
        if (isAnonymous) {
            throw new NotLoggedInException("Can't get information of logged user without logging in!");
        }

        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(GAME_API_URL + "/v1/user/getUserStatus").build(),
                isDebug);

        ChzzkUser user = gson.fromJson(
                contentJson,
                ChzzkUser.class);

        return user;
    }
}
