package com.github.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.repository.UserRepository;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.ReflectionUtility;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.PasswordUtility;

/**
 * Integration tests for register service
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class RegisterServiceIntegrationTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findUserById() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		User user = new User(TestUtility.getUser());
		User registered = this.registerService.save(user);

		User find = this.userRepository.findOne(registered.getId());

		Assert.assertNotEquals(null, find);
	}

	@Test
	public void findUserByEmail() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		User find = this.userRepository.findByEmail(user.getEmail());

		Assert.assertNotEquals(null, find);
	}

	@Test
	public void registerUserWithExistingEmail() {
		User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		User fetched = this.registerService.findByEmail(user.getEmail());

		Assert.assertNotEquals(null, fetched);
	}

	@Test(expected = RuntimeException.class)
	public void registerUserWithInvalidSaltAlgorithm() throws NoSuchFieldException, SecurityException, Exception {
		try {
			User user = new User(TestUtility.getUser());

			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM"),
					"blabla");

			this.registerService.save(user);
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM"),
					"SHA1PRNG");
		}
	}

	@Test(expected = RuntimeException.class)
	public void registerUserWithInvalidSaltAlgorithmProvider() throws NoSuchFieldException, SecurityException,
			Exception {
		try {
			User user = new User(TestUtility.getUser());

			ReflectionUtility.setValueToFinalStaticField(
					PasswordUtility.class.getDeclaredField("SALT_ALGORITHM_PROVIDER"), "blabla");

			this.registerService.save(user);
		} finally {
			ReflectionUtility.setValueToFinalStaticField(
					PasswordUtility.class.getDeclaredField("SALT_ALGORITHM_PROVIDER"), "SUN");
		}
	}
}