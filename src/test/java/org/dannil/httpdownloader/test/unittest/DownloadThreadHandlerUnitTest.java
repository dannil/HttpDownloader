package org.dannil.httpdownloader.test.unittest;

import java.io.File;
import java.io.IOException;

import org.dannil.httpdownloader.handler.DownloadThreadHandler;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.FileUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for download thread handler
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadThreadHandlerUnitTest {

	@Autowired
	private DownloadThreadHandler downloadThreadHandler;

	// DOES NOT CURRENTLY WORK; NEEDS REFACTORING

	@Test
	public final void saveToDiskNotNullDownload() {
		final Download download = new Download(TestUtility.getDownload());

		this.downloadThreadHandler.saveToDisk(download);
	}

	@Test(expected = NullPointerException.class)
	public final void saveToDiskNullDownload() {
		this.downloadThreadHandler.saveToDisk(null);
	}

	// WORKS
	@Test
	public final void deleteFromDiskNotNullDownload() throws IOException {
		final Download download = new Download(TestUtility.getDownload());

		File file = FileUtility.getFileFromURL(download);
		FileUtility.saveToDrive(file);

		this.downloadThreadHandler.deleteFromDisk(download);
	}

	@Test(expected = NullPointerException.class)
	public final void deleteFromDiskNullDownload() {
		this.downloadThreadHandler.deleteFromDisk(null);
	}

	@Test
	public final void initializeHandlerTwice() {
		final DownloadThreadHandler handler1 = new DownloadThreadHandler();
		final DownloadThreadHandler handler2 = new DownloadThreadHandler();
	}

}
