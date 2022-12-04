package com.github.dannil.httpdownloader.handler.download;

import java.util.ArrayList;
import java.util.List;

import com.github.dannil.httpdownloader.model.Download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Middleware class that acts between the service layer and the persistence layer. This
 * class makes sure that several downloads can be initiated at once, each one in a
 * separate thread.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@Component
public final class DownloadThreadHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadThreadHandler.class.getName());

    private static DownloadThreadHandler downloadThreadHandlerInstance;

    private List<Thread> threads;

    @Autowired
    private DownloadSaveToDisk saveToDiskInstance;

    @Autowired
    private DownloadDeleteFromDisk deleteFromDiskInstance;

    private DownloadThreadHandler() {
        this.threads = new ArrayList<>();
    }

    /**
     * Get the instance of the singleton.
     *
     * @return the instance of the singleton
     */
    public static synchronized DownloadThreadHandler getInstance() {
        if (downloadThreadHandlerInstance == null) {
            downloadThreadHandlerInstance = new DownloadThreadHandler();
        }
        return downloadThreadHandlerInstance;
    }

    /**
     * Saves the download to disk.
     *
     * @param download the download
     */
    public synchronized void saveToDisk(Download download) {
        if (download == null) {
            throw new IllegalArgumentException("Download can't be null");
        }

        this.saveToDiskInstance.setDownload(download);

        Thread t = new Thread(this.saveToDiskInstance, download.getFormat());

        this.threads.add(t);

        t.start();
    }

    /**
     * Deletes the download from disk.
     *
     * @param download the download
     */
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
     * @param threadName the name of the thread to interrupt
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
