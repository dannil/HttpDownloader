package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {

	// Loads index.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void indexGET(final HttpSession session, final Locale language) {
		System.out.println("Loading " + PathUtility.VIEW_PATH + "/index.xhtml...");
		session.setAttribute("language", LanguageUtility.getLanguageBundle(language));
	}

}
