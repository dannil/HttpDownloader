package com.github.dannil.httpdownloader.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.ILoginService;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.ReflectionUtility;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.PasswordUtility;

/**
 * Integration tests for login service
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class LoginServiceIntegrationTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ILoginService loginService;

	@Test
	public void loginUser() {
		User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		User login = this.loginService.login(user.getEmail(), user.getPassword());

		Assert.assertNotEquals(null, login);
	}

	@Test
	public void loginUserWithId() {
		User user = new User(TestUtility.getUser());
		User registered = this.registerService.save(user);

		User login = this.loginService.findById(registered.getId());

		Assert.assertNotEquals(null, login);
	}

	@Test
	public void loginUserWithNonExistingEmail() {
		User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		User login = this.loginService.login("placeholder@placeholder.com", user.getPassword());

		Assert.assertEquals(null, login);
	}

	@Test
	public void loginUserWithIncorrectPassword() {
		User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		User login = this.loginService.login(user.getEmail(), "placeholder");

		Assert.assertEquals(null, login);
	}

	@Test(expected = RuntimeException.class)
	public void loginExistingUserWithInvalidHashingAlgorithm() throws NoSuchFieldException, SecurityException,
			Exception {
		User user = new User(TestUtility.getUser());

		User saved = this.registerService.save(user);

		try {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("PBKDF2_ALGORITHM"),
					"blabla");

			this.loginService.login(saved.getEmail(), saved.getPassword());
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("PBKDF2_ALGORITHM"),
					"PBKDF2WithHmacSHA1");
		}
	}
}
