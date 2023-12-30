package xyz.r2turntrue.chzzk4j.exception;

import java.io.InvalidObjectException;

public class NotExistsException extends InvalidObjectException {
    /**
     * Constructs an {@code NotExistsException}.
     *
     * @param reason Detailed message explaining the reason for the failure.
     */
    public NotExistsException(String reason) {
        super(reason);
    }
}
