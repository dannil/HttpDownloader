package com.github.dannil.httpdownloader.test.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.validator.LoginValidator;

/**
 * Unit tests for login validator
 * 
 * @author Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class LoginValidatorUnitTest {

	@Autowired
	private LoginValidator loginValidator;

	@Test(expected = ClassCastException.class)
	public final void tryToValidateNonUserObjectLoggingIn() {
		final Download download = new Download(TestUtility.getDownload());
		this.loginValidator.validate(download, null);
	}

	@Test
	public final void validateUserLoggingInWithNullValues() {
		final User user = new User(TestUtility.getUser());
		user.setEmail(null);
		user.setPassword(null);

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.loginValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
		Assert.assertTrue(result.hasFieldErrors("password"));
	}

	@Test
	public final void validateUserLoggingInWithMalformedValues() {
		final User user = new User(TestUtility.getUser());
		user.setEmail("");
		user.setPassword("");

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.loginValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
		Assert.assertTrue(result.hasFieldErrors("password"));
	}

	@Test
	public final void validateUserLoggingInSuccess() {
		final User user = new User(TestUtility.getUser());

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.loginValidator.validate(user, result);

		Assert.assertFalse(result.hasErrors());
	}

}
