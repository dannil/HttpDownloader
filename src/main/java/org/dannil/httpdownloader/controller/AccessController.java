package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.dannil.httpdownloader.utility.ValidationUtility;
import org.dannil.httpdownloader.validator.LoginValidator;
import org.dannil.httpdownloader.validator.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on access operations, such as login and logout.
 * 
 * @author Daniel Nilsson
 */
@Controller(value = "AccessController")
public final class AccessController {

	private final static Logger LOGGER = Logger.getLogger(AccessController.class.getName());

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ILoginService loginService;

	@Autowired
	private RegisterValidator registerValidator;

	@Autowired
	private LoginValidator loginValidator;

	// Login a user, loads login.xhtml from /WEB-INF/view
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public final String loginGET(final HttpServletRequest request, final HttpSession session, final Locale locale) {
		if (!ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.info("Session user object already set, forwarding...");
			return URLUtility.redirect(PathUtility.URL_DOWNLOADS);
		}
		LOGGER.info("Loading " + PathUtility.PATH_VIEW + "/login.xhtml...");
		request.setAttribute("language", LanguageUtility.getLanguage(session));

		return PathUtility.URL_LOGIN;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public final String loginPOST(final HttpSession session, @ModelAttribute("user") final User user, final BindingResult result) {
		this.loginValidator.validate(user, result);
		if (result.hasErrors()) {
			LOGGER.error("ERRORS ON LOGIN");
			return URLUtility.redirect(PathUtility.URL_LOGIN);
		}

		final User tempUser = this.loginService.findByEmail(user.getEmail());
		if (ValidationUtility.isNull(tempUser)) {
			LOGGER.info("ERROR ON LOGIN - INVALID USERNAME AND/OR PASSWORD");
			return URLUtility.redirect(PathUtility.URL_LOGIN);
		}

		// Security measure
		tempUser.setPassword("");

		session.setAttribute("user", tempUser);

		LOGGER.info("SUCCESS ON LOGIN");

		return URLUtility.redirect(PathUtility.URL_DOWNLOADS);
	}

	// Logout a user
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public final String logoutGET(final HttpSession session) {
		session.setAttribute("user", null);

		LOGGER.info("Logout successful");

		return URLUtility.redirect(PathUtility.URL_LOGIN);
	}

	// Register a user, loads register.xhtml from /WEB-INF/view
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public final String registerGET(final HttpServletRequest request, final HttpSession session, final Locale locale) {
		LOGGER.info("Loading " + PathUtility.PATH_VIEW + "/register.xhtml...");
		request.setAttribute("language", LanguageUtility.getLanguage(session));

		return PathUtility.URL_REGISTER;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public final String registerPOST(final HttpSession session, @ModelAttribute("user") final User user, final BindingResult result) {
		this.registerValidator.validate(user, result);
		if (result.hasErrors()) {
			LOGGER.error("ERRORS ON REGISTER");
			return URLUtility.redirect(PathUtility.URL_REGISTER);
		}

		final User tempUser = this.registerService.save(user);

		// Security measure
		tempUser.setPassword("");

		LOGGER.info("SUCCESS ON REGISTER");

		return URLUtility.redirect(PathUtility.URL_LOGIN);
	}
}
