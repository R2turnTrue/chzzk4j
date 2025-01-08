package xyz.r2turntrue.chzzk4j.chat.event;

public class ConnectionClosedEvent extends ChzzkEvent {
    private int code;
    private String reason;
    private boolean remote;
    private boolean shouldReconnect;

    public ConnectionClosedEvent(int code, String reason, boolean remote, boolean shouldReconnect) {
        this.code = code;
        this.reason = reason;
        this.remote = remote;
        this.shouldReconnect = shouldReconnect;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public boolean isRemote() {
        return remote;
    }

    public boolean isShouldReconnect() {
        return shouldReconnect;
    }
}
