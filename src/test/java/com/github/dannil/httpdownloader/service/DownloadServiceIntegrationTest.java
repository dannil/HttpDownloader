package com.github.dannil.httpdownloader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.ConfigUtility;

/**
 * Integration tests for download service
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class DownloadServiceIntegrationTest {

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private IDownloadService downloadService;

	@Test
	public void findDownloadById() {
		Download download = TestUtility.getDownload();
		Download registered = this.downloadService.save(download);

		Download find = this.downloadService.findById(registered.getId());

		Assert.assertNotEquals(null, find);
	}

	@Test
	public void findDownloadsByUser() {
		Download download = TestUtility.getDownload();

		User user = TestUtility.getUser();
		User registered = this.registerService.save(user);

		download.setUser(registered);

		this.downloadService.save(download);

		List<Download> result = this.downloadService.findByUser(registered);

		Assert.assertEquals(1, result.size());
	}

	@Test
	public void startDownload() throws IOException {
		Download download = TestUtility.getDownload();

		User user = TestUtility.getUser();
		User registered = this.registerService.save(user);

		download.setUser(registered);

		Download saved = this.downloadService.save(download);

		registered.addDownload(saved);

		Assert.assertNotNull(saved.getStartDate());
	}

	@Test
	public void saveDownloadToDisk() throws InterruptedException {
		Download download = TestUtility.getDownload();

		Download saved = this.downloadService.saveToDisk(download);

		TimeUnit.SECONDS.sleep(1);

		Assert.assertNotEquals(null, saved);
	}

	@Test(expected = FileNotFoundException.class)
	public void deleteDownloadFromDisk() throws InterruptedException, IOException {
		Download download = TestUtility.getDownload();

		Download saved = this.downloadService.saveToDisk(download);

		TimeUnit.SECONDS.sleep(2);

		this.downloadService.delete(saved);

		TimeUnit.SECONDS.sleep(2);

		File file = new File(ConfigUtility.getDownloadsAbsolutePath() + "/" + saved.getFormat());
		try (FileInputStream stream = new FileInputStream(file)) {
			Assert.assertNull(stream);
		}
	}

	@Test
	public void deleteDownload() {
		Download download = TestUtility.getDownload();

		Download registered = this.downloadService.save(download);

		this.downloadService.delete(registered.getId());

		Assert.assertEquals(null, this.downloadService.findById(registered.getId()));
	}

}
