package com.github.dannil.httpdownloader.handler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.FileUtility;

/**
 * Unit tests for download thread handler
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
        "classpath:/WEB-INF/configuration/framework/application-context.xml" })
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

        Assert.assertTrue(FileUtility.getFromDrive(download).exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveToDiskNullDownload() {
        this.downloadThreadHandler.saveToDisk(null);
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

        File file = FileUtility.getFileFromURL(download);
        FileUtility.saveToDrive(file);

        this.downloadThreadHandler.deleteFromDisk(download);

        // Make sure the thread started by downloadThreadHandler finish
        // executing before asserting
        TimeUnit.SECONDS.sleep(1);

        Assert.assertFalse(FileUtility.getFromDrive(download).exists());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteFromDiskNullDownload() {
        this.downloadThreadHandler.deleteFromDisk(null);
    }

    @Test
    public void initializeHandlerTwice() {
        DownloadThreadHandler handler1 = DownloadThreadHandler.getInstance();
        DownloadThreadHandler handler2 = DownloadThreadHandler.getInstance();

        Assert.assertEquals(handler1, handler2);
    }

}
