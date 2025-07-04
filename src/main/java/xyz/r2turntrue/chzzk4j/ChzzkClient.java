package xyz.r2turntrue.chzzk4j;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginAdapter;
import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginResult;
import xyz.r2turntrue.chzzk4j.auth.oauth.TokenRefreshRequestBody;
import xyz.r2turntrue.chzzk4j.auth.oauth.TokenResponseBody;
import xyz.r2turntrue.chzzk4j.exception.ChannelNotExistsException;
import xyz.r2turntrue.chzzk4j.exception.NoAccessTokenOnlySupported;
import xyz.r2turntrue.chzzk4j.exception.NotExistsException;
import xyz.r2turntrue.chzzk4j.exception.NotLoggedInException;
import xyz.r2turntrue.chzzk4j.types.ChzzkChatSettings;
import xyz.r2turntrue.chzzk4j.types.ChzzkFollowingStatusResponse;
import xyz.r2turntrue.chzzk4j.types.ChzzkUser;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannel;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkPartialChannel;
import xyz.r2turntrue.chzzk4j.types.channel.emoticon.ChzzkChannelEmotePackData;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelFollowingData;
import xyz.r2turntrue.chzzk4j.types.channel.ChzzkChannelRules;
import xyz.r2turntrue.chzzk4j.types.channel.live.*;
import xyz.r2turntrue.chzzk4j.types.channel.recommendation.ChzzkRecommendationChannels;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ChzzkClient {
    public static String API_URL = "https://api.chzzk.naver.com";
    public static String GAME_API_URL = "https://comm-api.game.naver.com/nng_main";
    public static String OPENAPI_URL = "https://openapi.chzzk.naver.com";

    public boolean isDebug = false;

    private boolean isAnonymous;
    private boolean isLoggedIn;
    private boolean isOauthOnly;
    private boolean isLegacyOnly;

    private String apiClientId;
    private String apiSecret;
    private boolean hasApiKey = false;

    private List<ChzzkLoginAdapter> loginAdapters = new ArrayList<>();
    private ChzzkLoginResult loginResult;
    private OkHttpClient httpClient;
    private Gson gson;

    public String getApiClientId() {
        return apiClientId;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public boolean hasApiKey() {
        return hasApiKey;
    }

    public Gson getGson() {
        return gson;
    }

    private OkHttpClient.Builder buildHttp() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder();

        httpBuilder.addInterceptor(chain -> {
            Request original = chain.request();
            Request authorized = original.newBuilder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0")
                    .build();

            return chain.proceed(authorized);
        });

        if (hasApiKey) {
            httpBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request authorized = original.newBuilder()
                        .addHeader("Client-Id", apiClientId)
                        .addHeader("Client-Secret", apiSecret)
                        .addHeader("Content-Type", "application/json")
                        .build();

                return chain.proceed(authorized);
            });
        }

        return httpBuilder;
    }

    ChzzkClient(ChzzkClientBuilder chzzkBuilder) {
        this.isAnonymous = chzzkBuilder.isAnonymous;
        this.gson = new Gson();

        if (chzzkBuilder.apiClientId != null && chzzkBuilder.apiSecret != null) {
            hasApiKey = true;
            apiClientId = chzzkBuilder.apiClientId;
            apiSecret = chzzkBuilder.apiSecret;
        }

        if (!isAnonymous) {
            loginAdapters = chzzkBuilder.loginAdapters;
        }

        httpClient = buildHttp().build();
    }

    public CompletableFuture<Void> loginAsync() {
        if (!isAnonymous) {
            return CompletableFuture.runAsync(() -> {
                ChzzkLoginResult finalResult = new ChzzkLoginResult(
                        null,
                        null,
                        null,
                        null,
                        -1
                );

                for (ChzzkLoginAdapter adapter : loginAdapters) {
                    ChzzkLoginResult result = adapter.authorize(this).join();
                    if (result.accessToken() != null) {
                        finalResult._setAccessToken(result.accessToken());
                    }

                    if (result.refreshToken() != null) {
                        finalResult._setRefreshToken(result.refreshToken());
                    }

                    if (result.legacy_NID_AUT() != null) {
                        finalResult._setLegacy_NID_AUT(result.legacy_NID_AUT());
                    }

                    if (result.legacy_NID_SES() != null) {
                        finalResult._setLegacy_NID_SES(result.legacy_NID_SES());
                    }

                    if (result.tokenExpiresIn() > 0) {
                        finalResult._setTokenExpiresIn(result.tokenExpiresIn());
                    }
                }

                //loginResult = loginAdapter.authorize(this).join();
                loginResult = finalResult;
                isLoggedIn = true;

                OkHttpClient.Builder httpBuilder = buildHttp();

                if (loginResult.legacy_NID_AUT() != null && loginResult.legacy_NID_SES() != null) {
                    httpBuilder.addInterceptor(chain -> {
                        Request original = chain.request();
                        Request authorized = original.newBuilder()
                                .addHeader("Cookie",
                                        "NID_AUT=" + loginResult.legacy_NID_AUT() + "; " +
                                                "NID_SES=" + loginResult.legacy_NID_SES())
                                .build();

                        return chain.proceed(authorized);
                    });
                }

                if (loginResult.accessToken() != null) {
                    httpBuilder.addInterceptor(chain -> {
                        Request original = chain.request();

                        if (original.header("Not-Token-Api") != null) {
                            return chain.proceed(original.newBuilder()
                                    .removeHeader("Not-Token-Api")
                                    .build());
                        }
                        Request authorized = original.newBuilder()
                                .addHeader("Authorization", "Bearer " + loginResult.accessToken())
                                .build();

                        return chain.proceed(authorized);
                    });
                }

                isOauthOnly = loginResult.accessToken() != null && (loginResult.legacy_NID_AUT() == null || loginResult.legacy_NID_SES() == null);
                isLegacyOnly = loginResult.accessToken() == null;

                httpClient = httpBuilder.build();
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
    public ChzzkChannelFollowingData fetchFollowingStatus(String channelId) throws IOException, NotLoggedInException, ChannelNotExistsException, NoAccessTokenOnlySupported {
        if (isAnonymous) {
            throw new NotLoggedInException("Can't fetch following status without logging in!");
        }

        if (isOauthOnly) {
            throw new NoAccessTokenOnlySupported("You should use legacy login adapter to fetch following status! Sorry :(");
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
    public ChzzkRecommendationChannels fetchRecommendationChannels() throws IOException, NoAccessTokenOnlySupported, NotLoggedInException {
        if (isAnonymous) {
            throw new NotLoggedInException("Can't fetch recommendation channels without logging in!");
        }

        if (isOauthOnly) {
            throw new NoAccessTokenOnlySupported("You should use legacy login adapter to fetch recommendation channels! Sorry :(");
        }

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
     * !! Only less information will be provided if you logged in through only Oauth2!
     *
     * @return {@link ChzzkUser} that current logged in
     * @throws IOException if the request to API failed
     * @throws NotLoggedInException if this {@link ChzzkClient} didn't log in
     */
    public ChzzkUser fetchLoggedUser() throws IOException, NotLoggedInException {
        if (isAnonymous) {
            throw new NotLoggedInException("Can't fetch information of logged user without logging in!");
        }

        if (isOauthOnly) {
            JsonObject contentJson = RawApiUtils.getContentJson(
                    httpClient,
                    RawApiUtils.httpGetRequest(OPENAPI_URL + "/open/v1/users/me").build(),
                    isDebug).getAsJsonObject();

            if (isDebug) System.out.println(gson.toJson(contentJson));

            ChzzkUser user = new ChzzkUser();

            user._setUserId(contentJson.get("channelId").getAsString());
            user._setHasProfile(contentJson.has("nickname") && !contentJson.get("nickname").isJsonNull());
            user._setNickname(contentJson.get("nickname").getAsString());
            user._setLoggedIn(true);

            return user;
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

    public ChzzkPartialChannel[] fetchFollowingChannels() throws IOException, NotLoggedInException, NoAccessTokenOnlySupported {
        if (isAnonymous) {
            throw new NotLoggedInException("Can't fetch following channels without logging in!");
        }

        if (isOauthOnly) {
            throw new NoAccessTokenOnlySupported("You should use legacy login adapter to get following channels! Sorry :(");
        }

        JsonObject contentJson = RawApiUtils.getContentJson(
                httpClient,
                RawApiUtils.httpGetRequest(API_URL + "/service/v1/channels/followings").build(),
                isDebug).getAsJsonObject();

        if (isDebug) System.out.println(gson.toJson(contentJson));

        ChzzkFollowingStatusResponse[] response = gson.fromJson(
                contentJson.get("followingList"),
                ChzzkFollowingStatusResponse[].class);

        return Arrays.stream(response).map((resp) -> resp.channel).toArray(ChzzkPartialChannel[]::new);
    }

    public CompletableFuture<String> sendChatToLoggedInChannel(String content) throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't send chat without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't send chat without logging in with access token!");

        JsonObject req = new JsonObject();
        req.addProperty("message", content);

        return CompletableFuture.supplyAsync(() -> {
            try {
                JsonElement resp = RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpPostRequest(ChzzkClient.OPENAPI_URL + "/open/v1/chats/send",
                        gson.toJson(req)).build(), isDebug);

                return resp.getAsJsonObject().get("messageId").getAsString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> setAnnouncementOfLoggedInChannel(String content) throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't send announcement without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't set announcement without logging in with access token!");

        JsonObject req = new JsonObject();
        req.addProperty("message", content);

        return CompletableFuture.runAsync(() -> {
            try {
                RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpPostRequest(ChzzkClient.OPENAPI_URL + "/open/v1/chats/notice",
                        gson.toJson(req)).build(), isDebug);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> fetchStreamKey() throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't fetch stream key without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't fetch stream key without logging in with access token!");

        return CompletableFuture.supplyAsync(() -> {
            try {
                return RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpGetRequest(ChzzkClient.OPENAPI_URL + "/open/v1/streams/key")
                        .build(), isDebug).getAsJsonObject()
                        .get("streamKey")
                        .getAsString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<ChzzkLiveSettings> fetchLiveSettings() throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't fetch live settings without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't fetch live settings without logging in with access token!");

        return CompletableFuture.supplyAsync(() -> {
            try {
                return gson.fromJson(RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpGetRequest(ChzzkClient.OPENAPI_URL + "/open/v1/lives/setting")
                                .build(), isDebug), ChzzkLiveSettings.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> modifyLiveSettings(ChzzkLiveSettings settings) throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't modify live settings without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't modify live settings without logging in with access token!");

        return CompletableFuture.runAsync(() -> {
            try {
                RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpPatchRequest(ChzzkClient.OPENAPI_URL + "/open/v1/lives/setting",
                                gson.toJson(new ChzzkLiveSettings.ModifyRequest(settings)))
                        .build(), isDebug);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<ChzzkLiveCategory[]> searchCategories(String query) throws NotLoggedInException {
        return this.searchCategories(query, 20); // 문서에 명시된 기본 검색 카운트
    }

    public CompletableFuture<ChzzkLiveCategory[]> searchCategories(String query, int searchCount) throws NotLoggedInException {
        if (!hasApiKey) throw new IllegalStateException("Can't search categories without the OpenAPI key!");

        return CompletableFuture.supplyAsync(() -> {
            try {
                return gson.fromJson(RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpGetRequest(ChzzkClient.OPENAPI_URL + "/open/v1/categories/search?size=" + searchCount + "&query=" + URLEncoder.encode(query, String.valueOf(StandardCharsets.UTF_8)))
                                .addHeader("Not-Token-Api", "1")
                        .build(), isDebug), ChzzkLiveCategory.SearchResponse.class).getData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<ChzzkChatSettings> fetchChatSettings() throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't fetch chat settings without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't fetch chat settings without logging in with access token!");

        return CompletableFuture.supplyAsync(() -> {
            try {
                JsonElement elem = RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpGetRequest(ChzzkClient.OPENAPI_URL + "/open/v1/chats/settings").build(), isDebug);

                return gson.fromJson(elem, ChzzkChatSettings.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<ChzzkChatSettings> modifyChatSettings(ChzzkChatSettings newSettings) throws NotLoggedInException {
        if (!isLoggedIn) throw new NotLoggedInException("Can't modify chat settings without logging in!");
        if (isLegacyOnly) throw new IllegalStateException("Can't modify chat settings without logging in with access token!");

        return CompletableFuture.supplyAsync(() -> {
            try {
                JsonElement elem = RawApiUtils.getContentJson(getHttpClient(),
                        RawApiUtils.httpPutRequest(
                                ChzzkClient.OPENAPI_URL + "/open/v1/chats/settings",
                                gson.toJson(newSettings)).build(), isDebug);

                return gson.fromJson(elem, ChzzkChatSettings.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> refreshTokenAsync() throws NotLoggedInException {
        if (isAnonymous) {
            throw new NotLoggedInException("The client should be logged in to refresh token!");
        }

        if (loginResult.refreshToken() == null) {
            throw new IllegalStateException("The refresh token should not be null to refresh token!");
        }

        return CompletableFuture.runAsync(() -> {
            try {
                JsonElement resp = RawApiUtils.getContentJson(getHttpClient(), RawApiUtils.httpPostRequest(ChzzkClient.OPENAPI_URL + "/auth/v1/token",
                        gson.toJson(new TokenRefreshRequestBody(
                                "refresh_token",
                                loginResult.refreshToken(),
                                apiClientId,
                                apiSecret
                        ))).build(), isDebug);

                TokenResponseBody respBody = gson.fromJson(resp, TokenResponseBody.class);

                if (isDebug) {
                    System.out.println("-- Token Refresh!");
                    System.out.println("AccToken: " + respBody.getAccessToken());
                    System.out.println("RefToken: " + respBody.getRefreshToken());
                    System.out.println("ExpiresIn: " + respBody.getExpiresIn());
                }

                String accToken = respBody.getAccessToken();
                String refToken = respBody.getRefreshToken();
                int expiresIn = respBody.getExpiresIn();

                if (accToken == null || refToken == null) {
                    throw new Exception("access token or refresh token is null");
                }

                this.loginResult._setAccessToken(accToken);
                this.loginResult._setRefreshToken(refToken);
                this.loginResult._setTokenExpiresIn(expiresIn);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    public ChzzkLoginResult getLoginResult() {
        return loginResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkClient that = (ChzzkClient) o;
        return isDebug == that.isDebug && isAnonymous == that.isAnonymous && isLoggedIn == that.isLoggedIn && loginAdapters.equals(that.loginAdapters) && Objects.equals(loginResult, that.loginResult) && Objects.equals(httpClient, that.httpClient) && Objects.equals(gson, that.gson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDebug, isAnonymous, isLoggedIn, loginAdapters, loginResult, httpClient, gson);
    }
}
