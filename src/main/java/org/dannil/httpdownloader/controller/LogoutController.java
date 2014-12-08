package org.dannil.httpdownloader.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on logout.
 * 
 * @author Daniel Nilsson
 */
@Controller(value = "LogoutController")
@RequestMapping("/logout")
public final class LogoutController {

	private final static Logger LOGGER = Logger.getLogger(LogoutController.class.getName());

	@RequestMapping(method = RequestMethod.GET)
	public final String logoutGET(final HttpSession session) {
		session.setAttribute("user", null);

		LOGGER.info("Logout successful");

		return URLUtility.redirect(PathUtility.URL_LOGIN);
	}
}
