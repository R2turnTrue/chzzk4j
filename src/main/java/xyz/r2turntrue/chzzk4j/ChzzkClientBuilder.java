package xyz.r2turntrue.chzzk4j;

import xyz.r2turntrue.chzzk4j.auth.ChzzkLoginAdapter;

/**
 * Class for creating instances of {@link ChzzkClient}.
 */
public class ChzzkClientBuilder {
    boolean isAnonymous = false;
    boolean isDebug = false;
    String nidAuth;
    String nidSession;

    String apiClientId = null;
    String apiSecret = null;

    ChzzkLoginAdapter loginAdapter;

    /**
     * Creates a new {@link ChzzkClientBuilder} with API Key.
     */
    public ChzzkClientBuilder(String apiClientId, String apiSecret) {
        this.isAnonymous = true;
        this.apiClientId = apiClientId;
        this.apiSecret = apiSecret;
    }

    /**
     * Creates a new {@link ChzzkClientBuilder} without API Key.
     */
    public ChzzkClientBuilder() {
        this.isAnonymous = true;
    }

    public ChzzkClientBuilder withDebugMode() {
        isDebug = true;

        return this;
    }

    public ChzzkClientBuilder withLoginAdapter(ChzzkLoginAdapter adapter) {
        isAnonymous = false;
        loginAdapter = adapter;

        return this;
    }

    public ChzzkClient build() {
        ChzzkClient chzzk = new ChzzkClient(this);
        chzzk.isDebug = this.isDebug;
        return chzzk;
    }
}
