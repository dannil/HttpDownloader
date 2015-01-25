package org.dannil.httpdownloader.test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/conf/xml/spring-context.xml")
public final class UnitTest {

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private UserRepository userRepository;

	@Test
	public final void downloadEquals() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		Assert.assertEquals(true, downloadEquals1.equals(downloadEquals2));
	}

	@Test
	public final void userEquals() {
		final User userEquals1 = new User(TestUtility.getUser());
		final User userEquals2 = new User(userEquals1);

		Assert.assertEquals(true, userEquals1.equals(userEquals2));
	}

	@Test
	public final void downloadHashCode() {
		final Download downloadEquals1 = new Download(TestUtility.getDownload());
		final Download downloadEquals2 = new Download(downloadEquals1);

		Assert.assertEquals(true, downloadEquals1.hashCode() == downloadEquals2.hashCode());
	}

	@Test
	public final void downloadHashCodeNullId() {
		final Download downloadHashCode1 = new Download(TestUtility.getDownload());
		final Download downloadHashCode2 = new Download(downloadHashCode1);

		downloadHashCode1.setId(null);
		downloadHashCode2.setId(null);

		Assert.assertEquals(true, downloadHashCode1.hashCode() == downloadHashCode2.hashCode());
	}

	@Test
	public final void downloadHashCodeNullUrl() {
		final Download downloadHashCode1 = new Download(TestUtility.getDownload());
		final Download downloadHashCode2 = new Download(downloadHashCode1);

		downloadHashCode1.setUrl(null);
		downloadHashCode2.setUrl(null);

		Assert.assertEquals(true, downloadHashCode1.hashCode() == downloadHashCode2.hashCode());
	}

	@Test
	public final void userHashCode() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullId() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setId(null);
		userHashCode2.setId(null);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullEmail() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setEmail(null);
		userHashCode2.setEmail(null);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullPassword() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setPassword(null);
		userHashCode2.setPassword(null);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullFirstname() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setFirstname(null);
		userHashCode2.setFirstname(null);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullLastname() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setLastname(null);
		userHashCode2.setLastname(null);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
	}

	@Test
	public final void userHashCodeNullDownloads() {
		final User userHashCode1 = new User(TestUtility.getUser());
		final User userHashCode2 = new User(userHashCode1);

		userHashCode1.setDownloads(null);
		userHashCode2.setDownloads(null);

		Assert.assertEquals(true, userHashCode1.hashCode() == userHashCode2.hashCode());
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
	public final void getDownloadFilename() {
		final Download download = new Download(TestUtility.getDownload());

		Assert.assertNotEquals(null, download.getFilename());
	}

	@Test
	public final void getDownloadFormat() {
		final Download download = new Download(TestUtility.getDownload());

		Assert.assertNotEquals(null, download.getFormat());
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

		Assert.assertEquals(null, download);
	}

	@Test
	public final void getDownloadFromUserWithNonExistingIdUpperBoundary() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final Download fetched = user.getDownload(download.getId() + 1);

		Assert.assertEquals(null, fetched);
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
	}

	@Test
	public final void deleteDownloadFromUserEmptyList() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.deleteDownload(download);
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
	public final void deleteDownloadFromUserWithNonExistingIdUpperBoundary() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);
		user.deleteDownload(download.getId() + 1);
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

	@Test
	public final void getHashedPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final String password = "pass";
		final String hash = PasswordUtility.getHashedPassword(password);

		Assert.assertNotEquals(null, hash);
	}

	@Test
	public final void validateHashedPasswordCorrect() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final String password = "pass";
		final String hash = PasswordUtility.getHashedPassword(password);

		Assert.assertEquals(true, PasswordUtility.validateHashedPassword(password, hash));
	}

	@Test
	public final void validateHashedPasswordIncorrect() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		final String password1 = "pass1";
		final String password2 = "pass2";

		final String hash = PasswordUtility.getHashedPassword(password1);

		Assert.assertEquals(false, PasswordUtility.validateHashedPassword(password2, hash));
	}

}
