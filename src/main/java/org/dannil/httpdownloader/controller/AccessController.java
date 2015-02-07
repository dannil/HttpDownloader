package org.dannil.httpdownloader.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.ILoginService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.dannil.httpdownloader.utility.XMLUtility;
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
	private IDownloadService downloadService;

	@Autowired
	private IRegisterService registerService;

	@Autowired
	private ILoginService loginService;

	@Autowired
	private RegisterValidator registerValidator;

	@Autowired
	private LoginValidator loginValidator;

	private XMLUtility xmlUtility;

	/**
	 * <p>This method is only used for initializing a new user before the application
	 * starts up, as HSQL is configured to create a new database every time the application
	 * starts. This saves time from having to register a new user every single time we want
	 * to test the application.</p>
	 * 
	 * <p>This method can be removed in a production environment.</p>
	 */
	@PostConstruct
	private final void populateWithData() {
		final User user = new User("example@example.com", "1", "ExampleFirst", "ExampleLast");
		final User tempUser = this.registerService.save(user);

		final Download download = new Download("pi", "http://dannils.se/pi.zip");
		download.setUser(tempUser);
		final Download tempDownload = this.downloadService.save(download);
		user.addDownload(tempDownload);
	}

	@PostConstruct
	public final void init() {
		this.xmlUtility = new XMLUtility(PathUtility.getAbsolutePathToConfiguration() + "config.xml");
	}

	// Login a user, loads login.xhtml from /WEB-INF/view
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public final String loginGET(final HttpServletRequest request, final HttpSession session) {
		if (session.getAttribute("user") != null) {
			LOGGER.info("Session user object already set, forwarding...");
			return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/downloads"));
		}

		return this.xmlUtility.getElementValue("/configuration/app/urls/login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public final String loginPOST(final HttpSession session, @ModelAttribute("user") final User user, final BindingResult result) {
		this.loginValidator.validate(user, result);
		if (result.hasErrors()) {
			LOGGER.error("ERRORS ON LOGIN");
			return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/login"));
		}

		final User tempUser = this.loginService.findByEmail(user.getEmail());
		if (tempUser == null) {
			LOGGER.info("ERROR ON LOGIN - INVALID USERNAME AND/OR PASSWORD");
			return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/login"));
		}

		// Security measure
		tempUser.setPassword("");

		session.setAttribute("user", tempUser);

		LOGGER.info("SUCCESS ON LOGIN");

		return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/downloads"));
	}

	// Logout a user
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public final String logoutGET(final HttpSession session) {
		session.setAttribute("user", null);

		LOGGER.info("Logout successful");

		return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/login"));
	}

	// Register a user, loads register.xhtml from /WEB-INF/view
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public final String registerGET(final HttpServletRequest request, final HttpSession session) {
		return this.xmlUtility.getElementValue("/configuration/app/urls/register");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public final String registerPOST(final HttpSession session, @ModelAttribute("user") final User user, final BindingResult result) {
		this.registerValidator.validate(user, result);
		if (result.hasErrors()) {
			LOGGER.error("ERRORS ON REGISTER");
			return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/register"));
		}

		final User tempUser = this.registerService.save(user);

		// Security measure
		tempUser.setPassword("");

		LOGGER.info("SUCCESS ON REGISTER");

		return URLUtility.redirect(this.xmlUtility.getElementValue("/configuration/app/urls/login"));
	}

}
