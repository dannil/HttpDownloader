package org.dannil.httpdownloader.test.integrationtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.ConfigUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration tests for download service
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadServiceIntegrationTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private IDownloadService downloadService;

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
	public final void startDownload() throws IOException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User registered = this.registerService.save(user);

		download.setUser(registered);

		final Download saved = this.downloadService.save(download);

		registered.addDownload(saved);

		Assert.assertNotNull(registered.getDownloads().get(0).getStartDate());
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

		File file = new File(ConfigUtility.getDownloadsAbsolutePath() + "/" + saved.getFormat());
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

}
