package com.github.dannil.httpdownloader.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.ILoginService;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.utility.URLUtility;
import com.github.dannil.httpdownloader.validator.LoginValidator;
import com.github.dannil.httpdownloader.validator.RegisterValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on access operations, such as login and logout.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Controller(value = "AccessController")
public final class AccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessController.class.getName());

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private RegisterValidator registerValidator;

    @Autowired
    private LoginValidator loginValidator;

    /**
     * Default constructor.
     */
    public AccessController() {

    }

    /**
     * Loads the login page.
     *
     * @param request the HTTP request
     * @param session the HTTP session
     * @return an URL to the login page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("user") != null) {
            LOGGER.info("Session user object already set, forwarding...");
            return URLUtility.getUrlRedirect(URL.DOWNLOADS);
        }
        return URLUtility.getUrl(URL.LOGIN);
    }

    /**
     * Login a user.
     *
     * @param session the HTTP session
     * @param user the user which tries to login
     * @param result the result of the login operation
     * @return an URL to the download page on success or the login page on failure
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(HttpSession session, @ModelAttribute("user") User user, BindingResult result) {
        this.loginValidator.validate(user, result);
        if (result.hasErrors()) {
            LOGGER.error("ERRORS ON LOGIN");
            return URLUtility.getUrlRedirect(URL.LOGIN);
        }

        User tempUser = this.loginService.findByEmail(user.getEmail());
        if (tempUser == null) {
            LOGGER.info("ERROR ON LOGIN - INVALID USERNAME AND/OR PASSWORD");
            return URLUtility.getUrlRedirect(URL.LOGIN);
        }

        // Security measure
        tempUser.setPassword("");

        session.setAttribute("user", tempUser);

        LOGGER.info("SUCCESS ON LOGIN");

        return URLUtility.getUrlRedirect(URL.DOWNLOADS);
    }

    /**
     * Logout a user.
     *
     * @param session the HTTP session
     * @return an URL to the login page
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGET(HttpSession session) {
        session.setAttribute("user", null);

        LOGGER.info("Logout successful");

        return URLUtility.getUrlRedirect(URL.LOGIN);
    }

    /**
     * Loads the register page.
     *
     * @param request the HTTP request
     * @param session the HTTP session
     * @return an URL to the register page
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGET(HttpServletRequest request, HttpSession session) {
        return URLUtility.getUrl(URL.REGISTER);
    }

    /**
     * Register a user.
     *
     * @param session the HTTP session
     * @param user the user which tries to register
     * @param result the result of the register operation
     * @return an URL to the login page on success or the register page on failure
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPOST(HttpSession session, @ModelAttribute("user") User user, BindingResult result) {
        this.registerValidator.validate(user, result);
        if (result.hasErrors()) {
            LOGGER.error("ERRORS ON REGISTER");
            return URLUtility.getUrlRedirect(URL.REGISTER);
        }

        User tempUser = this.registerService.save(user);

        // Security measure
        tempUser.setPassword("");

        LOGGER.info("SUCCESS ON REGISTER");

        return URLUtility.getUrlRedirect(URL.LOGIN);
    }

}
