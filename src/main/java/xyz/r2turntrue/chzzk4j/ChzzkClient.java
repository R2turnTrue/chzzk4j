package xyz.r2turntrue.chzzk4j;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginAdapter;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginResult;
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
import xyz.r2turntrue.chzzk4j.types.channel.live.*;
import xyz.r2turntrue.chzzk4j.types.channel.recommendation.ChzzkRecommendationChannels;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ChzzkClient {
    public static String API_URL = "https://api.chzzk.naver.com";
    public static String GAME_API_URL = "https://comm-api.game.naver.com/nng_main";

    public boolean isDebug = false;

    private boolean isAnonymous;
    private boolean isLoggedIn;

    private ChzzkLoginAdapter loginAdapter;
    private ChzzkLoginResult loginResult;
    private OkHttpClient httpClient;
    private Gson gson;

    private OkHttpClient.Builder buildHttp() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();

        httpBuilder.addInterceptor(chain -> {
            Request original = chain.request();
            Request authorized = original.newBuilder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0")
                    .build();

            return chain.proceed(authorized);
        });

        return httpBuilder;
    }

    ChzzkClient(ChzzkClientBuilder chzzkBuilder) {
        this.isAnonymous = chzzkBuilder.isAnonymous;
        this.gson = new Gson();

        if (!isAnonymous) {
            loginAdapter = chzzkBuilder.loginAdapter;
        }

        httpClient = buildHttp().build();
    }

    public CompletableFuture<Void> loginAsync() {
        if (!isAnonymous) {
            return CompletableFuture.runAsync(() -> {
                loginResult = loginAdapter.authorize().join();
                isLoggedIn = true;

                if (loginResult.legacy_NID_AUT() != null && loginResult.legacy_NID_SES() != null) {
                    httpClient = buildHttp().addInterceptor(chain -> {
                        Request original = chain.request();
                        Request authorized = original.newBuilder()
                                .addHeader("Cookie",
                                        "NID_AUT=" + loginResult.legacy_NID_AUT() + "; " +
                                                "NID_SES=" + loginResult.legacy_NID_SES())
                                .build();

                        return chain.proceed(authorized);
                    }).build();
                }
            });
        } else {
            throw new InvalidParameterException("The chzzk client doesn't have any login adapter!");
        }
    }

    /**
     * Get this {@link ChzzkClient} logged in.
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
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
    public ChzzkChannel fetchChannel(String channelId) throws IOException, ChannelNotExistsException {
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
     * Get {@link ChzzkLiveStatus} by the channel id.
     * @param channelId ID of {@link ChzzkChannel}
     * @return {@link ChzzkLiveStatus} of the channel
     * @throws IOException if the request to API failed
     */
    public @NotNull ChzzkLiveStatus fetchLiveStatus(@NotNull String channelId) throws IOException {
        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/polling/v2/channels/" + channelId + "/live-status").build(),
                isDebug);

        return gson.fromJson(contentJson, ChzzkLiveStatus.class);
    }

    /**
     * Get {@link ChzzkLiveDetail} by the channel id.
     * @param channelId ID of {@link ChzzkChannel}
     * @return {@link ChzzkLiveDetail} of the channel
     * @throws IOException if the request to API failed
     */
    public @NotNull ChzzkLiveDetail fetchLiveDetail(@NotNull String channelId) throws IOException {
        JsonElement contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v2/channels/" + channelId + "/live-detail").build(),
                isDebug);

        return gson.fromJson(contentJson, ChzzkLiveDetail.class);
    }

    /**
     * Get channel's {@link ChzzkChannelRules} by the channel id.
     *
     * @param channelId ID of {@link ChzzkChannel}
     * @return {@link ChzzkChannelRules} of the channel
     * @throws IOException        if the request to API failed
     * @throws NotExistsException if the channel doesn't exists or the rules of the channel doesn't available
     */
    public ChzzkChannelRules fetchChannelChatRules(String channelId) throws IOException, NotExistsException {
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

    public ChzzkChannelEmotePackData fetchChannelEmotePackData(String channelId) throws IOException {
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
     * @throws NotLoggedInException if this {@link ChzzkClient} didn't log in
     * @throws ChannelNotExistsException if the channel doesn't exists
     */
    public ChzzkChannelFollowingData fetchFollowingStatus(String channelId) throws IOException, NotLoggedInException, ChannelNotExistsException {
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
    public ChzzkRecommendationChannels fetchRecommendationChannels() throws IOException {
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
     * Get {@link ChzzkUser} that the {@link ChzzkClient} logged in.
     *
     * @return {@link ChzzkUser} that current logged in
     * @throws IOException if the request to API failed
     * @throws NotLoggedInException if this {@link ChzzkClient} didn't log in
     */
    public ChzzkUser fetchLoggedUser() throws IOException, NotLoggedInException {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkClient that = (ChzzkClient) o;
        return isDebug == that.isDebug && isAnonymous == that.isAnonymous && isLoggedIn == that.isLoggedIn && Objects.equals(loginAdapter, that.loginAdapter) && Objects.equals(loginResult, that.loginResult) && Objects.equals(httpClient, that.httpClient) && Objects.equals(gson, that.gson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDebug, isAnonymous, isLoggedIn, loginAdapter, loginResult, httpClient, gson);
    }
}
