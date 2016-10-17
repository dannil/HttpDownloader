package com.github.dannil.httpdownloader.test.unittest;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.PasswordUtility;

/**
 * Unit tests for user
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class UserUnitTest {

	@Test
	public final void createUserWithConstructor() {
		final User user = new User(TestUtility.getUser());

		final User userConstructor = new User(user.getEmail(), user.getPassword(), user.getFirstname(),
				user.getLastname(), user.getDownloads());
		userConstructor.setId(user.getId());

		Assert.assertEquals(user, userConstructor);
	}

	@Test
	public final void createUserWithMethods() {
		final User userUtility = new User(TestUtility.getUser());
		final User userMethods = new User();

		userMethods.setId(userUtility.getId());
		userMethods.setEmail(userUtility.getEmail());
		userMethods.setPassword(userUtility.getPassword());
		userMethods.setFirstname(userUtility.getFirstname());
		userMethods.setLastname(userUtility.getLastname());
		userMethods.setDownloads(userUtility.getDownloads());

		Assert.assertEquals(userUtility, userMethods);
	}

	@Test
	public final void userEquals() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		Assert.assertTrue(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsItself() {
		final User userEquals1 = new User(TestUtility.getUser());

		Assert.assertTrue(userEquals1.equals(userEquals1));
	}

	@Test
	public final void userNotEqualsWithNull() {
		final User userEquals1 = new User(TestUtility.getUser());

		Assert.assertFalse(userEquals1.equals(null));
	}

	@Test
	public final void userNotEqualsWithIncompatibleObject() {
		final User userEquals1 = new User(TestUtility.getUser());
		final Download downloadEquals1 = new Download(TestUtility.getDownload());

		Assert.assertFalse(userEquals1.equals(downloadEquals1));
	}

	@Test
	public final void userNotEqualsOnEmail() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals2.setEmail(userEquals1.getEmail() + "a");

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullEmail() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setEmail(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullEmail() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setEmail(null);
		userEquals2.setEmail(null);

		Assert.assertTrue(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnPassword() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeySpecException {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
		userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

		userEquals2.setPassword(userEquals1.getPassword() + "a");

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullPassword() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeySpecException {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
		userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

		userEquals1.setPassword(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullPassword() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidKeySpecException {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
		userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

		userEquals1.setPassword(null);
		userEquals2.setPassword(null);

		Assert.assertTrue(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnFirstname() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals2.setFirstname(userEquals1.getFirstname() + "a");

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullFirstname() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setFirstname(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullFirstname() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setFirstname(null);
		userEquals2.setFirstname(null);

		Assert.assertTrue(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnLastname() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals2.setLastname(userEquals1.getId() + "a");

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullLastname() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setLastname(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullLastname() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setLastname(null);
		userEquals2.setLastname(null);

		Assert.assertTrue(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnDownloads() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		final Download download = new Download(TestUtility.getDownload());
		userEquals2.addDownload(download);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullDownloads() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setDownloads(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullDownloads() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setDownloads(null);
		userEquals2.setDownloads(null);

		Assert.assertTrue(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userHashCode() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullId() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setId(null);
		userHashCode2.setId(null);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullEmail() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setEmail(null);
		userHashCode2.setEmail(null);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullPassword() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setPassword(null);
		userHashCode2.setPassword(null);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullFirstname() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setFirstname(null);
		userHashCode2.setFirstname(null);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullLastname() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setLastname(null);
		userHashCode2.setLastname(null);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullDownloads() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setDownloads(null);
		userHashCode2.setDownloads(null);

		Assert.assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
	}

	@Test
	public final void userToString() {
		final User userToString1 = new User(TestUtility.getUser());
		final User userToString2 = new User(userToString1);

		Assert.assertEquals(userToString1.toString(), userToString2.toString());
	}

	@Test
	public final void addDownloadToUser() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		Assert.assertEquals(1, user.getDownloads().size());
	}

	@Test
	public final void addDownloadToUserWithConstructor() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final User check = new User(user);

		Assert.assertEquals(1, check.getDownloads().size());
	}

	@Test
	public final void addDownloadToUserNull() {
		final User user = new User(TestUtility.getUser());

		user.addDownload(null);

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void addDownloadToUserWithNullId() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		download.setId(null);

		user.addDownload(download);
	}

}
