package org.dannil.httpdownloader.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.ILoginService;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.validator.LoginValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "LoginController")
@RequestMapping("/login")
public final class LoginController implements IController {

	private ResourceBundle languageBundle;

	@Inject
	private ILoginService loginService;

	@Inject
	private LoginValidator loginValidator;

	@Override
	public void initializeLanguage() {
		this.languageBundle = LanguageUtility.getLanguageBundle();
	}

	// Loads login.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void loginGET(final HttpSession session) {
		System.out.println("Loading " + PathUtility.VIEW_PATH + "/login.xhtml...");
		this.initializeLanguage();
		session.setAttribute("language", this.languageBundle);
	}

	@RequestMapping(method = RequestMethod.POST)
	public final String loginPOST(@ModelAttribute("user") final User user, final HttpSession session, final BindingResult result) {
		this.loginValidator.validate(user, result);

		if (result.hasErrors()) {
			System.out.println("ERRORS");
			return "redirect:/login";
		}

		User tempUser = this.loginService.findByEmail(user.getEmail());
		session.setAttribute("user", tempUser);

		System.out.println("SUCCESS");
		System.out.println(tempUser);

		return "redirect:/files";
	}
}
