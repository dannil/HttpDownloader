package com.github.dannil.httpdownloader.exception;

/**
 * Exception that is thrown if an error occurs on login.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 3270258098501820647L;

    /**
     * Default constructor.
     */
    public LoginException() {
        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param message
     *            the message
     */
    public LoginException(String message) {
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
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overloaded constructor.
     *
     * @param cause
     *            the cause
     */
    public LoginException(Throwable cause) {
        super(cause);
    }

}
