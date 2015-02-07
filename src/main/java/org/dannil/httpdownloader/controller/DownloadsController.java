package org.dannil.httpdownloader.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.utility.URLUtility;
import org.dannil.httpdownloader.validator.DownloadValidator;
import org.joda.time.DateTime;
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
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     0.0.1-SNAPSHOT
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
	@RequestMapping(method = RequestMethod.GET)
	public final String downloadsGET(final HttpServletRequest request, final HttpSession session) {
		final User user = (User) session.getAttribute("user");

		request.setAttribute("downloads", user.getDownloads());

		return URLUtility.getUrl(URL.DOWNLOADS);
	}

	// Interface for adding a new download, loads add.xhtml from
	// /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public final String downloadsAddGET(final HttpServletRequest request, final HttpSession session) {
		return URLUtility.getUrl(URL.DOWNLOADS_ADD);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
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

	// Get a download with the given id
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public final String downloadsGetIdGET(final HttpServletResponse response, final HttpSession session, @PathVariable final Long id) throws IOException {
		final User user = (User) session.getAttribute("user");
		final Download download = user.getDownload(id);

		if (!download.getUser().getId().equals(user.getId())) {
			LOGGER.error("Injection attempt detected in DownloadsController.downloadsGetIdGET!");
			return URLUtility.getUrlRedirect(URL.DOWNLOADS);
		}

		this.downloadService.serveDownload(this.context, response, download);

		// As we have manipulated the MIME type to be returned as a type of
		// "Save file"-dialog, the browser will not see this line anyway and it
		// can therefore be null to avoid confusion
		return null;
	}

	// Delete a download with the given id, loads downloads.xhtml from
	// /WEB-inf/view on success
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public final String downloadsDeleteIdGET(final HttpSession session, @PathVariable final Long id) {
		final User user = (User) session.getAttribute("user");
		final Download download = user.getDownload(id);

		if (!download.getUser().getId().equals(user.getId())) {
			LOGGER.error("Injection attempt detected in DownloadsController.downloadsDeleteIdGET!");
			return URLUtility.getUrlRedirect(URL.DOWNLOADS);
		}

		user.deleteDownload(download);
		this.downloadService.delete(download);

		return URLUtility.getUrlRedirect(URL.DOWNLOADS);
	}

}
