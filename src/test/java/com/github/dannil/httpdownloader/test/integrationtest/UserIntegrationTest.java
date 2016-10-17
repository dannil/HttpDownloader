package com.github.dannil.httpdownloader.test.integrationtest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Integration tests for user. Most, if not all, of the operations in this test file
 * is related to the manipulation of downloads on a user.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class UserIntegrationTest {

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

		final List<Download> downloads = user.getDownloads();

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

}
