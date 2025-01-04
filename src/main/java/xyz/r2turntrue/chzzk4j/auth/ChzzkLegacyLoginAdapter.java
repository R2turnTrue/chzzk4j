package xyz.r2turntrue.chzzk4j.auth;

import java.util.concurrent.CompletableFuture;

public class ChzzkLegacyLoginAdapter implements ChzzkLoginAdapter {

    public String nidSes;
    public String nidAut;

    public ChzzkLegacyLoginAdapter(String nidAut, String nidSes) {
        this.nidSes = nidSes;
        this.nidAut = nidAut;
    }

    @Override
    public CompletableFuture<ChzzkLoginResult> authorize() {
        return CompletableFuture.supplyAsync(() -> new ChzzkLoginResult(
                nidAut,
                nidSes,
                null,
                null,
                -1
        ));
    }
}
