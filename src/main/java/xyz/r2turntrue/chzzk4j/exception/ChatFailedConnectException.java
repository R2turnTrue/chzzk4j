package xyz.r2turntrue.chzzk4j.exception;

public class ChatFailedConnectException extends IllegalStateException {
    public int errorCode;
    public String errorMessage;

    public ChatFailedConnectException(int errorCode, String errorMessage) {
        super("Failed to connect to chat! (Message: " + errorMessage + ", Code: " + errorCode + ")");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
