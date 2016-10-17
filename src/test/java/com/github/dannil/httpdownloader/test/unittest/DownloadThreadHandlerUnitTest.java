package com.github.dannil.httpdownloader.test.unittest;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.handler.DownloadThreadHandler;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.FileUtility;

/**
 * Unit tests for download thread handler
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadThreadHandlerUnitTest {

	@Autowired
	private DownloadThreadHandler downloadThreadHandler;

	@Test
	public final void saveToDiskNotNullDownload() throws InterruptedException {
		final Download download = new Download(TestUtility.getDownload());

		this.downloadThreadHandler.saveToDisk(download);

		// Make sure the thread started by downloadThreadHandler finish
		// executing before asserting
		Thread.sleep(500);

		Assert.assertTrue(FileUtility.getFromDrive(download).exists());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void saveToDiskNullDownload() {
		this.downloadThreadHandler.saveToDisk(null);
	}

	@Test
	public final void saveToDiskInvalidUrl() {
		final Download download = new Download(TestUtility.getDownload());
		download.setUrl("blabla/blabla");

		this.downloadThreadHandler.saveToDisk(download);
	}

	@Test
	public final void deleteFromDiskNotNullDownload() throws IOException, InterruptedException {
		final Download download = new Download(TestUtility.getDownload());

		File file = FileUtility.getFileFromURL(download);
		FileUtility.saveToDrive(file);

		this.downloadThreadHandler.deleteFromDisk(download);

		// Make sure the thread started by downloadThreadHandler finish
		// executing before asserting
		Thread.sleep(500);

		Assert.assertFalse(FileUtility.getFromDrive(download).exists());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void deleteFromDiskNullDownload() {
		this.downloadThreadHandler.deleteFromDisk(null);
	}

	@Test
	public final void initializeHandlerTwice() {
		final DownloadThreadHandler handler1 = DownloadThreadHandler.getInstance();
		final DownloadThreadHandler handler2 = DownloadThreadHandler.getInstance();

		Assert.assertEquals(handler1, handler2);
	}

}
