package com.github.dannil.httpdownloader.exception;

public class LanguageException extends RuntimeException {

    private static final long serialVersionUID = -4490930790085702530L;

    /**
     * Default constructor.
     */
    public LanguageException() {
        super();
    }

    /**
     * Overloaded constructor.
     * 
     * @param message
     *            the message
     */
    public LanguageException(String message) {
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
    public LanguageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overloaded constructor.
     * 
     * @param cause
     *            the cause
     */
    public LanguageException(Throwable cause) {
        super(cause);
    }

}
