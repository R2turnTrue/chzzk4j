package xyz.r2turntrue.chzzk4j.auth;

import org.jetbrains.annotations.Nullable;
import xyz.r2turntrue.chzzk4j.ChzzkClient;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ChzzkSimpleUserLoginAdapter implements ChzzkLoginAdapter {

    public String accessToken;
    public String refreshToken;

    public ChzzkSimpleUserLoginAdapter(String accessToken, @Nullable String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public CompletableFuture<ChzzkLoginResult> authorize(ChzzkClient client) {
        return CompletableFuture.supplyAsync(() -> new ChzzkLoginResult(
                null,
                null,
                accessToken,
                refreshToken,
                -1
        ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkSimpleUserLoginAdapter that = (ChzzkSimpleUserLoginAdapter) o;
        return Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, refreshToken);
    }
}
