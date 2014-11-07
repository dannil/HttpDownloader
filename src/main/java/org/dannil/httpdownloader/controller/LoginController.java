package org.dannil.httpdownloader.controller;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
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
public final class LoginController extends GenericController {

	@Autowired
	private ILoginService loginService;

	@Autowired
	private LoginValidator loginValidator;

	// Loads login.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void loginGET(final HttpSession session) {
		this.initializeLanguage();
		System.out.println("Loading " + PathUtility.VIEW_PATH + "/login.xhtml...");
		session.setAttribute("language", this.languageBundle);
	}

	@RequestMapping(method = RequestMethod.POST)
	public final String loginPOST(@ModelAttribute("user") final User user, final HttpSession session, final BindingResult result) {
		this.loginValidator.validate(user, result);

		if (result.hasErrors()) {
			System.out.println("ERRORS");
			return RedirectUtility.redirect("/login");
		}

		User tempUser = this.loginService.findByEmail(user.getEmail());
		session.setAttribute("user", tempUser);

		System.out.println("SUCCESS");
		// System.out.println(tempUser);

		return RedirectUtility.redirect("/downloads");
	}
}
