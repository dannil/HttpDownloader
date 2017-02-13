package com.github.dannil.httpdownloader.exception;

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
