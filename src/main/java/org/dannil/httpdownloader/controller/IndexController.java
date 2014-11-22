package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {

	private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());

	// Loads index.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void indexGET(final HttpSession session, final Locale locale) {
		LOGGER.info("Loading " + PathUtility.VIEW_PATH + "/index.xhtml...");
		session.setAttribute("language", LanguageUtility.getLanguageBundle(locale));
	}

}
