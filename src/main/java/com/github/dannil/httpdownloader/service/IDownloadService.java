package com.github.dannil.httpdownloader.service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for DownloadService.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface IDownloadService {

    /**
     * Find a download by it's ID.
     *
     * @param downloadId the ID of the download
     * @return the download with the specified ID
     */
    Download findById(long downloadId);

    /**
     * Find a download by it's related user.
     *
     * @param user the related user of the download
     * @return the download
     */
    List<Download> findByUser(User user);

    /**
     * Persist a download.
     *
     * @param download the download
     * @return the download
     */
    Download save(Download download);

    /**
     * Delete a download.
     *
     * @param download the download
     */
    void delete(Download download);

    /**
     * Delete a download by it's ID.
     *
     * @param downloadId the ID of the download
     */
    void delete(long downloadId);

    /**
     * Initiate the specified download and save it to the disk.
     *
     * @param download
     *            the download to save
     *
     * @return the saved download
     */
    Download saveToDisk(Download download);

    /**
     * Display a download dialog to the user.
     *
     * @param context
     *            the current servlet context
     * @param response
     *            the response to serve the dialog to
     * @param download
     *            the download to serve
     *
     * @throws IOException
     *             if the download for some reason can't be found
     */
    void serveDownload(ServletContext context, HttpServletResponse response, Download download) throws IOException;

}
