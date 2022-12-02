package com.github.dannil.httpdownloader.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IDownloadService;
import com.github.dannil.httpdownloader.utility.FileUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;
import com.github.dannil.httpdownloader.validator.DownloadValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on downloads.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public class DownloadsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadsController.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private IDownloadService downloadService;

    @Autowired
    private DownloadValidator downloadValidator;

    /**
     * Default constructor.
     */
    public DownloadsController() {

    }

    /**
     * Loads the downloads page.
     *
     * @param request the HTTP request
     * @param session the HTTP session
     * @return an URL to the downloads page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String downloadsGET(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");

        request.setAttribute("downloads", user.getDownloads());

        return URLUtility.getUrl(URL.DOWNLOADS);
    }

    /**
     * Loads the add downloads page.
     *
     * @param request the HTTP request
     * @param session the HTTP session
     * @return an URL to the add downloads page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String downloadsAddGET(HttpServletRequest request, HttpSession session) {
        return URLUtility.getUrl(URL.DOWNLOADS_ADD);
    }

    /**
     * Add a new download.
     *
     * @param request the HTTP request
     * @param session the HTTP session
     * @param download the download which is added
     * @param result the result of the download add operation
     * @return an URL to the downloads page on success or the add downloads page on failure
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String downloadsAddPOST(HttpServletRequest request, HttpSession session, @ModelAttribute Download download,
            BindingResult result) {
        User user = (User) session.getAttribute("user");

        this.downloadValidator.validate(download, result);
        if (result.hasErrors()) {
            LOGGER.error("ERROR ON ADDING NEW DOWNLOAD");
            return URLUtility.getUrlRedirect(URL.DOWNLOADS_ADD);
        }

        download.setUser(user);

        Download tempDownload;
        if (request.getParameter("start") == null) {
            tempDownload = this.downloadService.save(download);
        } else {
            download.setStartDate(LocalDateTime.now());
            tempDownload = this.downloadService.save(download);
            this.downloadService.saveToDisk(tempDownload);

        }
        user.addDownload(tempDownload);

        LOGGER.info("SUCCESS ON ADDING NEW DOWNLOAD");

        return URLUtility.getUrlRedirect(URL.DOWNLOADS);
    }

    /**
     * Start the download.
     *
     * @param session the HTTP session
     * @param id the ID of the download
     * @return an URL to the downloads page
     */
    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    public String downloadsStartIdGET(final HttpSession session, @PathVariable Long id) {
        User user = (User) session.getAttribute("user");
        Download download = user.getDownload(id);

        if (download != null) {
            File file = FileUtility.getFromDrive(download);
            if (file != null && !file.exists()) {
                download.setStartDate(LocalDateTime.now());
                this.downloadService.saveToDisk(download);
            }
        }

        return URLUtility.getUrlRedirect(URL.DOWNLOADS);
    }

    /**
     * Get the download with the given ID.
     *
     * @param response the HTTP response
     * @param session the HTTP session
     * @param id the ID of the download
     * @return a browser download dialog window
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String downloadsGetIdGET(HttpServletResponse response, HttpSession session, @PathVariable Long id)
            throws IOException {
        User user = (User) session.getAttribute("user");
        Download download = user.getDownload(id);

        if (download != null) {
            File file = FileUtility.getFromDrive(download);
            if (file != null && !file.exists()) {
                LOGGER.error("Download was null");
                return URLUtility.getUrlRedirect(URL.DOWNLOADS);
            }
            this.downloadService.serveDownload(this.context, response, download);
        }

        // As we have manipulated the MIME type to be returned as a type of
        // "Save file"-dialog, the browser will not see this line anyway and it
        // can therefore be null to avoid confusion
        return null;
    }

    /**
     * Delete a download with the given ID.
     *
     * @param session the HTTP session
     * @param id the ID of the download
     * @return an URL to the downloads page
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String downloadsDeleteIdGET(HttpSession session, @PathVariable Long id) {
        User user = (User) session.getAttribute("user");
        Download download = user.getDownload(id);

        user.deleteDownload(download);
        this.downloadService.delete(download);

        return URLUtility.getUrlRedirect(URL.DOWNLOADS);
    }

}
