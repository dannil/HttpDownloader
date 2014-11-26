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
import org.dannil.httpdownloader.validator.LoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public final void loginGET(final HttpServletRequest request, final HttpSession session, final Locale locale) {
		LOGGER.info("Loading " + PathUtility.VIEW_PATH + "/login.xhtml...");
		request.setAttribute("language", LanguageUtility.getLanguageBundle(locale));
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

		return RedirectUtility.redirect(PathUtility.URL_DOWNLOADS);
	}
}
