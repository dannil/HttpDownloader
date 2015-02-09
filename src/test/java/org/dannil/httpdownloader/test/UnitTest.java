package org.dannil.httpdownloader.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.controller.AccessController;
import org.dannil.httpdownloader.controller.DownloadsController;
import org.dannil.httpdownloader.controller.IndexController;
import org.dannil.httpdownloader.controller.LanguageController;
import org.dannil.httpdownloader.filter.CharsetFilter;
import org.dannil.httpdownloader.interceptor.AccessInterceptor;
import org.dannil.httpdownloader.interceptor.DownloadsInterceptor;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.ConfigUtility;
import org.dannil.httpdownloader.utility.FileUtility;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.dannil.httpdownloader.utility.XMLUtility;
import org.dannil.httpdownloader.validator.DownloadValidator;
import org.dannil.httpdownloader.validator.LoginValidator;
import org.dannil.httpdownloader.validator.RegisterValidator;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class for running all the unit tests in this project. Utilizes the Spring JUnit runner
 * instead of the regular JUnit runner, or else the tests wouldn't be able to utilize 
 * Spring Framework dependencies (such as @Autowired, which is scattered in almost 
 * all the classes).
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public final class UnitTest {

	@Autowired
	private AccessController accessController;

	@Autowired
	private DownloadsController downloadsController;

	@Autowired
	private IndexController indexController;

	@Autowired
	private LanguageController languageController;

	@Autowired
	private AccessInterceptor accessInterceptor;

	@Autowired
	private DownloadsInterceptor downloadsInterceptor;

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private DownloadValidator downloadValidator;

	@Autowired
	private LoginValidator loginValidator;

	@Autowired
	private RegisterValidator registerValidator;

	@Test
	public final void downloadEquals() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadEqualsItself() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());

		Assert.assertTrue(downloadEquals1.equals(downloadEquals1));
	}

	@Test
	public final void downloadNotEqualsWithNull() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());

		Assert.assertFalse(downloadEquals1.equals(null));
	}

	@Test
	public final void downloadNotEqualsWithIncompatibleObject() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final User userEquals1 = new User(TestUtility.getUser());

		Assert.assertFalse(downloadEquals1.equals(userEquals1));
	}

	@Test
	public final void downloadNotEqualsOnId() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals2.setId(downloadEquals1.getId() + 1);

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnNullId() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setId(null);

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadEqualsOnBothNullId() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setId(null);
		downloadEquals2.setId(null);

		Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnTitle() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals2.setTitle(downloadEquals1.getTitle() + "a");

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnNullTitle() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setTitle(null);

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadEqualsOnBothNullTitle() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setTitle(null);
		downloadEquals2.setTitle(null);

		Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnUrl() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals2.setUrl(downloadEquals1.getUrl() + "a");

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnNullUrl() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setUrl(null);

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadEqualsOnBothNullUrl() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setUrl(null);
		downloadEquals2.setUrl(null);

		Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnStartDate() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals2.setStartDate(new DateTime());

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnNullStartDate() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setStartDate(null);

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadEqualsOnBothNullStartDate() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setStartDate(null);
		downloadEquals2.setStartDate(null);

		Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnEndDate() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals2.setEndDate(new DateTime());

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadNotEqualsOnNullEndDate() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setEndDate(null);

		Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void downloadEqualsOnBothNullEndDate() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		downloadEquals1.setEndDate(null);
		downloadEquals2.setEndDate(null);

		Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
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
	public final void userNotEqualsOnId() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals2.setId(userEquals1.getId() + 1);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullId() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setId(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullId() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setId(null);
		userEquals2.setId(null);

		Assert.assertTrue(userEquals1.equals(userEquals2));
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
	public final void userNotEqualsOnPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
		userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

		userEquals2.setPassword(userEquals1.getPassword() + "a");

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userNotEqualsOnNullPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
		userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

		userEquals1.setPassword(null);

		Assert.assertFalse(userEquals1.equals(userEquals2));
	}

	@Test
	public final void userEqualsOnBothNullPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
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
	public final void downloadHashCode() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		Assert.assertEquals(downloadEquals1.hashCode(), downloadEquals2.hashCode());
	}

	@Test
	public final void downloadHashCodeNullId() {
		final Download downloadHashCode1 = new Download(TestUtility.getDownload());
		final Download downloadHashCode2 = new Download(downloadHashCode1);

		downloadHashCode1.setId(null);
		downloadHashCode2.setId(null);

		Assert.assertEquals(downloadHashCode1.hashCode(), downloadHashCode2.hashCode());
	}

	@Test
	public final void downloadHashCodeNullUrl() {
		final Download downloadHashCode1 = new Download(TestUtility.getDownload());
		final Download downloadHashCode2 = new Download(downloadHashCode1);

		downloadHashCode1.setUrl(null);
		downloadHashCode2.setUrl(null);

		Assert.assertEquals(downloadHashCode1.hashCode(), downloadHashCode2.hashCode());
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
	public final void downloadToString() {
		final Download downloadToString1 = new Download(TestUtility.getDownload());
		final Download downloadToString2 = new Download(downloadToString1);

		Assert.assertEquals(downloadToString1.toString(), downloadToString2.toString());
	}

	@Test
	public final void userToString() {
		final User userToString1 = new User(TestUtility.getUser());
		final User userToString2 = new User(userToString1);

		Assert.assertEquals(userToString1.toString(), userToString2.toString());
	}

	@Test
	public final void createDownloadWithConstructor() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		download.setUser(user);

		final Download downloadConstructor = new Download(download.getTitle(), download.getUrl(), download.getStartDate(), download.getEndDate(), download.getUser());
		downloadConstructor.setId(download.getId());

		Assert.assertEquals(download, downloadConstructor);
	}

	@Test
	public final void createDownloadWithConstructorNullUser() {
		final Download download = new Download(TestUtility.getDownload());

		final Download downloadConstructor = new Download(download.getTitle(), download.getUrl(), download.getStartDate(), download.getEndDate(), null);
		downloadConstructor.setId(download.getId());

		Assert.assertEquals(download, downloadConstructor);
	}

	@Test
	public final void createDownloadWithMethods() {
		final Download downloadUtility = new Download(TestUtility.getDownload());
		final Download downloadMethods = new Download();

		downloadMethods.setId(downloadUtility.getId());
		downloadMethods.setTitle(downloadUtility.getTitle());
		downloadMethods.setUrl(downloadUtility.getUrl());
		downloadMethods.setStartDate(downloadUtility.getStartDate());
		downloadMethods.setEndDate(downloadUtility.getEndDate());
		downloadMethods.setUser(downloadUtility.getUser());

		Assert.assertEquals(downloadUtility, downloadMethods);
	}

	@Test
	public final void getDownloadStartDateFormatted() {
		final Download download = new Download(TestUtility.getDownload());

		Assert.assertNotNull(download.getStartDateFormatted());
	}

	@Test
	public final void getDownloadStartDateFormattedWithNullDate() {
		final Download download = new Download(TestUtility.getDownload());
		download.setStartDate(null);

		Assert.assertNull(download.getStartDateFormatted());
	}

	@Test
	public final void getDownloadEndDateFormatted() {
		final Download download = new Download(TestUtility.getDownload());

		Assert.assertNotNull(download.getEndDateFormatted());
	}

	@Test
	public final void getDownloadEndDateFormattedWithNullDate() {
		final Download download = new Download(TestUtility.getDownload());
		download.setEndDate(null);

		Assert.assertNull(download.getEndDateFormatted());
	}

	@Test
	public final void getDownloadFilename() {
		final Download download = new Download(TestUtility.getDownload());

		Assert.assertNotNull(download.getFilename());
	}

	@Test
	public final void getDownloadFormat() {
		final Download download = new Download(TestUtility.getDownload());

		Assert.assertNotNull(download.getFormat());
	}

	@Test
	public final void createUserWithConstructor() {
		final User user = new User(TestUtility.getUser());

		final User userConstructor = new User(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getDownloads());
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

	@Test
	public final void getDownloadFromUser() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final Download retrieved = user.getDownload(download.getId());

		Assert.assertEquals(download, retrieved);
	}

	@Test
	public final void getDownloadFromUserWithEmptyDownloadList() {
		final User user = new User(TestUtility.getUser());

		final Download download = user.getDownload(1);

		Assert.assertNull(download);
	}

	@Test
	public final void getDownloadFromUserWithNonExistingIdUpperBoundary() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final Download fetched = user.getDownload(download.getId() + 1);

		Assert.assertNull(fetched);
	}

	@Test
	public final void getDownloadsFromUserWithEmptyDownloadList() {
		final User user = new User(TestUtility.getUser());

		final LinkedList<Download> downloads = user.getDownloads();

		Assert.assertEquals(0, downloads.size());
	}

	@Test(expected = NullPointerException.class)
	public final void getDownloadFromUserWithNullId() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		download.setId(null);

		user.getDownload(download.getId());
	}

	@Test
	public final void deleteDownloadFromUser() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);
		user.deleteDownload(download);

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test
	public final void deleteDownloadFromUserNull() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);
		user.deleteDownload(null);

		Assert.assertEquals(1, user.getDownloads().size());
	}

	@Test
	public final void deleteDownloadFromUserEmptyList() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.deleteDownload(download);

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test
	public final void deleteDownloadFromUserWithId() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);
		user.deleteDownload(download.getId());

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test
	public final void deleteDownloadFromUserWithMultipleDownloadsWithNonMatchingId() {
		final User user = new User(TestUtility.getUser());
		final Download download1 = new Download(TestUtility.getDownload());
		final Download download2 = new Download(TestUtility.getDownload());
		final Download download3 = new Download(TestUtility.getDownload());

		user.addDownload(download1);
		user.addDownload(download2);
		user.addDownload(download3);

		user.deleteDownload(download2.getId());
		user.deleteDownload(download3.getId() + 1);

		Assert.assertEquals(2, user.getDownloads().size());
	}

	@Test
	public final void deleteDownloadFromUserWithNonExistingIdUpperBoundary() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);
		user.deleteDownload(download.getId() + 1);

		Assert.assertEquals(1, user.getDownloads().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void deleteDownloadFromUserWithNullId() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		download.setId(null);

		user.deleteDownload(download);
	}

	// ----- UTILITY ----- //

	@Test(expected = Exception.class)
	public final void fileUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<FileUtility> constructor = FileUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = Exception.class)
	public final void languageUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<LanguageUtility> constructor = LanguageUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = Exception.class)
	public final void passwordUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<PasswordUtility> constructor = PasswordUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = Exception.class)
	public final void pathUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<ConfigUtility> constructor = ConfigUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = Exception.class)
	public final void urlUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<URLUtility> constructor = URLUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public final void getHashedPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final String password = "pass";
		final String hash = PasswordUtility.getHashedPassword(password);

		Assert.assertNotNull(hash);
	}

	@Test
	public final void validateHashedPasswordCorrect() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final String password = "pass";
		final String hash = PasswordUtility.getHashedPassword(password);

		Assert.assertTrue(PasswordUtility.validateHashedPassword(password, hash));
	}

	@Test
	public final void validateHashedPasswordIncorrect() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final String password1 = "pass1";
		final String password2 = "pass2";

		final String hash = PasswordUtility.getHashedPassword(password1);

		Assert.assertFalse(PasswordUtility.validateHashedPassword(password2, hash));
	}

	@Test
	public final void getDefaultLanguage() {
		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("language")).thenReturn(null);

		final ResourceBundle language = LanguageUtility.getLanguage(session);

		Assert.assertTrue(language.getString("languagetag").equals(LanguageUtility.getDefault().toLanguageTag()));
	}

	@Test
	public final void getDefaultLanguageWithAttributeSet() {
		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("language")).thenReturn(Locale.getDefault());

		final ResourceBundle language = LanguageUtility.getLanguage(session);

		Assert.assertTrue(language.getString("languagetag").equals(Locale.getDefault().toLanguageTag()));
	}

	// TODO finish test
	@Test(expected = RuntimeException.class)
	public final void getDefaultLanguageWithNonExistingProperties() throws IOException {
		// Steps:
		// 1. Load all the property files from the disk and save them in a list
		// 2. Delete all the property files from the disk
		// 3. Run the getLanguage(HttpSession) method; this should now throw an
		// exception as no property files can be found
		// 4. Restore all the property files by using the previous mentioned
		// list

		LinkedList<Properties> properties = new LinkedList<Properties>(FileUtility.getProperties(ConfigUtility.getPropertiesAbsolutePath()));

		throw new RuntimeException();
	}

	@Test
	public final void getNonExistingLanguage() {
		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("language")).thenReturn(Locale.forLanguageTag("fo-FO"));

		final ResourceBundle language = LanguageUtility.getLanguage(session);

		final Locale defaultLocale = Locale.forLanguageTag("en-US");

		Assert.assertTrue(language.getString("languagetag").equals(defaultLocale.toLanguageTag()));
	}

	@Test
	public final void getAllPropertyFiles() throws IOException {
		LinkedList<Properties> properties = new LinkedList<Properties>(FileUtility.getProperties(ConfigUtility.getPropertiesAbsolutePath()));

		Assert.assertNotEquals(0, properties.size());
	}

	@Test(expected = NullPointerException.class)
	public final void getAllPropertyFilesNonExistingDirectory() throws IOException {
		FileUtility.getProperties("blabla/blabla");
	}

	@Test
	public final void absolutePathIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getAbsolutePath());
	}

	@Test
	public final void absolutePathToConfigurationIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getConfigurationAbsolutePath());
	}

	@Test
	public final void absolutePathToPropertiesIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getPropertiesAbsolutePath());
	}

	@Test
	public final void absolutePathToLanguageIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getLanguageAbsolutePath());
	}

	@Test
	public final void absolutePathToDownloadsIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getDownloadsAbsolutePath());
	}

	@Test
	public final void listUrls() {
		Assert.assertNotNull(URL.values());
	}

	@Test
	public final void convertStringToEnum() {
		Assert.assertEquals(URL.INDEX, URL.valueOf("INDEX"));
	}

	@Test
	public final void getUrlWithNone() {
		Assert.assertNull(URLUtility.getUrl(URL.NONE));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void getUrlWithNullInput() {
		URLUtility.getUrl(null);
	}

	@Test(expected = RuntimeException.class)
	public final void illegalXPathExpression() {
		XMLUtility utility = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
		utility.getElementValue("!#¤%&/()=?`");
	}

	// ----- CONTROLLER ----- //

	@Test
	public final void loadLoginPageUserSet() {
		final User user = new User(TestUtility.getUser());

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		final HttpServletRequest request = mock(HttpServletRequest.class);

		session.setAttribute("user", user);

		final String path = this.accessController.loginGET(request, session);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public final void loadLoginPageUserNotSet() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);

		final String path = this.accessController.loginGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.LOGIN), path);
	}

	@Test
	public final void loadLogoutPage() {
		final HttpSession session = mock(HttpSession.class);

		final String path = this.accessController.logoutGET(session);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public final void loadRegisterPage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);

		final String path = this.accessController.registerGET(request, session);
		Assert.assertEquals(URLUtility.getUrl(URL.REGISTER), path);
	}

	@Test
	public final void registerUserWithMalformedValues() {
		final HttpSession session = mock(HttpSession.class);

		final User user = new User(TestUtility.getUser());
		user.setFirstname("");
		user.setLastname("");
		user.setEmail("");
		user.setPassword("");

		final BindingResult result = new BeanPropertyBindingResult(user, "user");

		final String path = this.accessController.registerPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.REGISTER), path);
	}

	@Test
	public final void loadIndexPage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);

		final String path = this.indexController.indexGET(request, session);
		Assert.assertEquals(URLUtility.getUrl(URL.INDEX), path);
	}

	@Test
	public final void loadLanguageWithNullReferer() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("referer")).thenReturn(null);

		final HttpSession session = mock(HttpSession.class);
		final Locale language = Locale.getDefault();

		final String path = this.languageController.languageGET(request, session, language.toLanguageTag());
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public final void loadLanguageWithExistingLanguage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("referer")).thenReturn("/downloads");

		final HttpSession session = mock(HttpSession.class);
		final Locale language = Locale.forLanguageTag("en-US");

		final String path = this.languageController.languageGET(request, session, language.toLanguageTag());
		Assert.assertEquals(URLUtility.redirect(request.getHeader("referer")), path);
	}

	@Test
	public final void loadLanguageWithNonExistingLanguage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("referer")).thenReturn("/downloads");

		final HttpSession session = mock(HttpSession.class);
		final Locale language = Locale.forLanguageTag("fo-FO");

		final String path = this.languageController.languageGET(request, session, language.toLanguageTag());
		Assert.assertEquals(URLUtility.redirect(request.getHeader("referer")), path);
	}

	@Test
	public final void loadDownloadsPage() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final HttpServletRequest request = mock(HttpServletRequest.class);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		final String path = this.downloadsController.downloadsGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.DOWNLOADS), path);
	}

	@Test
	public final void loadAddDownloadsPage() {
		final User user = new User(TestUtility.getUser());
		final HttpServletRequest request = mock(HttpServletRequest.class);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		final String path = this.downloadsController.downloadsAddGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.DOWNLOADS_ADD), path);
	}

	@Test
	public final void addDownloadWithErrors() {
		final Download download = new Download(TestUtility.getDownload());

		download.setTitle(null);
		download.setUrl(null);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);
		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		final String path = this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS_ADD), path);
	}

	// ----- VALIDATOR ----- //

	@Test(expected = ClassCastException.class)
	public final void tryToValidateNonUserObjectLoggingIn() {
		final Download download = new Download(TestUtility.getDownload());
		this.loginValidator.validate(download, null);
	}

	@Test
	public final void validateUserLoggingInWithNullValues() {
		final User user = new User(TestUtility.getUser());
		user.setEmail(null);
		user.setPassword(null);

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.loginValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
		Assert.assertTrue(result.hasFieldErrors("password"));
	}

	@Test
	public final void validateUserLoggingInWithMalformedValues() {
		final User user = new User(TestUtility.getUser());
		user.setEmail("");
		user.setPassword("");

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.loginValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
		Assert.assertTrue(result.hasFieldErrors("password"));
	}

	@Test
	public final void validateUserLoggingInSuccess() {
		final User user = new User(TestUtility.getUser());

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.loginValidator.validate(user, result);

		Assert.assertFalse(result.hasErrors());
	}

	@Test(expected = ClassCastException.class)
	public final void tryToValidateNonUserObjectRegistering() {
		final Download download = new Download(TestUtility.getDownload());
		this.registerValidator.validate(download, null);
	}

	@Test
	public final void validateUserRegisteringExistingEmail() {
		final User user = new User(TestUtility.getUser());

		final User attempt = this.registerService.save(user);

		final BindingResult result = new BeanPropertyBindingResult(attempt, "user");
		this.registerValidator.validate(attempt, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
	}

	@Test
	public final void validateUserRegisteringWithMalformedFirstnameAndLastname() {
		final User user = new User(TestUtility.getUser());
		user.setFirstname(null);
		user.setLastname(null);

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.registerValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("firstname"));
		Assert.assertTrue(result.hasFieldErrors("lastname"));
	}

	@Test
	public final void validateUserRegisteringWithMalformedEmail() {
		final User user = new User(TestUtility.getUser());
		user.setEmail("abc@abc");

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.registerValidator.validate(user, result);

		Assert.assertTrue(result.hasFieldErrors("email"));
	}

	@Test(expected = ClassCastException.class)
	public final void tryToValidateNonDownloadObject() {
		final User user = new User(TestUtility.getUser());
		this.downloadValidator.validate(user, null);
	}

	@Test
	public final void validateDownloadWithMalformedTitleAndUrl() {
		final Download download = new Download(TestUtility.getDownload());
		download.setTitle(null);
		download.setUrl(null);

		final BindingResult result = new BeanPropertyBindingResult(download, "download");
		this.downloadValidator.validate(download, result);

		Assert.assertTrue(result.hasFieldErrors("title"));
		Assert.assertTrue(result.hasFieldErrors("url"));
	}

	@Test
	public final void validateDownloadSuccess() {
		final Download download = new Download(TestUtility.getDownload());

		final BindingResult result = new BeanPropertyBindingResult(download, "download");
		this.downloadValidator.validate(download, result);

		Assert.assertFalse(result.hasErrors());
	}

	// ----- FILTER ----- //

	@Test
	public final void charsetFilterWithNullCharacterEncoding() throws IOException, ServletException {
		final ServletRequest request = mock(ServletRequest.class);
		final ServletResponse response = mock(ServletResponse.class);
		final FilterChain chain = mock(FilterChain.class);

		final FilterConfig config = mock(FilterConfig.class);

		final CharsetFilter filter = new CharsetFilter();

		filter.init(config);
		filter.doFilter(request, response, chain);

		Assert.assertEquals("UTF-8", filter.getEncoding());
	}

	@Test
	public final void charsetFilterWithLatin1CharacterEncoding() throws IOException, ServletException {
		final ServletRequest request = mock(ServletRequest.class);
		when(request.getCharacterEncoding()).thenReturn("latin1");

		final ServletResponse response = mock(ServletResponse.class);
		final FilterChain chain = mock(FilterChain.class);

		final FilterConfig config = mock(FilterConfig.class);
		when(config.getInitParameter("requestEncoding")).thenReturn("latin1");

		final CharsetFilter filter = new CharsetFilter();

		filter.init(config);
		filter.doFilter(request, response, chain);

		Assert.assertEquals("latin1", filter.getEncoding());
	}

	@Test
	public final void charsetFilterDestroy() {
		final CharsetFilter filter = new CharsetFilter();
		filter.destroy();

		Assert.assertNull(filter.getEncoding());
	}

	// ----- INTERCEPTOR ----- //

	@Test
	public final void preHandleOnAccess() throws Exception {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = new Object();

		final boolean result = this.accessInterceptor.preHandle(request, response, handler);

		Assert.assertTrue(result);
	}

	@Test
	public final void postHandleOnAccess() throws Exception {
		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = new Object();
		final ModelAndView modelAndView = new ModelAndView();

		this.accessInterceptor.postHandle(request, response, handler, modelAndView);
	}

	@Test
	public final void userAttributeIsNullAtInterceptor() throws Exception {
		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("user")).thenReturn(null);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final boolean result = this.downloadsInterceptor.preHandle(request, response, null);

		Assert.assertFalse(result);
	}

	@Test
	public final void userAttributeIsNotNullAtInterceptor() throws Exception {
		final User user = new User(TestUtility.getUser());

		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("user")).thenReturn(user);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final boolean result = this.downloadsInterceptor.preHandle(request, response, null);

		Assert.assertTrue(result);
	}

	@Test
	public final void postHandleOnDownloads() throws Exception {
		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = new Object();
		final ModelAndView modelAndView = new ModelAndView();

		this.downloadsInterceptor.postHandle(request, response, handler, modelAndView);
	}

}
