package xyz.r2turntrue.chzzk4j;

import xyz.r2turntrue.chzzk4j.naver.Naver;

/**
 * Class for creating instances of {@link Chzzk}.
 */
public class ChzzkBuilder {
    boolean isAnonymous = false;
    boolean isDebug = false;
    String nidAuth;
    String nidSession;

    /**
     * Creates a new {@link ChzzkBuilder} that not logged in.
     */
    public ChzzkBuilder() {
        this.isAnonymous = true;
    }

    public ChzzkBuilder withDebugMode() {
        isDebug = true;

        return this;
    }

    /**
     * Makes {@link ChzzkBuilder} authorized.
     * To build an instance of {@link Chzzk} that logged in, we must have
     * the values of NID_AUT and NID_SES cookies.<br>
     *
     * You can get that values from developer tools of your browser.<br>
     * In Chrome, you can see the values from
     * {@code Application > Cookies > https://chzzk.naver.com}
     *
     * @param nidAuth The value of NID_AUT cookie
     * @param nidSession The value of NID_SES cookie
     */
    public ChzzkBuilder withAuthorization(String nidAuth, String nidSession) {
        this.nidAuth = nidAuth;
        this.nidSession = nidSession;
        this.isAnonymous = false;

        return this;
    }

    /**
     * Add authorize token (NID_AUT and NID_SES) automatically by {@link Naver}.
     *
     * @param naver The authorized naver object
     */
    public ChzzkBuilder withAuthorization(Naver naver) {
        return withAuthorization(naver.getCookie(Naver.Cookie.NID_AUT), naver.getCookie(Naver.Cookie.NID_SES));
    }

    public Chzzk build() {
        Chzzk chzzk = new Chzzk(this);
        chzzk.isDebug = this.isDebug;
        return chzzk;
    }
}
