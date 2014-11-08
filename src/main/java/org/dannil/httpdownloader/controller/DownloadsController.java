package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.RedirectUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController {

	@Autowired
	private IDownloadService downloadService;

	// Loads downloads.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String downloadsGET(final HttpSession session, final Locale language) {
		if (session.getAttribute("user") == null) {
			System.out.println("IS NULL");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		System.out.println("Loading " + PathUtility.VIEW_PATH + "/downloads.xhtml...");
		session.setAttribute("language", LanguageUtility.getLanguageBundle(language));

		Download download = this.downloadService.findById(1);
		System.out.println(download);

		return PathUtility.URL_DOWNLOADS;
	}

	// Loads add.xhtml from /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public final String downloadsAddGET(final HttpSession session, final Locale locale) {
		if (session.getAttribute("user") == null) {
			System.out.println("Session object user is not set");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		System.out.println("Loading " + PathUtility.VIEW_DOWNLOADS_PATH + "/add.xhtml...");
		session.setAttribute("language", LanguageUtility.getLanguageBundle(locale));

		return PathUtility.URL_DOWNLOADS_ADD;
	}

}
