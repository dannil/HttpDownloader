package org.dannil.httpdownloader.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.controller.AccessController;
import org.dannil.httpdownloader.controller.DownloadsController;
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
import org.dannil.httpdownloader.utility.URLUtility;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * Class for running all the database integration tests in this project. Utilizes the 
 * Spring JUnit runner instead of the regular JUnit runner, or else the tests wouldn't 
 * be able to utilize Spring Framework dependencies (such as @Autowired, which is 
 * scattered in almost all the classes).
 * 
 * @author Daniel Nilsson
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public final class IntegrationTest {

	@Autowired
	private AccessController accessController;

	@Autowired
	private DownloadsController downloadsController;

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ILoginService loginService;

	@Autowired
	private UserRepository userRepository;

	// ----- SERVICE ----- //

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
	public final void deleteDownloadFromDisk() throws InterruptedException, IOException {
		final Download download = new Download(TestUtility.getDownload());

		final Download saved = this.downloadService.saveToDisk(download);

		Thread.sleep(1500);

		this.downloadService.delete(saved);

		Thread.sleep(1500);

		File file = new File(PathUtility.getAbsolutePathToDownloads() + "/" + saved.getFormat());
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	@Test
	public final void deleteDownload() {
		final Download download = new Download(TestUtility.getDownload());

		final Download registered = this.downloadService.save(download);

		this.downloadService.delete(registered.getId());

		Assert.assertEquals(null, this.downloadService.findById(registered.getId()));
	}

	// ----- CONTROLLER ----- //

	@Test
	public final void loginExistingUser() {
		final User user = new User(TestUtility.getUser());
		final HttpSession session = mock(HttpSession.class);
		final BindingResult result = mock(BindingResult.class);

		this.registerService.save(user);

		final String path = this.accessController.loginPOST(session, user, result);

		Assert.assertEquals(URLUtility.redirect(PathUtility.URL_DOWNLOADS), path);
	}

	@Test
	public final void loginNonExistingUser() {
		final HttpSession session = mock(HttpSession.class);
		final User user = new User(TestUtility.getUser());
		final BindingResult result = mock(BindingResult.class);

		final String path = this.accessController.loginPOST(session, user, result);
		Assert.assertEquals(URLUtility.redirect(PathUtility.URL_LOGIN), path);
	}

	@Test
	public final void loginUserWithErrors() {
		final HttpSession session = mock(HttpSession.class);

		final User user = new User(TestUtility.getUser());
		user.setEmail(null);
		user.setPassword(null);

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.accessController.loginPOST(session, user, result);

		Assert.assertTrue(result.hasErrors());
	}

	@Test
	public final void registerUserSuccess() {
		final HttpSession session = mock(HttpSession.class);
		final User user = new User(TestUtility.getUser());

		final BindingResult result = new BeanPropertyBindingResult(user, "user");

		final String path = this.accessController.registerPOST(session, user, result);
		Assert.assertEquals(URLUtility.redirect(PathUtility.URL_LOGIN), path);
	}

	@Test
	public final void addDownloadStartDownloadingProcess() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final DateTime startDate = download.getStartDate();

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn("start");

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(-1, startDate.compareTo(download.getStartDate()));
	}

	@Test
	public final void addDownloadStartDownloadingProcessAndValidateLandingPage() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn("start");

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		final String path = this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(URLUtility.redirect(PathUtility.URL_DOWNLOADS), path);
	}

	@Test
	public final void addDownloadDoNotStartDownloadingProcess() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final DateTime startDate = download.getStartDate();

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn(null);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(0, startDate.compareTo(download.getStartDate()));
	}

	@Test
	public final void downloadsControllerGetDownloadByIdSuccess() throws InterruptedException, IOException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final Download savedDownload = this.downloadService.saveToDisk(download);

		Thread.sleep(1000);

		saved.addDownload(savedDownload);

		final ServletOutputStream stream = mock(ServletOutputStream.class);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, saved.getDownloads().get(0).getId());

		// if the test goes well, we should recieve null back as the path, as
		// getting
		// a download doesn't redirect us to any page.
		Assert.assertNull(path);
	}

	@Test
	public final void downloadsControllerGetDownloadByIdInjectionAttemptDownloadsUserIdDoesNotEqualSessionUserId() throws IOException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User injector = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		final User injectorSaved = this.registerService.save(injector);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);
		injectorSaved.addDownload(savedDownload);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, injectorSaved.getDownloads().get(0).getId());

		Assert.assertEquals(URLUtility.redirect(PathUtility.URL_DOWNLOADS), path);
	}

	@Test
	public final void downloadsControllerDeleteDownloadByIdSuccess() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		this.downloadsController.downloadsDeleteIdGET(session, saved.getDownloads().get(0).getId());

		Assert.assertNull(this.downloadService.findById(savedDownload.getId()));
	}

	@Test
	public final void downloadsControllerDeleteDownloadByIdInjectionAttemptDownloadsUserIdDoesNotEqualSessionUserId() {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User injector = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		final User injectorSaved = this.registerService.save(injector);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);
		injectorSaved.addDownload(savedDownload);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsDeleteIdGET(session, injectorSaved.getDownloads().get(0).getId());

		Assert.assertEquals(URLUtility.redirect(PathUtility.URL_DOWNLOADS), path);
	}

}
