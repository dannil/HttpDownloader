package com.github.dannil.httpdownloader.model;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Integration tests for user. Most, if not all, of the operations in this test file is
 * related to the manipulation of downloads on a user.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class UserIntegrationTest {

	@Test
	public void getDownloadFromUser() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);

		Download retrieved = user.getDownload(download.getId());

		Assert.assertEquals(download, retrieved);
	}

	@Test
	public void getDownloadFromUserWithEmptyDownloadList() {
		User user = TestUtility.getUser();

		Download download = user.getDownload(1);

		Assert.assertNull(download);
	}

	@Test
	public void getDownloadFromUserWithNonExistingIdUpperBoundary() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);

		Download fetched = user.getDownload(download.getId() + 1);

		Assert.assertNull(fetched);
	}

	@Test
	public void getDownloadsFromUserWithEmptyDownloadList() {
		User user = TestUtility.getUser();

		Collection<Download> downloads = user.getDownloads();

		Assert.assertEquals(0, downloads.size());
	}

	@Test(expected = NullPointerException.class)
	public void getDownloadFromUserWithNullId() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);

		download.setId(null);

		user.getDownload(download.getId());
	}

	@Test
	public void deleteDownloadFromUser() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);
		user.deleteDownload(download);

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test
	public void deleteDownloadFromUserNull() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);
		user.deleteDownload(null);

		Assert.assertEquals(1, user.getDownloads().size());
	}

	@Test
	public void deleteDownloadFromUserEmptyList() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.deleteDownload(download);

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test
	public void deleteDownloadFromUserWithId() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);
		user.deleteDownload(download.getId());

		Assert.assertEquals(0, user.getDownloads().size());
	}

	@Test
	public void deleteDownloadFromUserWithMultipleDownloadsWithNonMatchingId() {
		User user = TestUtility.getUser();
		Download download1 = TestUtility.getDownload();
		Download download2 = TestUtility.getDownload();
		Download download3 = TestUtility.getDownload();

		user.addDownload(download1);
		user.addDownload(download2);
		user.addDownload(download3);

		user.deleteDownload(download2.getId());
		user.deleteDownload(download3.getId() + 1);

		Assert.assertEquals(2, user.getDownloads().size());
	}

	@Test
	public void deleteDownloadFromUserWithNonExistingIdUpperBoundary() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);
		user.deleteDownload(download.getId() + 1);

		Assert.assertEquals(1, user.getDownloads().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteDownloadFromUserWithNullId() {
		User user = TestUtility.getUser();
		Download download = TestUtility.getDownload();

		user.addDownload(download);

		download.setId(null);

		user.deleteDownload(download);
	}

}
