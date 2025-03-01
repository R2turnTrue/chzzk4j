package xyz.r2turntrue.chzzk4j.session;

import xyz.r2turntrue.chzzk4j.ChzzkClient;

import java.io.IOException;

public class ChzzkSessionBuilder {

    private ChzzkClient chzzk;
    private boolean autoRecreate = true;

    public ChzzkSessionBuilder(ChzzkClient chzzk) {
        this.chzzk = chzzk;
    }

    public ChzzkSessionBuilder withAutoRecreate(boolean autoRecreate) {
        this.autoRecreate = autoRecreate;
        return this;
    }

    public ChzzkClientSession buildClientSession() throws IOException {
        return new ChzzkClientSession(chzzk, autoRecreate);
    }

    public ChzzkUserSession buildUserSession() throws IOException {
        if (!chzzk.isLoggedIn()) {
            throw new IllegalStateException("Should be logged in to build an user session!");
        }

        return new ChzzkUserSession(chzzk, autoRecreate);
    }

}
