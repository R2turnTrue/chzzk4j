package xyz.r2turntrue.chzzk4j.auth;

import xyz.r2turntrue.chzzk4j.ChzzkClient;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ChzzkLegacyLoginAdapter implements ChzzkLoginAdapter {

    public String nidSes;
    public String nidAut;

    public ChzzkLegacyLoginAdapter(String nidAut, String nidSes) {
        this.nidSes = nidSes;
        this.nidAut = nidAut;
    }

    @Override
    public CompletableFuture<ChzzkLoginResult> authorize(ChzzkClient client) {
        return CompletableFuture.supplyAsync(() -> new ChzzkLoginResult(
                nidAut,
                nidSes,
                null,
                null,
                -1
        ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkLegacyLoginAdapter that = (ChzzkLegacyLoginAdapter) o;
        return Objects.equals(nidSes, that.nidSes) && Objects.equals(nidAut, that.nidAut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nidSes, nidAut);
    }
}
