package org.dannil.httpdownloader.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.RedirectUtility;
import org.dannil.httpdownloader.utility.ResourceUtility;
import org.dannil.httpdownloader.utility.ValidationUtility;
import org.dannil.httpdownloader.validator.DownloadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on downloads
 * 
 * @author Daniel Nilsson
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
	public final String downloadsGET(final HttpServletRequest request, final HttpSession session, final Locale locale) {
		if (ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		LOGGER.info("Loading " + PathUtility.VIEW_PATH + "/downloads.xhtml...");
		request.setAttribute("language", ResourceUtility.getLanguageBundle(locale));

		final User user = (User) session.getAttribute("user");
		user.setDownloads(this.downloadService.findByUser(user));

		request.setAttribute("downloads", user.getDownloads());

		return PathUtility.URL_DOWNLOADS;
	}

	// Loads add.xhtml from /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public final String downloadsAddGET(final HttpServletRequest request, final HttpSession session, final Locale locale) {
		if (ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		LOGGER.info("Loading " + PathUtility.VIEW_DOWNLOADS_PATH + "/add.xhtml...");
		request.setAttribute("language", ResourceUtility.getLanguageBundle(locale));

		return PathUtility.URL_DOWNLOADS_ADD;
	}

	// POST handler for add.xhtml from /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public final String downloadsAddPOST(final HttpServletRequest request, final HttpSession session, @ModelAttribute final Download download, final BindingResult result) {
		if (ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		final User user = (User) session.getAttribute("user");

		this.downloadValidator.validate(download, result);
		if (result.hasErrors()) {
			LOGGER.error("ERROR ON ADDING NEW DOWNLOAD");
			return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS_ADD);
		}

		download.setUser(user);
		final Download tempDownload = this.downloadService.save(download);
		LOGGER.info("SUCCESS ON ADDING NEW DOWNLOAD");

		final boolean startDownload = request.getParameter("start") != null;
		if (startDownload) {
			this.downloadService.saveToDisk(tempDownload);
		}

		return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
	}

	// POST handler for deleting a download
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public final String downloadsDeleteIdGET(final HttpSession session, @PathVariable final Long id) {
		if (ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		final User user = (User) session.getAttribute("user");
		final Download download = this.downloadService.findById(id);

		if (download != null) {
			if (!download.getUser().getUserId().equals(user.getUserId())) {
				LOGGER.error("Injection attempt detected in DownloadsController.downloadsDeleteIdGET!");
				return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
			}
			this.downloadService.delete(download);
		}

		return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public final String downloadsGetGET(final HttpServletResponse response, final HttpSession session, @PathVariable final Long id) {
		if (ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		final User user = (User) session.getAttribute("user");
		final Download download = user.getDownload(id);

		if (download != null && !download.getUser().getUserId().equals(user.getUserId())) {
			LOGGER.error("Injection attempt detected in DownloadsController.downloadsDeleteIdGET!");
			return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
		}

		final String path = PathUtility.DOWNLOADS_PATH + "/" + download.hashCode() + "_" + FilenameUtils.getName(download.getUrl());
		final File file = new File(path);

		FileInputStream inStream;
		OutputStream outStream;

		try {
			inStream = new FileInputStream(file);

			String mimeType = this.context.getMimeType(path);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}

			// modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) file.length());

			// forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inStream.close();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// As we have manipulated the MIME type to be returned as a type of
		// "Save file"-dialog, the browser will not see this line anyway and it
		// can therefore be null to avoid confusion
		return null;
	}
}
