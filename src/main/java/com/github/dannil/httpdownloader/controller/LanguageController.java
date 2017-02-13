package com.github.dannil.httpdownloader.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.LanguageUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Controller for altering the display language.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Controller(value = "LanguageController")
@RequestMapping("/language")
public class LanguageController {

    private static final Logger LOGGER = Logger.getLogger(LanguageController.class.getName());

    /**
     * <p> Performs a check on the language string if the specified language exists in the
     * application. If it finds a match, it sets this language in the local session so
     * it's saved between pages. </p>
     * 
     * @param request
     *            the request where to fetch the referrer URL from
     * @param session
     *            the session to set the language in
     * @param language
     *            the language string to search for
     * 
     * @return the page where the user came from (fetched from <b>request</b>)
     */
    @RequestMapping(value = "/{language}", method = GET)
    public String languageGET(HttpServletRequest request, HttpSession session, @PathVariable String language) {
        if (request.getHeader("referer") == null) {
            return URLUtility.getUrlRedirect(URL.LOGIN);
        }

        // Convert the selected language into a representable object. This is
        // done for many reasons; an example is grandfathered tags (tags not in
        // use anymore). The Locale class automatically converts these tags to
        // their modern counterparts and we don't have to care about it.
        //
        // We also need to split the selected language into two parts to
        // convert the format from language-region (e.g. en-us) to
        // language-REGION (e.g. en-US)
        String[] parts = language.split("-");
        Locale selectedLanguage = new Locale(parts[0], parts[1].toUpperCase(Locale.ENGLISH));

        // Get the available languages from the language utility and compare it
        // with the user-specified language
        List<Locale> availableLanguages = LanguageUtility.getLanguages();

        for (Locale availableLanguage : availableLanguages) {
            if (availableLanguage.equals(selectedLanguage)) {
                LOGGER.info("FOUND LANGUAGE: " + selectedLanguage.toLanguageTag());
                session.setAttribute("language", selectedLanguage);
                break;
            }
        }

        return URLUtility.redirect(request.getHeader("referer"));
    }

}
