package com.github.dannil.httpdownloader.handler.download;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.repository.DownloadRepository;
import com.github.dannil.httpdownloader.utility.FileUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Asynchronous runner for saving downloads to disk.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 2.0.0-SNAPSHOT
 */
@Component
final class DownloadSaveToDisk implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadSaveToDisk.class.getName());

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
            //FileUtility.saveToDrive(file);
        } catch (IOException e) {
            LOGGER.error("Error while saving file to drive", e);
        }

        this.download.setEndDate(LocalDateTime.now());
        this.repository.save(this.download);
    }

    public void setDownload(final Download download) {
        this.download = download;
    }

}
