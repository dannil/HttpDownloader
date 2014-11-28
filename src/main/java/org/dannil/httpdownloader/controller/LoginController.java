package org.dannil.httpdownloader.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.RedirectUtility;
import org.dannil.httpdownloader.utility.ValidationUtility;
import org.dannil.httpdownloader.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on login
 * 
 * @author Daniel Nilsson
 */
@Controller(value = "LoginController")
@RequestMapping("/login")
public final class LoginController {

	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());

	@Autowired
	private ILoginService loginService;

	@Autowired
	private LoginValidator loginValidator;

	// Loads login.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final String loginGET(final HttpServletRequest request, final HttpSession session, final Locale locale) {
		if (!ValidationUtility.isNull(session.getAttribute("user"))) {
			LOGGER.info("Session user object already set, forwarding...");
			return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
		}
		LOGGER.info("Loading " + PathUtility.VIEW_PATH + "/login.xhtml...");
		request.setAttribute("language", LanguageUtility.getLanguageBundle(locale));

		return PathUtility.URL_LOGIN;
	}

	@RequestMapping(method = RequestMethod.POST)
	public final String loginPOST(final HttpServletRequest request, final HttpSession session, final Locale locale, @ModelAttribute("user") final User user, final BindingResult result) {
		this.loginValidator.validate(user, result);
		if (result.hasErrors()) {
			LOGGER.error("ERRORS ON LOGIN");
			return RedirectUtility.redirect(PathUtility.URL_LOGIN);
		}

		User tempUser = this.loginService.findByEmail(user.getEmail());
		tempUser.setPassword("");
		session.setAttribute("user", tempUser);

		LOGGER.info("SUCCESS");
		LOGGER.info(tempUser);

		LOGGER.info(tempUser.getDownloads());

		return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
	}
}
