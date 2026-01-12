package xyz.r2turntrue.chzzk4j.auth.oauth;

public record TokenRevokeRequestBody(
        String clientId,
        String clientSecret,
        String token,
        String tokenTypeHint) {
}
