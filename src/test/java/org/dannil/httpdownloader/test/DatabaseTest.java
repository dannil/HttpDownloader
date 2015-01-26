package org.dannil.httpdownloader.test;

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
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

		Thread.sleep(2000);

		Assert.assertNotEquals(null, saved);
	}

	@Test
	public final void deleteDownloadFromDisk() throws InterruptedException {
		final Download download = new Download(TestUtility.getDownload());

		final Download saved = this.downloadService.saveToDisk(download);

		Thread.sleep(2000);

		this.downloadService.delete(saved);
	}

	@Test
	public final void deleteDownload() {
		final Download download = new Download(TestUtility.getDownload());

		final Download registered = this.downloadService.save(download);

		this.downloadService.delete(registered.getId());

		Assert.assertEquals(null, this.downloadService.findById(registered.getId()));
	}

}
