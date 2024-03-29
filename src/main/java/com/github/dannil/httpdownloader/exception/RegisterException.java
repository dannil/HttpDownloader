package com.github.dannil.httpdownloader.exception;

/**
 * Exception that is thrown if an error occurs on registration.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
public class RegisterException extends RuntimeException {

    private static final long serialVersionUID = 8271718841650891999L;

    /**
     * Default constructor.
     */
    public RegisterException() {
        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param message
     *            the message
     */
    public RegisterException(String message) {
        super(message);
    }

    /**
     * Overloaded constructor.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overloaded constructor.
     *
     * @param cause
     *            the cause
     */
    public RegisterException(Throwable cause) {
        super(cause);
    }

}
