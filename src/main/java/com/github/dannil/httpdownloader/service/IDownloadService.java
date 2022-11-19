package com.github.dannil.httpdownloader.service;

import java.io.IOException;
import java.util.List;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface for DownloadService.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface IDownloadService {

    // Others, defined in DownloadRepository
    Download findById(long downloadId);

    List<Download> findByUser(User user);

    Download save(Download download);

    void delete(Download download);

    void delete(long downloadId);

    // Delegated to DownloadService
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
