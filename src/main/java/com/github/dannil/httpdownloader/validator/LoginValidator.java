package com.github.dannil.httpdownloader.validator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.dannil.httpdownloader.model.User;

/**
 * Class which handles validation for login process.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component(value = "LoginValidator")
public class LoginValidator extends GenericValidator implements Validator {

	private static final Logger LOGGER = Logger.getLogger(LoginValidator.class.getName());

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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid_email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid_password");

		// COMPLEX VALIDATIONS

		// Correct e-mail format
		if (!isValidEmail(user.getEmail())) {
			errors.rejectValue("email", "invalid_email");
			LOGGER.error("INVALID EMAIL - MALFORMED PATTERN");
		}
	}

}
