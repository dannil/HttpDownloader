package com.github.dannil.httpdownloader.exception;

/**
 * Indicates that an unqualified access attempt was made. This could for an example be an
 * injection attempt (the user attempted to fetch a download that isn't related to that
 * user).
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
public class UnqualifiedAccessException extends Exception {

    private static final long serialVersionUID = -2701538840920442047L;

    /**
     * Default constructor.
     */
    public UnqualifiedAccessException() {
        super();
    }

    /**
     * Overloaded constructor.
     *
     * @param message
     *            the message
     */
    public UnqualifiedAccessException(String message) {
        super(message);
    }

}
