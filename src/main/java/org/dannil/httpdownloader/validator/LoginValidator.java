package org.dannil.httpdownloader.validator;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class which handles validation for login process.
 * 
 * @author Daniel Nilsson
 */
@Component(value = "LoginValidator")
public final class LoginValidator extends GenericValidator implements Validator {

	private final static Logger LOGGER = Logger.getLogger(LoginValidator.class.getName());

	@Autowired
	private ILoginService loginService;

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
