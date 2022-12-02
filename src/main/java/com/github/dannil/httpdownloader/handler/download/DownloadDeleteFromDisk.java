package com.github.dannil.httpdownloader.handler.download;

import com.github.dannil.httpdownloader.exception.DownloadException;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.utility.FileUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Asynchronous runner for deleting downloads from disk.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 2.0.0-SNAPSHOT
 */
@Component
final class DownloadDeleteFromDisk implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadDeleteFromDisk.class.getName());

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

