package xyz.r2turntrue.chzzk4j.auth.oauth;

public record TokenResponseBody(
        String accessToken,
        String refreshToken,
        String tokenType,
        int expiresIn
) {
}
