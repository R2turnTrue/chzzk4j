package xyz.r2turntrue.chzzk4j.exception;

public class NoAccessTokenOnlySupported extends Exception {
    public NoAccessTokenOnlySupported(String reason) {
        super(reason);
    }
}
