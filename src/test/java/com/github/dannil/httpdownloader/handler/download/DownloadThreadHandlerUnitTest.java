package com.github.dannil.httpdownloader.handler.download;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.FileUtility;

/**
 * Unit tests for download thread handler
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class DownloadThreadHandlerUnitTest {

    @Autowired
    private DownloadThreadHandler downloadThreadHandler;

    @Test
    public void saveToDiskNotNullDownload() throws InterruptedException {
        Download download = TestUtility.getDownload();

        this.downloadThreadHandler.saveToDisk(download);

        // Make sure the thread started by downloadThreadHandler finish
        // executing before asserting
        TimeUnit.SECONDS.sleep(1);

        assertTrue(FileUtility.getFromDrive(download).exists());
    }

    @Test
    public void saveToDiskNullDownload() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.downloadThreadHandler.saveToDisk(null);
        });
    }

    @Test
    public void saveToDiskInvalidUrl() {
        Download download = TestUtility.getDownload();
        download.setUrl("blabla/blabla");

        this.downloadThreadHandler.saveToDisk(download);
    }

    @Test
    public void deleteFromDiskNotNullDownload() throws IOException, InterruptedException {
        Download download = TestUtility.getDownload();

        FileUtility.getFileFromURL(download);

        this.downloadThreadHandler.deleteFromDisk(download);

        // Make sure the thread started by downloadThreadHandler finish
        // executing before asserting
        TimeUnit.SECONDS.sleep(1);

        assertFalse(FileUtility.getFromDrive(download).exists());
    }

    @Test
    public void deleteFromDiskNullDownload() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.downloadThreadHandler.deleteFromDisk(null);
        });
    }

    @Test
    public void initializeHandlerTwice() {
        DownloadThreadHandler handler1 = DownloadThreadHandler.getInstance();
        DownloadThreadHandler handler2 = DownloadThreadHandler.getInstance();

        assertEquals(handler1, handler2);
    }

}
