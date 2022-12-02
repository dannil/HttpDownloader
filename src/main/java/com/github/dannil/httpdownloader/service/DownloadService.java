package com.github.dannil.httpdownloader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

import com.github.dannil.httpdownloader.handler.download.DownloadThreadHandler;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.repository.DownloadRepository;
import com.github.dannil.httpdownloader.utility.FileUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles back end operations for downloads.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Service(value = "DownloadService")
public class DownloadService implements IDownloadService {

    @Autowired
    private DownloadRepository downloadRepository;

    @Autowired
    private DownloadThreadHandler handler;

    /**
     * Default constructor.
     */
    public DownloadService() {

    }

    /**
     * Find a download by it's ID.
     *
     * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
     */
    @Override
    public Download findById(long downloadId) {
        Optional<Download> opDownload = this.downloadRepository.findById(downloadId);
        return opDownload.orElse(null);
    }

    /**
     * Find downloads for the specified user.
     *
     * @see com.github.dannil.httpdownloader.repository.DownloadRepository#findByUser(User)
     */
    @Override
    public List<Download> findByUser(User user) {
        return new ArrayList<>(this.downloadRepository.findByUser(user));
    }

    /**
     * Delete a persisted download which matches the specified download.
     *
     * @see org.springframework.data.repository.CrudRepository#delete(Object)
     */
    @Override
    public void delete(Download download) {
        this.handler.interrupt(download.getFormat());
        this.handler.deleteFromDisk(download);

        this.downloadRepository.delete(download);
    }

    /**
     * Delete a persisted download with the specified ID.
     *
     * @see org.springframework.data.repository.CrudRepository#delete(Object)
     */
    @Override
    public void delete(long downloadId) {
        this.downloadRepository.deleteById(downloadId);
    }

    /**
     * Persist the specified download.
     *
     * @see org.springframework.data.repository.CrudRepository#save(Object)
     */
    @Override
    public Download save(Download download) {
        return this.downloadRepository.save(download);
    }

    /**
     * Initiate the specified download and save it to the disk.
     *
     * @see com.github.dannil.httpdownloader.handler.download.DownloadThreadHandler#saveToDisk(Download)
     */
    @Override
    public Download saveToDisk(Download download) {
        this.handler.saveToDisk(download);

        return download;
    }

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
    @Override
    public void serveDownload(ServletContext context, HttpServletResponse response, Download download)
            throws IOException {
        File file = FileUtility.getFromDrive(download);

        if (file != null) {
            try (FileInputStream inStream = new FileInputStream(file)) {
                String mimeType = context.getMimeType(file.getAbsolutePath());
                if (mimeType == null) {
                    // set to binary type if MIME mapping not found
                    mimeType = "application/octet-stream";
                }

                // modifies response
                response.setContentType(mimeType);
                response.setContentLength((int) file.length());

                // forces download
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", download.getFilename());
                response.setHeader(headerKey, headerValue);

                // obtains response's output stream
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }

}
