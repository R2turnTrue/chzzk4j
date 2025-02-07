package xyz.r2turntrue.chzzk4j.auth;

import xyz.r2turntrue.chzzk4j.ChzzkClient;

import java.util.concurrent.CompletableFuture;

public interface ChzzkLoginAdapter {
    CompletableFuture<ChzzkLoginResult> authorize(ChzzkClient client);
}
