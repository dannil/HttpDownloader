package org.dannil.httpdownloader.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.XMLUtility;
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

	private XMLUtility xmlUtility;

	@PostConstruct
	private final void init() {
		this.xmlUtility = new XMLUtility(PathUtility.getAbsolutePathToConfiguration() + "config.xml");
	}

	// Landing controller for application, loads index.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String indexGET(final HttpServletRequest request, final HttpSession session) {
		LOGGER.info("Loading " + this.xmlUtility.getElementValue("/configuration/app/paths/views/view") + "/index.xhtml...");

		return this.xmlUtility.getElementValue("/configuration/app/urls/index");
	}

}
