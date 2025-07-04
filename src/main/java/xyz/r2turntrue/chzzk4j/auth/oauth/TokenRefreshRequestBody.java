package xyz.r2turntrue.chzzk4j.auth.oauth;

public class TokenRefreshRequestBody {
    private final String grantType;
    private final String refreshToken;
    private final String clientId;
    private final String clientSecret;

    public TokenRefreshRequestBody(String grantType, String refreshToken, String clientId, String clientSecret) {
        this.grantType = grantType;
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}