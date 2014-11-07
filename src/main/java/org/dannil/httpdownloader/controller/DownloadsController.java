package org.dannil.httpdownloader.controller;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController extends GenericController {

	// Loads downloads.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void downloadsGET(final HttpSession session) {
		System.out.println("Loading " + PathUtility.VIEW_PATH + "/downloads.xhtml...");
		session.setAttribute("language", super.languageBundle);
	}

}
