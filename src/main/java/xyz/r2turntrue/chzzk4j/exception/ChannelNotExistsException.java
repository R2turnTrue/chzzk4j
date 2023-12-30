package xyz.r2turntrue.chzzk4j.exception;

public class ChannelNotExistsException extends NotExistsException {
    /**
     * Constructs an {@code ChannelNotExistsException}.
     *
     * @param reason Detailed message explaining the reason for the failure.
     */
    public ChannelNotExistsException(String reason) {
        super(reason);
    }
}
