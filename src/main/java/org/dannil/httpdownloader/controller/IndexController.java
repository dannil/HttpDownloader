package org.dannil.httpdownloader.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.utility.URLUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on index.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {

	private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());

	// Landing controller for application, loads index.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String indexGET(final HttpServletRequest request, final HttpSession session) {
		return URLUtility.getUrl(URL.INDEX);
	}

}
