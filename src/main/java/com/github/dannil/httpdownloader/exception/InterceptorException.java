package com.github.dannil.httpdownloader.exception;

public class InterceptorException extends RuntimeException {

	private static final long serialVersionUID = -2422208318566149217L;

	/**
	 * Default constructor.
	 */
	public InterceptorException() {
		super();
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public InterceptorException(String message) {
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
	public InterceptorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param cause
	 *            the cause
	 */
	public InterceptorException(Throwable cause) {
		super(cause);
	}

}
