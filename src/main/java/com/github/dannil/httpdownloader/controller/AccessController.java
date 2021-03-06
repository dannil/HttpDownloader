package com.github.dannil.httpdownloader.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.ILoginService;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.utility.URLUtility;
import com.github.dannil.httpdownloader.validator.LoginValidator;
import com.github.dannil.httpdownloader.validator.RegisterValidator;

/**
 * Controller for mappings on access operations, such as login and logout.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Controller(value = "AccessController")
public final class AccessController {

    private static final Logger LOGGER = Logger.getLogger(AccessController.class.getName());

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private RegisterValidator registerValidator;

    @Autowired
    private LoginValidator loginValidator;

    // Login a user, loads login.xhtml from /WEB-INF/view
    @RequestMapping(value = "/login", method = GET)
    public String loginGET(HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("user") != null) {
            LOGGER.info("Session user object already set, forwarding...");
            return URLUtility.getUrlRedirect(URL.DOWNLOADS);
        }
        return URLUtility.getUrl(URL.LOGIN);
    }

    @RequestMapping(value = "/login", method = POST)
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

    // Logout a user
    @RequestMapping(value = "/logout", method = GET)
    public String logoutGET(HttpSession session) {
        session.setAttribute("user", null);

        LOGGER.info("Logout successful");

        return URLUtility.getUrlRedirect(URL.LOGIN);
    }

    // Register a user, loads register.xhtml from /WEB-INF/view
    @RequestMapping(value = "/register", method = GET)
    public String registerGET(HttpServletRequest request, HttpSession session) {
        return URLUtility.getUrl(URL.REGISTER);
    }

    @RequestMapping(value = "/register", method = POST)
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
