package com.github.dannil.httpdownloader.test.unittest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith; import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.utility.PasswordUtility;

/**
 * Unit tests for password utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class) @WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml", "classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class PasswordUtilityUnitTest {

	@Test(expected = Exception.class)
	public final void passwordUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<PasswordUtility> constructor = PasswordUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public final void getHashedPassword() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeySpecException {
		final String password = "pass";
		final String hash = PasswordUtility.getHashedPassword(password);

		Assert.assertNotNull(hash);
	}

	@Test
	public final void validateHashedPasswordCorrect() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeySpecException {
		final String password = "pass";
		final String hash = PasswordUtility.getHashedPassword(password);

		Assert.assertTrue(PasswordUtility.compareHashedPasswords(password, hash));
	}

	@Test
	public final void validateHashedPasswordIncorrect() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeySpecException {
		final String password1 = "pass1";
		final String password2 = "pass2";

		final String hash = PasswordUtility.getHashedPassword(password1);

		Assert.assertFalse(PasswordUtility.compareHashedPasswords(password2, hash));
	}

}
