package com.github.dannil.httpdownloader.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for register validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class RegisterValidatorUnitTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private RegisterValidator registerValidator;

	@Test(expected = ClassCastException.class)
	public void tryToValidateNonUserObjectRegistering() {
		Download download = TestUtility.getDownload();
		this.registerValidator.validate(download, null);
	}

	@Test
	public void validateUserRegisteringExistingEmail() {
		User user = TestUtility.getUser();

		User attempt = this.registerService.save(user);

		BindingResult result = new BeanPropertyBindingResult(attempt, "user");
		this.registerValidator.validate(attempt, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
	}

	@Test
	public void validateUserRegisteringWithMalformedFirstnameAndLastname() {
		User user = TestUtility.getUser();
		user.setFirstname(null);
		user.setLastname(null);

		BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.registerValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("firstname"));
		Assert.assertTrue(result.hasFieldErrors("lastname"));
	}

	@Test
	public void validateUserRegisteringWithMalformedEmail() {
		User user = TestUtility.getUser();
		user.setEmail("abc@abc");

		BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.registerValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
	}

}
