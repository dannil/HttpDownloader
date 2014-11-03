// Author: 	Daniel Nilsson
// Date: 	2014-07-09
// Changed: 2014-11-02

package org.dannil.httpdownloader.controller;

import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController implements IController {

	private ResourceBundle languageBundle;

	@Override
	public void initializeLanguage() {
		LanguageUtility languageUtility = new LanguageUtility();
		this.languageBundle = languageUtility.getLanguageBundle();
	}

	// Loads downloads.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void downloadsGET(final HttpSession session) {
		System.out.println("Loading " + PathUtility.VIEW_PATH + "/downloads.xhtml...");
		this.initializeLanguage();
		session.setAttribute("language", this.languageBundle);
	}

}
