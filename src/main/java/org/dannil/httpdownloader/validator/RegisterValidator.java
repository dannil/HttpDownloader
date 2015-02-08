package org.dannil.httpdownloader.validator;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class which handles validation for register process.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Component(value = "RegisterValidator")
public final class RegisterValidator extends GenericValidator implements Validator {

	private final static Logger LOGGER = Logger.getLogger(RegisterValidator.class.getName());

	@Autowired
	private IRegisterService registerService;

	@Override
	public final boolean supports(final Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public final void validate(final Object target, final Errors errors) {
		User user = null;
		if (this.supports(target.getClass())) {
			user = (User) target;
		} else {
			throw new ClassCastException("Can't convert " + target.getClass().getName() + " to an User object");
		}

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "invalid_firstname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "invalid_lastname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid_email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid_password");

		// COMPLEX VALIDATIONS

		// Check if a user already exists with the specified e-mail
		if (this.registerService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "email_already_in_use");
			LOGGER.error("INVALID EMAIL - ALREADY IN USE");
		}

		// Check for numbers
		if (!isLettersOnly(user.getFirstname())) {
			errors.rejectValue("firstname", "invalid_firstname");
			LOGGER.error("INVALID FIRSTNAME - CONTAINS NUMBERS");
		}
		if (!isLettersOnly(user.getLastname())) {
			errors.rejectValue("firstname", "invalid_lastname");
			LOGGER.error("INVALID LASTNAME - CONTAINS NUMBERS");
		}

		// Correct e-mail format
		if (!isValidEmail(user.getEmail())) {
			errors.rejectValue("email", "invalid_email");
			LOGGER.error("INVALID EMAIL - MALFORMED PATTERN");
		}

	}

}
