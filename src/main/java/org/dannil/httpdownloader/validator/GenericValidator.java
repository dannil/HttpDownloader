package org.dannil.httpdownloader.validator;

import org.hibernate.validator.internal.constraintvalidators.EmailValidator;

public abstract class GenericValidator {

	protected final boolean isLettersOnly(final String string) {
		return string.matches(".*[^0-9].*");
	}

	// CHANGE IN THE FUTURE
	protected final boolean isNumbersOnly(final String string) {
		return string.matches(".*[0-9]+.*");
	}

	protected final boolean isValidEmail(final String email) {
		return new EmailValidator().isValid(email, null);
		// return
		// email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}
}
