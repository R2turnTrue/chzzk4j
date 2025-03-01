package xyz.r2turntrue.chzzk4j.session.message.system;

public class ClientboundSystemConnected {
    private String sessionKey;

    public String getSessionKey() {
        return sessionKey;
    }

    @Override
    public String toString() {
        return "ClientboundSystemConnected{" +
                "sessionKey='" + sessionKey + '\'' +
                '}';
    }
}
