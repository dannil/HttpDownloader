package org.dannil.httpdownloader.validator;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.RegisterService;
import org.dannil.httpdownloader.utility.ValidationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class which handles validation for register process.
 * 
 * @author Daniel Nilsson
 */
@Component(value = "RegisterValidator")
public final class RegisterValidator extends GenericValidator implements Validator {

	private final static Logger LOGGER = Logger.getLogger(RegisterValidator.class.getName());

	@Autowired
	private RegisterService registerService;

	@Override
	public final boolean supports(final Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public final void validate(final Object target, final Errors errors) {
		final User user = (User) target;

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "invalid_firstname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "invalid_lastname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid_email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid_password");

		// COMPLEX VALIDATIONS

		// Check if a user already exists with the specified e-mail
		if (!ValidationUtility.isNull(this.registerService.findByEmail(user.getEmail()))) {
			errors.reject("email", "email_already_in_use");
			LOGGER.error("INVALID EMAIL - ALREADY IN USE");
		}

		// Check for numbers
		if (!isLettersOnly(user.getFirstname())) {
			errors.reject("firstname", "invalid_firstname");
			LOGGER.error("INVALID FIRSTNAME - CONTAINS NUMBERS");
		}
		if (!isLettersOnly(user.getLastname())) {
			errors.reject("firstname", "invalid_lastname");
			LOGGER.error("INVALID LASTNAME - CONTAINS NUMBERS");
		}

		// Correct e-mail format
		if (!isValidEmail(user.getEmail())) {
			errors.reject("email", "invalid_email");
			LOGGER.error("INVALID EMAIL - MALFORMED PATTERN");
		}

	}

}