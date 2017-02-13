package com.github.dannil.httpdownloader.exception;

public class ParsingException extends RuntimeException {

    private static final long serialVersionUID = -8691227007904461714L;

    /**
     * Default constructor.
     */
    public ParsingException() {
        super();
    }

    /**
     * Overloaded constructor.
     * 
     * @param message
     *            the message
     */
    public ParsingException(String message) {
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
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overloaded constructor.
     * 
     * @param cause
     *            the cause
     */
    public ParsingException(Throwable cause) {
        super(cause);
    }

}
