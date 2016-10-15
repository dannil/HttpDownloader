package com.github.dannil.httpdownloader.exception;

public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = -9035069668277386157L;

	/**
	 * Default constructor.
	 */
	public ConfigException() {
		super();
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public ConfigException(String message) {
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
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ConfigException(Throwable cause) {
		super(cause);
	}

}
