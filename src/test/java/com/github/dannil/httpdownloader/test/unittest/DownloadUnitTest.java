package com.github.dannil.httpdownloader.test.unittest;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for download
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadUnitTest {

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
	public final void downloadToString() {
		final Download downloadToString1 = new Download(TestUtility.getDownload());
		final Download downloadToString2 = new Download(downloadToString1);

		Assert.assertEquals(downloadToString1.toString(), downloadToString2.toString());
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

}
