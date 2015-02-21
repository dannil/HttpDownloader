package org.dannil.httpdownloader.test.integrationtest;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.ReflectionUtility;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for login service
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class LoginServiceIntegrationTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ILoginService loginService;

	@Test
	public final void loginUser() {
		final User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		final User login = this.loginService.login(user.getEmail(), user.getPassword());

		Assert.assertNotEquals(null, login);
	}

	@Test
	public final void loginUserWithId() {
		final User user = new User(TestUtility.getUser());
		final User registered = this.registerService.save(user);

		final User login = this.loginService.findById(registered.getId());

		Assert.assertNotEquals(null, login);
	}

	@Test
	public final void loginUserWithNonExistingEmail() {
		final User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		final User login = this.loginService.login("placeholder@placeholder.com", user.getPassword());

		Assert.assertEquals(null, login);
	}

	@Test
	public final void loginUserWithIncorrectPassword() {
		final User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		final User login = this.loginService.login(user.getEmail(), "placeholder");

		Assert.assertEquals(null, login);
	}

	@Test(expected = RuntimeException.class)
	public final void loginExistingUserWithInvalidHashingAlgorithm() throws NoSuchFieldException, SecurityException, Exception {
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		try {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("PBKDF2_ALGORITHM"), "blabla");

			this.loginService.login(saved.getEmail(), saved.getPassword());
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("PBKDF2_ALGORITHM"), "PBKDF2WithHmacSHA1");
		}
	}

}
