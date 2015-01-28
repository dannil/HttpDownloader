package org.dannil.httpdownloader.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.ILoginService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.ReflectionUtility;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class for running all the database integration tests in this project. Utilizes the 
 * Spring JUnit runner instead of the regular JUnit runner, or else the tests wouldn't 
 * be able to utilize Spring Framework dependencies (such as @Autowired, which is 
 * scattered in almost all the classes).
 * 
 * @author Daniel Nilsson
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/conf/xml/spring-context.xml")
public final class DatabaseTest {

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ILoginService loginService;

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

			final User saved = this.registerService.save(user);
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM"), "SHA1PRNG");
		}
	}

	@Test(expected = RuntimeException.class)
	public final void registerUserWithInvalidSaltAlgorithmProvider() throws NoSuchFieldException, SecurityException, Exception {
		try {
			final User user = new User(TestUtility.getUser());

			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM_PROVIDER"), "blabla");

			final User saved = this.registerService.save(user);
		} finally {
			ReflectionUtility.setValueToFinalStaticField(PasswordUtility.class.getDeclaredField("SALT_ALGORITHM_PROVIDER"), "SUN");
		}
	}

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

	@Test
	public final void findDownloadById() {
		final Download download = new Download(TestUtility.getDownload());
		final Download registered = this.downloadService.save(download);

		final Download find = this.downloadService.findById(registered.getId());

		Assert.assertNotEquals(null, find);
	}

	@Test
	public final void findDownloadsByUser() {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User registered = this.registerService.save(user);

		download.setUser(registered);

		this.downloadService.save(download);

		final LinkedList<Download> result = this.downloadService.findByUser(registered);

		Assert.assertEquals(1, result.size());
	}

	@Test
	public final void saveDownloadToDisk() throws InterruptedException {
		final Download download = new Download(TestUtility.getDownload());

		final Download saved = this.downloadService.saveToDisk(download);

		Thread.sleep(1000);

		Assert.assertNotEquals(null, saved);
	}

	@Test(expected = FileNotFoundException.class)
	public final void deleteDownloadFromDisk() throws InterruptedException, FileNotFoundException {
		final Download download = new Download(TestUtility.getDownload());

		final Download saved = this.downloadService.saveToDisk(download);

		Thread.sleep(1000);

		this.downloadService.delete(saved);

		Thread.sleep(1000);

		File file = new File(PathUtility.getAbsolutePathToDownloads() + "/" + saved.getFormat());
		FileInputStream stream = new FileInputStream(file);
	}

	@Test
	public final void deleteDownload() {
		final Download download = new Download(TestUtility.getDownload());

		final Download registered = this.downloadService.save(download);

		this.downloadService.delete(registered.getId());

		Assert.assertEquals(null, this.downloadService.findById(registered.getId()));
	}

}
