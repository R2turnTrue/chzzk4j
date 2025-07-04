package xyz.r2turntrue.chzzk4j.auth.oauth;

public class TokenRequestBody {
    private final String grantType;
    private final String clientId;
    private final String clientSecret;
    private final String code;
    private final String state;

    public TokenRequestBody(String grantType, String clientId, String clientSecret, String code, String state) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.state = state;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}