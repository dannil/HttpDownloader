package com.github.dannil.httpdownloader.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;

import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for download
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class DownloadUnitTest {

    @Test
    public void createDownloadWithConstructor() {
        Download download = TestUtility.getDownload();
        User user = TestUtility.getUser();

        download.setUser(user);

        Download downloadConstructor = new Download(download.getTitle(), download.getUrl(), download.getStartDate(),
                download.getEndDate(), download.getUser());
        downloadConstructor.setId(download.getId());

        Assert.assertEquals(download, downloadConstructor);
    }

    @Test
    public void createDownloadWithConstructorNullUser() {
        Download download = TestUtility.getDownload();

        Download downloadConstructor = new Download(download.getTitle(), download.getUrl(), download.getStartDate(),
                download.getEndDate(), null);
        downloadConstructor.setId(download.getId());

        Assert.assertEquals(download, downloadConstructor);
    }

    @Test
    public void createDownloadWithMethods() {
        Download downloadUtility = TestUtility.getDownload();
        Download downloadMethods = new Download(downloadUtility.getTitle(), downloadUtility.getUrl());

        downloadMethods.setId(downloadUtility.getId());
        downloadMethods.setStartDate(downloadUtility.getStartDate());
        downloadMethods.setEndDate(downloadUtility.getEndDate());
        downloadMethods.setUser(downloadUtility.getUser());

        Assert.assertEquals(downloadUtility, downloadMethods);
    }

    @Test
    public void downloadEquals() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadEqualsItself() {
        Download downloadEquals1 = TestUtility.getDownload();

        Assert.assertTrue(downloadEquals1.equals(downloadEquals1));
    }

    @Test
    public void downloadNotEqualsWithNull() {
        Download downloadEquals1 = TestUtility.getDownload();

        Assert.assertFalse(downloadEquals1.equals(null));
    }

    @Test
    public void downloadNotEqualsWithIncompatibleObject() {
        Download downloadEquals1 = TestUtility.getDownload();
        User userEquals1 = TestUtility.getUser();

        Assert.assertFalse(downloadEquals1.equals(userEquals1));
    }

    @Test
    public void downloadNotEqualsOnTitle() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals2.setTitle(downloadEquals1.getTitle() + "a");

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnNullTitle() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setTitle(null);

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadEqualsOnBothNullTitle() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setTitle(null);
        downloadEquals2.setTitle(null);

        Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnUrl() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals2.setUrl(downloadEquals1.getUrl() + "a");

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnNullUrl() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setUrl(null);

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadEqualsOnBothNullUrl() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setUrl(null);
        downloadEquals2.setUrl(null);

        Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnStartDate() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals2.setStartDate(LocalDateTime.MAX);

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnNullStartDate() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setStartDate(null);

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadEqualsOnBothNullStartDate() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setStartDate(null);
        downloadEquals2.setStartDate(null);

        Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnEndDate() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals2.setStartDate(LocalDateTime.MAX);

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadNotEqualsOnNullEndDate() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setEndDate(null);

        Assert.assertFalse(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadEqualsOnBothNullEndDate() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        downloadEquals1.setEndDate(null);
        downloadEquals2.setEndDate(null);

        Assert.assertTrue(downloadEquals1.equals(downloadEquals2));
    }

    @Test
    public void downloadHashCode() {
        Download downloadEquals1 = TestUtility.getDownload();
        Download downloadEquals2 = TestUtility.deepCopy(downloadEquals1);

        Assert.assertEquals(downloadEquals1.hashCode(), downloadEquals2.hashCode());
    }

    @Test
    public void downloadHashCodeNullId() {
        Download downloadHashCode1 = TestUtility.getDownload();
        Download downloadHashCode2 = TestUtility.deepCopy(downloadHashCode1);

        downloadHashCode1.setId(null);
        downloadHashCode2.setId(null);

        Assert.assertEquals(downloadHashCode1.hashCode(), downloadHashCode2.hashCode());
    }

    @Test
    public void downloadHashCodeNullUrl() {
        Download downloadHashCode1 = TestUtility.getDownload();
        Download downloadHashCode2 = TestUtility.deepCopy(downloadHashCode1);

        downloadHashCode1.setUrl(null);
        downloadHashCode2.setUrl(null);

        Assert.assertEquals(downloadHashCode1.hashCode(), downloadHashCode2.hashCode());
    }

    @Test
    public void downloadToString() {
        Download downloadToString1 = TestUtility.getDownload();
        Download downloadToString2 = TestUtility.deepCopy(downloadToString1);

        Assert.assertEquals(downloadToString1.toString(), downloadToString2.toString());
    }

    @Test
    public void getDownloadStartDateFormatted() {
        Download download = TestUtility.getDownload();

        Assert.assertNotNull(download.getStartDateFormatted());
    }

    @Test
    public void getDownloadStartDateFormattedWithNullDate() {
        Download download = TestUtility.getDownload();
        download.setStartDate(null);

        Assert.assertNull(download.getStartDateFormatted());
    }

    @Test
    public void getDownloadEndDateFormatted() {
        Download download = TestUtility.getDownload();

        Assert.assertNotNull(download.getEndDateFormatted());
    }

    @Test
    public void getDownloadEndDateFormattedWithNullDate() {
        Download download = TestUtility.getDownload();
        download.setEndDate(null);

        Assert.assertNull(download.getEndDateFormatted());
    }

    @Test
    public void getDownloadFilename() {
        Download download = TestUtility.getDownload();

        Assert.assertNotNull(download.getFilename());
    }

    @Test
    public void getDownloadFormat() {
        Download download = TestUtility.getDownload();

        Assert.assertNotNull(download.getFormat());
    }

}
