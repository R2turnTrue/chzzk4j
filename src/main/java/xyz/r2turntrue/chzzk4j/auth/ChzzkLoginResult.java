package xyz.r2turntrue.chzzk4j.auth;

import java.util.Objects;

public class ChzzkLoginResult {

    String legacy_NID_AUT;
    String legacy_NID_SES;
    String accessToken;
    String refreshToken;
    int tokenExpiresIn;

    public String legacy_NID_AUT() {
        return legacy_NID_AUT;
    }

    public String legacy_NID_SES() {
        return legacy_NID_SES;
    }

    public String accessToken() {
        return accessToken;
    }

    public String refreshToken() {
        return refreshToken;
    }

    public int tokenExpiresIn() {
        return tokenExpiresIn;
    }

    public ChzzkLoginResult(String legacy_NID_AUT, String legacy_NID_SES, String accessToken, String refreshToken, int tokenExpiresIn) {
        this.legacy_NID_AUT = legacy_NID_AUT;
        this.legacy_NID_SES = legacy_NID_SES;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenExpiresIn = tokenExpiresIn;
    }

    public void _setLegacy_NID_AUT(String legacy_NID_AUT) {
        this.legacy_NID_AUT = legacy_NID_AUT;
    }

    public void _setLegacy_NID_SES(String legacy_NID_SES) {
        this.legacy_NID_SES = legacy_NID_SES;
    }

    public void _setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void _setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void _setTokenExpiresIn(int tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkLoginResult that = (ChzzkLoginResult) o;
        return tokenExpiresIn == that.tokenExpiresIn && Objects.equals(legacy_NID_AUT, that.legacy_NID_AUT) && Objects.equals(legacy_NID_SES, that.legacy_NID_SES) && Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legacy_NID_AUT, legacy_NID_SES, accessToken, refreshToken, tokenExpiresIn);
    }
}
