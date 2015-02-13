package org.dannil.httpdownloader.test.unittest;

import org.dannil.httpdownloader.handler.DownloadThreadHandler;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.test.utility.TestUtility;
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
	public final void threadHandlerSaveToDiskNotNullDownload() {
		final Download download = new Download(TestUtility.getDownload());

		this.downloadThreadHandler.saveToDisk(download);
	}

	@Test(expected = NullPointerException.class)
	public final void threadHandlerSaveToDiskNullDownload() {
		this.downloadThreadHandler.saveToDisk(null);
	}

	@Test
	public final void threadHandlerDeleteFromDiskNotNullDownload() {
		final Download download = new Download(TestUtility.getDownload());

		this.downloadThreadHandler.deleteFromDisk(download);
	}

	@Test(expected = NullPointerException.class)
	public final void threadHandlerDeleteFromDiskNullDownload() {
		this.downloadThreadHandler.deleteFromDisk(null);
	}

}
