package com.github.dannil.httpdownloader.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IDownloadService;
import com.github.dannil.httpdownloader.utility.FileUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;
import com.github.dannil.httpdownloader.validator.DownloadValidator;

/**
 * Controller for mappings on downloads.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController {

	private final static Logger LOGGER = Logger.getLogger(DownloadsController.class.getName());

	@Autowired
	private ServletContext context;

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private DownloadValidator downloadValidator;

	// Loads downloads.xhtml from /WEB-INF/view
	@RequestMapping(method = GET)
	public final String downloadsGET(final HttpServletRequest request, final HttpSession session) {
		final User user = (User) session.getAttribute("user");

		request.setAttribute("downloads", user.getDownloads());

		return URLUtility.getUrl(URL.DOWNLOADS);
	}

	// Interface for adding a new download, loads add.xhtml from
	// /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = GET)
	public final String downloadsAddGET(final HttpServletRequest request, final HttpSession session) {
		return URLUtility.getUrl(URL.DOWNLOADS_ADD);
	}

	@RequestMapping(value = "/add", method = POST)
	public final String downloadsAddPOST(final HttpServletRequest request, final HttpSession session, @ModelAttribute final Download download, final BindingResult result) {
		final User user = (User) session.getAttribute("user");

		this.downloadValidator.validate(download, result);
		if (result.hasErrors()) {
			LOGGER.error("ERROR ON ADDING NEW DOWNLOAD");
			return URLUtility.getUrlRedirect(URL.DOWNLOADS_ADD);
		}

		download.setUser(user);

		Download tempDownload;
		if (request.getParameter("start") != null) {
			download.setStartDate(new DateTime());
			tempDownload = this.downloadService.save(download);
			this.downloadService.saveToDisk(tempDownload);
		} else {
			tempDownload = this.downloadService.save(download);
		}
		user.addDownload(tempDownload);

		LOGGER.info("SUCCESS ON ADDING NEW DOWNLOAD");

		return URLUtility.getUrlRedirect(URL.DOWNLOADS);
	}

	@RequestMapping(value = "/start/{id}", method = GET)
	public final String downloadsStartIdGET(final HttpSession session, @PathVariable final Long id) {
		final User user = (User) session.getAttribute("user");
		final Download download = user.getDownload(id);

		if (download != null) {
			final File file = FileUtility.getFromDrive(download);
			if (file != null && !file.exists()) {
				download.setStartDate(new DateTime());
				this.downloadService.saveToDisk(download);
			}
		}

		return URLUtility.getUrlRedirect(URL.DOWNLOADS);
	}

	// Get a download with the given id
	@RequestMapping(value = "/get/{id}", method = GET)
	public final String downloadsGetIdGET(final HttpServletResponse response, final HttpSession session, @PathVariable final Long id) throws IOException {
		final User user = (User) session.getAttribute("user");
		final Download download = user.getDownload(id);

		if (download != null) {
			final File file = FileUtility.getFromDrive(download);
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

	// Delete a download with the given id, loads downloads.xhtml from
	// /WEB-inf/view on success
	@RequestMapping(value = "/delete/{id}", method = GET)
	public final String downloadsDeleteIdGET(final HttpSession session, @PathVariable final Long id) {
		final User user = (User) session.getAttribute("user");
		final Download download = user.getDownload(id);

		user.deleteDownload(download);
		this.downloadService.delete(download);

		return URLUtility.getUrlRedirect(URL.DOWNLOADS);
	}

}