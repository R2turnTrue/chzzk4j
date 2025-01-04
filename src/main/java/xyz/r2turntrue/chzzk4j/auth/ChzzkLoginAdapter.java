package xyz.r2turntrue.chzzk4j.auth;

import java.util.concurrent.CompletableFuture;

public interface ChzzkLoginAdapter {
    CompletableFuture<ChzzkLoginResult> authorize();
}
