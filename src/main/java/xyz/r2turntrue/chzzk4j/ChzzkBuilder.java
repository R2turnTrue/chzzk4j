package xyz.r2turntrue.chzzk4j;

/**
 * Class for creating instances of {@link Chzzk}.
 */
public class ChzzkBuilder {
    boolean isAnonymous = false;
    String nidAuth;
    String nidSession;

    /**
     * Creates a new {@link ChzzkBuilder} that not logged in.
     */
    public ChzzkBuilder() {
        this.isAnonymous = true;
    }

    /**
     * Creates a new {@link ChzzkBuilder} that logged in.
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
    public ChzzkBuilder(String nidAuth, String nidSession) {
        this.nidAuth = nidAuth;
        this.nidSession = nidSession;
    }

    public Chzzk build() {
        return new Chzzk(this);
    }
}
