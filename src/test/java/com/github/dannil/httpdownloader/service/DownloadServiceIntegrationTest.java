package com.github.dannil.httpdownloader.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.ConfigUtility;

/**
 * Integration tests for download service
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
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

        assertNotEquals(null, find);
    }

    @Test
    public void findDownloadsByUser() {
        Download download = TestUtility.getDownload();

        User user = TestUtility.getUser();
        User registered = this.registerService.save(user);

        download.setUser(registered);

        this.downloadService.save(download);

        List<Download> result = this.downloadService.findByUser(registered);

        assertEquals(1, result.size());
    }

    @Test
    public void startDownload() throws IOException {
        Download download = TestUtility.getDownload();

        User user = TestUtility.getUser();
        User registered = this.registerService.save(user);

        download.setUser(registered);

        Download saved = this.downloadService.save(download);

        registered.addDownload(saved);

        assertNotNull(saved.getStartDate());
    }

    @Test
    public void saveDownloadToDisk() throws InterruptedException {
        Download download = TestUtility.getDownload();

        Download saved = this.downloadService.saveToDisk(download);

        TimeUnit.SECONDS.sleep(1);

        assertNotEquals(null, saved);
    }

    @Test
    public void deleteDownloadFromDisk() throws InterruptedException, IOException {
        Download download = TestUtility.getDownload();

        Download saved = this.downloadService.saveToDisk(download);

        TimeUnit.SECONDS.sleep(2);

        this.downloadService.delete(saved);

        TimeUnit.SECONDS.sleep(2);

        File file = new File(ConfigUtility.getDownloadsAbsolutePath() + "/" + saved.getFormat());
        assertThrows(FileNotFoundException.class, () -> {
            try (FileInputStream stream = new FileInputStream(file)) {
                assertNull(stream);
            }
        });
    }

    @Test
    public void deleteDownload() {
        Download download = TestUtility.getDownload();

        Download registered = this.downloadService.save(download);

        this.downloadService.delete(registered.getId());

        assertEquals(null, this.downloadService.findById(registered.getId()));
    }

}
