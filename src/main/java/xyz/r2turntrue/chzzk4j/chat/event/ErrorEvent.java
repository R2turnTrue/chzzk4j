package xyz.r2turntrue.chzzk4j.chat.event;

public class ErrorEvent extends ChzzkEvent {
    private Exception exception;

    public ErrorEvent(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
