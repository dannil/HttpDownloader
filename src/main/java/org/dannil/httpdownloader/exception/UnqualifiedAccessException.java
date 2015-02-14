package org.dannil.httpdownloader.exception;

/**
 * Indicates that an unqualified access attempt was made; this could be an injection 
 * attempt (the user attempted to fetch a download that isn't related to that user).
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       1.0.0
 */
public final class UnqualifiedAccessException extends Exception {

	private static final long serialVersionUID = -2701538840920442047L;

	public UnqualifiedAccessException() {
		super();
	}

	public UnqualifiedAccessException(final String message) {
		super(message);
	}

}
