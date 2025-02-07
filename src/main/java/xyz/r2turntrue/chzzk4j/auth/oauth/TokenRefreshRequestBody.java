package xyz.r2turntrue.chzzk4j.auth.oauth;

public record TokenRefreshRequestBody(
        String grantType,
        String refreshToken,
        String clientId,
        String clientSecret
) {
}
