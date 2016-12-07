package com.github.dannil.httpdownloader.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IRegisterService;

/**
 * Class which handles validation for register process.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component(value = "RegisterValidator")
public class RegisterValidator extends GenericValidator implements Validator {

	private static final Logger LOGGER = Logger.getLogger(RegisterValidator.class.getName());

	private static final String FIRSTNAME_FIELD = "firstname";
	private static final String LASTNAME_FIELD = "lastname";
	private static final String EMAIL_FIELD = "email";
	private static final String PASSWORD_FIELD = "password";

	@Autowired
	private IRegisterService registerService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user;
		if (this.supports(target.getClass())) {
			user = (User) target;
		} else {
			throw new ClassCastException("Can't convert " + target.getClass().getName() + " to an User object");
		}

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, FIRSTNAME_FIELD, "invalid_firstname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, LASTNAME_FIELD, "invalid_lastname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, EMAIL_FIELD, "invalid_email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, PASSWORD_FIELD, "invalid_password");

		// COMPLEX VALIDATIONS

		// Check if a user already exists with the specified e-mail
		if (this.registerService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "email_already_in_use");
			LOGGER.error("INVALID EMAIL - ALREADY IN USE");
		}

		// Check for numbers
		if (!isLettersOnly(user.getFirstname())) {
			errors.rejectValue(FIRSTNAME_FIELD, "invalid_firstname");
			LOGGER.error("INVALID FIRSTNAME - CONTAINS NUMBERS");
		}
		if (!isLettersOnly(user.getLastname())) {
			errors.rejectValue(FIRSTNAME_FIELD, "invalid_lastname");
			LOGGER.error("INVALID LASTNAME - CONTAINS NUMBERS");
		}

		// Correct e-mail format
		if (!isValidEmail(user.getEmail())) {
			errors.rejectValue(EMAIL_FIELD, "invalid_email");
			LOGGER.error("INVALID EMAIL - MALFORMED PATTERN");
		}

	}

}
