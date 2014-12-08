package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.ResourceUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on index.
 * 
 * @author Daniel Nilsson
 */
@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {

	private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());

	// Loads index.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String indexGET(final HttpServletRequest request, final Locale locale) {
		LOGGER.info("Loading " + PathUtility.PATH_VIEW + "/index.xhtml...");
		request.setAttribute("language", ResourceUtility.getLanguageBundle(locale));

		return PathUtility.URL_INDEX;
	}

}
