package org.dannil.httpdownloader.validator;

import javax.inject.Inject;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component(value = "LoginValidator")
public final class LoginValidator extends GenericValidator implements Validator {

	@Inject
	private ILoginService loginService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final User user = (User) target;

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid_email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid_password");

		// COMPLEX VALIDATIONS

		// Correct e-mail format
		if (!isValidEmail(user.getEmail())) {
			errors.reject("email", "invalid_email");
			System.out.println("INVALID EMAIL - MALFORMED PATTERN");
		}

		// Check for a correct login
		if (!this.loginService.isLoginCorrect(user.getEmail(), user.getPassword())) {
			errors.rejectValue("email", "invalid_email");
			errors.rejectValue("password", "invalid_password");
			System.out.println("INVALID LOGIN - NO MATCH BETWEEN EMAIL/PASSWORD");
		}
	}

}
