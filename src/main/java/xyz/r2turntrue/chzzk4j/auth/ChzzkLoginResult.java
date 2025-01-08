package xyz.r2turntrue.chzzk4j.auth;

public record ChzzkLoginResult(
        String legacy_NID_AUT,
        String legacy_NID_SES,
        String accessToken,
        String refreshToken,
        int tokenExpiresIn
) {
}
