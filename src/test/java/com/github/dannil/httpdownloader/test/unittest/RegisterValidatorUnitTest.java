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
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.validator.RegisterValidator;

/**
 * Unit tests for register validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class RegisterValidatorUnitTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private RegisterValidator registerValidator;

	@Test(expected = ClassCastException.class)
	public final void tryToValidateNonUserObjectRegistering() {
		final Download download = new Download(TestUtility.getDownload());
		this.registerValidator.validate(download, null);
	}

	@Test
	public final void validateUserRegisteringExistingEmail() {
		final User user = new User(TestUtility.getUser());

		final User attempt = this.registerService.save(user);

		final BindingResult result = new BeanPropertyBindingResult(attempt, "user");
		this.registerValidator.validate(attempt, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
	}

	@Test
	public final void validateUserRegisteringWithMalformedFirstnameAndLastname() {
		final User user = new User(TestUtility.getUser());
		user.setFirstname(null);
		user.setLastname(null);

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.registerValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("firstname"));
		Assert.assertTrue(result.hasFieldErrors("lastname"));
	}

	@Test
	public final void validateUserRegisteringWithMalformedEmail() {
		final User user = new User(TestUtility.getUser());
		user.setEmail("abc@abc");

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.registerValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
	}

}
