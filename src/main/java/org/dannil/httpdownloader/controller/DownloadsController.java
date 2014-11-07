package org.dannil.httpdownloader.controller;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.RedirectUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController extends GenericController {

	// Loads downloads.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String downloadsGET(final HttpSession session) {
		if (session.getAttribute("userId") == null) {
			System.out.println("IS NULL");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		System.out.println("Loading " + PathUtility.VIEW_PATH + "/downloads.xhtml...");
		session.setAttribute("language", super.languageBundle);

		return PathUtility.URL_DOWNLOADS;
	}

	// Loads add.xhtml from /WEB-INF/view/downloads
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public final String downloadsAddGET(final HttpSession session) {
		if (session.getAttribute("userId") == null) {
			System.out.println("IS NULL");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		System.out.println("Loading " + PathUtility.VIEW_DOWNLOADS_PATH + "/add.xhtml...");
		session.setAttribute("language", super.languageBundle);

		return PathUtility.URL_DOWNLOADS_ADD;
	}

}
