package xyz.r2turntrue.chzzk4j.auth.oauth;

public record TokenRequestBody(
        String grantType,
        String clientId,
        String clientSecret,
        String code,
        String state
) {
}
