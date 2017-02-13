package com.github.dannil.httpdownloader.exception;

public class DownloadException extends RuntimeException {

    private static final long serialVersionUID = -2422208318566149217L;

    /**
     * Default constructor.
     */
    public DownloadException() {
        super();
    }

    /**
     * Overloaded constructor.
     * 
     * @param message
     *            the message
     */
    public DownloadException(String message) {
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
    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Overloaded constructor.
     * 
     * @param cause
     *            the cause
     */
    public DownloadException(Throwable cause) {
        super(cause);
    }

}
