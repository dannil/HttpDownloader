package com.github.dannil.httpdownloader.validator;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Class which encapsulates generic validation methods which isn't
 * specific to any other class.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
public abstract class GenericValidator {

	/**
	 * Validate the specified string as containing only letters.
	 * 
	 * @param input
	 * 				the string to validate
	 * 
	 * @return true if the string contains only letters, false if not
	 */
	protected final boolean isLettersOnly(final String input) {
		if (input == null) {
			return false;
		}
		return input.matches(".*[^0-9].*");
	}

	// CHANGE IN THE FUTURE
	/**
	 * Validate the specified string as containing only numbers.
	 * 
	 * @param input
	 * 				the string to validate
	 * 
	 * @return true if the string contains only numbers, false if not
	 */
	// protected final boolean isNumbersOnly(final String input) {
	// if (input == null) {
	// return false;
	// }
	// return input.matches(".*[0-9]+.*");
	// }

	/**
	 * Validate the specified string as a valid e-mail format.
	 * 
	 * @param email
	 * 				the string to validate as a e-mail
	 * 
	 * @return true if the string is a valid e-mail format, false if not
	 */
	protected final boolean isValidEmail(final String email) {
		if (email == null) {
			return false;
		}
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
		// return
		// email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}
}
