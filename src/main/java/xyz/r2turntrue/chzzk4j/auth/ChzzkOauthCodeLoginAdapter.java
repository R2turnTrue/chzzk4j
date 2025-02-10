package xyz.r2turntrue.chzzk4j.auth;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;
import xyz.r2turntrue.chzzk4j.ChzzkClient;
import xyz.r2turntrue.chzzk4j.auth.oauth.TokenRequestBody;
import xyz.r2turntrue.chzzk4j.auth.oauth.TokenResponseBody;
import xyz.r2turntrue.chzzk4j.util.RawApiUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ChzzkOauthCodeLoginAdapter implements ChzzkLoginAdapter {

    public String code;
    public String state;

    public ChzzkOauthCodeLoginAdapter(String code, String state) {
        this.code = code;
        this.state = state; // 어따 쓰는건진 모르겠는데 /auth/v1/token에서 받네요?
    }

    @Override
    public CompletableFuture<ChzzkLoginResult> authorize(ChzzkClient client) {
        return CompletableFuture.supplyAsync(() -> {
            var gson = new Gson();

            JsonElement resp = null;
            try {
                resp = RawApiUtils.getContentJson(client.getHttpClient(), RawApiUtils.httpPostRequest(ChzzkClient.OPENAPI_URL + "/auth/v1/token",
                        gson.toJson(new TokenRequestBody(
                                "authorization_code",
                                client.getApiClientId(),
                                client.getApiSecret(),
                                code,
                                state
                        ))).build(), client.isDebug);

                var respBody = gson.fromJson(resp, TokenResponseBody.class);

                if (client.isDebug) {
                    System.out.println("AccToken: " + respBody.accessToken());
                    System.out.println("RefToken: " + respBody.refreshToken());
                    System.out.println("ExpiresIn: " + respBody.expiresIn());
                }

                return new ChzzkLoginResult(
                        null,
                        null,
                        respBody.accessToken(),
                        respBody.refreshToken(),
                        respBody.expiresIn()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkOauthCodeLoginAdapter that = (ChzzkOauthCodeLoginAdapter) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}
