package org.dannil.httpdownloader.test;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.TestUtility;
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
	public final void addDownloadToUser() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		Assert.assertEquals(1, user.getDownloads().size());
	}

	@Test(expected = IllegalStateException.class)
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
	public final void deleteDownloadFromUserWithId() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);
		user.deleteDownload(download.getId());

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test(expected = IllegalStateException.class)
	public final void deleteDownloadFromUserWithNullId() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		download.setId(null);

		user.deleteDownload(download);
	}

}
