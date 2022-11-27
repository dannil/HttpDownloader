package com.github.dannil.httpdownloader.validator;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Class which encapsulates generic validation methods which isn't specific to any other
 * class.
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class GenericValidator {

    protected GenericValidator() {

    }

    /**
     * Validate the specified string as containing only letters.
     * 
     * @param input
     *            the string to validate
     * 
     * @return true if the string contains only letters, false if not
     */
    protected final boolean isLettersOnly(final String input) {
        return (input == null) ? false : input.matches(".*[^0-9].*");
    }

    /**
     * Validate the specified string as a valid e-mail format.
     * 
     * @param email
     *            the string to validate as a e-mail
     * 
     * @return true if the string is a valid e-mail format, false if not
     */
    protected final boolean isValidEmail(final String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

}
