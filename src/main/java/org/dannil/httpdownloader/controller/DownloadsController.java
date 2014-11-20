package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.RedirectUtility;
import org.dannil.httpdownloader.validator.DownloadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController {

	private final static Logger LOGGER = Logger.getLogger(DownloadsController.class.getName());

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private DownloadValidator downloadValidator;

	// Loads downloads.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String downloadsGET(final HttpSession session, final Locale locale) {
		if (session.getAttribute("user") == null) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		LOGGER.info("Loading " + PathUtility.VIEW_PATH + "/downloads.xhtml...");
		session.setAttribute("language", LanguageUtility.getLanguageBundle(locale));

		Download download = this.downloadService.findById(1);
		LOGGER.info(download);

		Download tempDownload1 = new Download();
		tempDownload1.setTitle("Hello World");
		tempDownload1.setUrl("http://dannils.se/dev/hashgenerator/");
		System.out.println(tempDownload1);
		Download tempDownload2 = this.downloadService.save(tempDownload1);

		return PathUtility.URL_DOWNLOADS;
	}

	// Loads add.xhtml from /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public final String downloadsAddGET(final HttpSession session, final Locale locale) {
		if (session.getAttribute("user") == null) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		LOGGER.info("Loading " + PathUtility.VIEW_DOWNLOADS_PATH + "/add.xhtml...");
		session.setAttribute("language", LanguageUtility.getLanguageBundle(locale));

		return PathUtility.URL_DOWNLOADS_ADD;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public final String downloadsAddPOST(final HttpSession session, final Locale locale, @ModelAttribute final Download download, final BindingResult result) {
		if (session.getAttribute("user") == null) {
			LOGGER.error("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		this.downloadValidator.validate(download, result);
		if (result.hasErrors()) {
			LOGGER.error("ERROR ON ADDING NEW DOWNLOAD");
			return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS_ADD);
		}

		// Download tempDownload = this.downloadService.save(download);
		LOGGER.info("SUCCESS ON ADDING NEW DOWNLOAD");
		return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);

	}
}
