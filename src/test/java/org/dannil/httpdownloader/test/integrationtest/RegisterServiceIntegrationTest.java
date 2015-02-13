package org.dannil.httpdownloader.test.integrationtest;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
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
 * Integration tests for register service
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class RegisterServiceIntegrationTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public final void findUserById() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final User user = new User(TestUtility.getUser());
		final User registered = this.registerService.save(user);

		final User find = this.userRepository.findOne(registered.getId());

		Assert.assertNotEquals(null, find);
	}

	@Test
	public final void findUserByEmail() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		final User find = this.userRepository.findByEmail(user.getEmail());

		Assert.assertNotEquals(null, find);
	}

	@Test
	public final void registerUserWithExistingEmail() {
		final User user = new User(TestUtility.getUser());
		this.registerService.save(user);

		final User fetched = this.registerService.findByEmail(user.getEmail());

		Assert.assertNotEquals(null, fetched);
	}

	@Test(expected = RuntimeException.class)
	public final void registerUserWithInvalidSaltAlgorithm() throws NoSuchFieldException, SecurityException, Exception {
		try {
			final User user = new User(TestUtility.getUser());

			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM"), "blabla");

			this.registerService.save(user);
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM"), "SHA1PRNG");
		}
	}

	@Test(expected = RuntimeException.class)
	public final void registerUserWithInvalidSaltAlgorithmProvider() throws NoSuchFieldException, SecurityException, Exception {
		try {
			final User user = new User(TestUtility.getUser());

			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM_PROVIDER"), "blabla");

			this.registerService.save(user);
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM_PROVIDER"), "SUN");
		}
	}

}
