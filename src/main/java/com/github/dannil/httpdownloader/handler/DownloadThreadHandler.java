package com.github.dannil.httpdownloader.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.dannil.httpdownloader.exception.DownloadException;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.repository.DownloadRepository;
import com.github.dannil.httpdownloader.utility.FileUtility;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Middleware class that acts between the service layer and the persistence layer. This
 * class makes sure that several downloads can be initiated at once, each one in a
 * separate thread.
 *
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@Component
public class DownloadThreadHandler {

    private static final Logger LOGGER = Logger.getLogger(DownloadThreadHandler.class.getName());

    private static DownloadThreadHandler downloadThreadHandlerInstance;

    private List<Thread> threads;

    @Autowired
    private DownloadSaveToDisk saveToDiskInstance;

    @Autowired
    private DownloadDeleteFromDisk deleteFromDiskInstance;

    private DownloadThreadHandler() {
        this.threads = new ArrayList<>();
    }

    public static synchronized DownloadThreadHandler getInstance() {
        if (downloadThreadHandlerInstance == null) {
            downloadThreadHandlerInstance = new DownloadThreadHandler();
        }
        return downloadThreadHandlerInstance;
    }

    public synchronized void saveToDisk(Download download) {
        if (download == null) {
            throw new IllegalArgumentException("Download can't be null");
        }

        this.saveToDiskInstance.setDownload(download);

        Thread t = new Thread(this.saveToDiskInstance, download.getFormat());

        this.threads.add(t);

        t.start();
    }

    public synchronized void deleteFromDisk(Download download) {
        if (download == null) {
            throw new IllegalArgumentException("Download can't be null");
        }

        this.deleteFromDiskInstance.setDownload(download);

        Thread t = new Thread(this.deleteFromDiskInstance, download.getFormat());

        this.threads.add(t);

        t.start();
    }

    /**
     * Interrupts a thread with the specified name.
     *
     * @param threadName
     *            the name of the thread to interrupt
     */
    public synchronized void interrupt(String threadName) {
        for (Thread t : this.threads) {
            if (t.getName().equals(threadName)) {
                LOGGER.info("Found thread " + threadName + ", interrupting...");
                t.interrupt();

                this.threads.remove(t);
                break;
            }
        }
    }

}

@Component
class DownloadSaveToDisk implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(DownloadSaveToDisk.class.getName());

    @Autowired
    private DownloadRepository repository;

    private Download download;

    private DownloadSaveToDisk() {

    }

    @Override
    public void run() {
        LOGGER.info("Trying to save download " + this.download.getFormat());

        File file;
        try {
            file = FileUtility.getFileFromURL(this.download);
            FileUtility.saveToDrive(file);
        } catch (IOException e) {
            LOGGER.error("Error while saving file to drive", e);
        }

        this.download.setEndDate(new DateTime());
        this.repository.save(this.download);
    }

    public void setDownload(final Download download) {
        this.download = download;
    }

}

@Component
class DownloadDeleteFromDisk implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(DownloadSaveToDisk.class.getName());

    private Download download;

    private DownloadDeleteFromDisk() {

    }

    @Override
    public void run() {
        LOGGER.info("Trying to delete download " + this.download.getFormat());

        boolean isDeleted = FileUtility.deleteFromDrive(this.download);
        if (!isDeleted) {
            throw new DownloadException("Couldn't delete download " + this.download);
        }
    }

    public void setDownload(final Download download) {
        this.download = download;
    }

}