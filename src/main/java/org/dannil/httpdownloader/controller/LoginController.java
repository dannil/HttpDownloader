// Author: 	Daniel Nilsson
// Date: 	2014-08-18
// Changed: 2014-11-03

package org.dannil.httpdownloader.controller;

import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "LoginController")
@RequestMapping("/login")
public final class LoginController implements IController {

	private ResourceBundle languageBundle;

	@Override
	public void initializeLanguage() {
		LanguageUtility languageUtility = new LanguageUtility();
		this.languageBundle = languageUtility.getLanguageBundle();
	}

	// Loads login.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void loginGET(final HttpSession session) {
		System.out.println("Loading " + PathUtility.VIEW_PATH + "/login.xhtml...");
		this.initializeLanguage();
		session.setAttribute("language", this.languageBundle);
	}

	@RequestMapping(method = RequestMethod.POST)
	public final void loginPOST() {
		// TODO
	}

}
