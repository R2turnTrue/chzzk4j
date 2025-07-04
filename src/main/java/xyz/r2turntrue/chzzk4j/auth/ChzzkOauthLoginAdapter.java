package xyz.r2turntrue.chzzk4j.auth;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpServer;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.auth.oauth.TokenRequestBody;
import xyz.r2turntrue.chzzk4j.auth.oauth.TokenResponseBody;
import xyz.r2turntrue.chzzk4j.util.HttpUtils;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class ChzzkOauthLoginAdapter implements ChzzkLoginAdapter {

    private String redirectHost = "localhost";
    private int port = 8080;
    private String succeedPage = "Authorized! Back to the app!";
    private String failedPage = "Authorization Failed! Re-try it!";

    private String state;
    private String code;

    private int expiresIn;

    private String accToken;
    private String refToken;

    public String getOauthState() {
        return state;
    }

    public ChzzkOauthLoginAdapter() {
    }

    public ChzzkOauthLoginAdapter(int port) {
        this.port = port;
    }

    public ChzzkOauthLoginAdapter(String redirectHost) {
        this.redirectHost = redirectHost;
    }

    public ChzzkOauthLoginAdapter(String redirectHost, int port) {
        this.redirectHost = redirectHost;
        this.port = port;
    }

    public ChzzkOauthLoginAdapter(String redirectHost, int port, String succeedPage, String failedPage) {
        this.redirectHost = redirectHost;
        this.port = port;
        this.succeedPage = succeedPage;
        this.failedPage = failedPage;
    }

    @Override
    public CompletableFuture<ChzzkLoginResult> authorize(ChzzkClient client) {
        return CompletableFuture.supplyAsync(() -> {
            InetSocketAddress address = new InetSocketAddress(port);
            HttpServer httpServer = null;
            try {
                httpServer = HttpServer.create(address, 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            CountDownLatch latch = new CountDownLatch(1);

            HttpServer finalHttpServer = httpServer;
            httpServer.createContext("/oauth_callback", (exchange) -> {

                try {
                    Map<String, String> params = HttpUtils.queryToMap(exchange.getRequestURI().getQuery());

                    if (params.containsKey("code") && params.containsKey("state")) {
                        state = params.get("state");
                        code = params.get("code");

                        if (client.isDebug) System.out.println("Received the code");

                        Gson gson = new Gson();

                        JsonElement resp = RawApiUtils.getContentJson(client.getHttpClient(), RawApiUtils.httpPostRequest(ChzzkClient.OPENAPI_URL + "/auth/v1/token",
                                gson.toJson(new TokenRequestBody(
                                        "authorization_code",
                                        client.getApiClientId(),
                                        client.getApiSecret(),
                                        code,
                                        state
                                ))).build(), client.isDebug);

                        TokenResponseBody respBody = gson.fromJson(resp, TokenResponseBody.class);

                        if (client.isDebug) {
                            System.out.println("AccToken: " + respBody.getAccessToken());
                            System.out.println("RefToken: " + respBody.getRefreshToken());
                            System.out.println("ExpiresIn: " + respBody.getExpiresIn());
                        }

                        accToken = respBody.getAccessToken();
                        refToken = respBody.getRefreshToken();
                        expiresIn = respBody.getExpiresIn();

                        if (accToken == null || refToken == null) {
                            throw new Exception("access token or refresh token is null");
                        }

                        HttpUtils.sendContent(exchange, succeedPage, 200);

                        finalHttpServer.stop(1);
                        latch.countDown();
                    } else {
                        HttpUtils.sendContent(exchange, failedPage, 403);
                    }
                } catch (Exception e) {
                    if (client.isDebug) e.printStackTrace();
                    HttpUtils.sendContent(exchange, e.getMessage(), 403);
                }
            });

            httpServer.start();

            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return new ChzzkLoginResult(
                    null,
                    null,
                    accToken,
                    refToken,
                    expiresIn
            );
        });
    }

    public String getAccountInterlockUrl(String clientId, boolean redirectToHttps) throws UnsupportedEncodingException {
        return getAccountInterlockUrl(clientId, redirectToHttps, "dummy");
    }

    public String getAccountInterlockUrl(String clientId, boolean redirectToHttps, String state) throws UnsupportedEncodingException {
        return "https://chzzk.naver.com/account-interlock" +
                "?clientId=" +
                clientId +
                "&redirectUri=" +
                URLEncoder.encode(
                        (redirectToHttps ? "https://" : "http://") +
                                redirectHost +
                                ":" +
                                port +
                                "/oauth_callback",
                        String.valueOf(StandardCharsets.UTF_8)
                ) +
                "&state=" +
                URLEncoder.encode(
                        state,
                        String.valueOf(StandardCharsets.UTF_8)
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkOauthLoginAdapter that = (ChzzkOauthLoginAdapter) o;
        return port == that.port && Objects.equals(redirectHost, that.redirectHost) && Objects.equals(succeedPage, that.succeedPage) && Objects.equals(failedPage, that.failedPage) && Objects.equals(state, that.state) && Objects.equals(code, that.code) && Objects.equals(accToken, that.accToken) && Objects.equals(refToken, that.refToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redirectHost, port, succeedPage, failedPage, state, code, accToken, refToken);
    }
}
