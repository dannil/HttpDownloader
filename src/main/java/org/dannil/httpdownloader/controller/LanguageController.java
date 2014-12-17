package org.dannil.httpdownloader.controller;

import java.util.LinkedList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for altering the display language.
 * 
 * @author Daniel Nilsson
 */
@Controller(value = "LanguageController")
@RequestMapping("/language")
public final class LanguageController {

	private final static Logger LOGGER = Logger.getLogger(LanguageController.class.getName());

	/**
	 * <p>Performs a check on the language string if the specified language exists in the application. 
	 * If it finds a match, it sets this language in the local session so it's saved between pages.</p>
	 * 
	 * @param request
	 * 					the request where to fetch the referrer URL from
	 * @param session
	 * 					the session to set the language in
	 * @param language
	 * 					the language string to search for
	 * 
	 * @return the page where the user came from (fetched from <b>request</b>)
	 */
	@RequestMapping(value = "/{language}", method = RequestMethod.GET)
	public final String languageGET(final HttpServletRequest request, final HttpSession session, @PathVariable final String language) {
		if (request.getHeader("referer") == null) {
			return URLUtility.redirect(PathUtility.URL_LOGIN);
		}

		// Convert the selected language into a representable object. This is
		// done for many reasons; an example is grandfathered tags (tags not in
		// use anymore). The Locale class automatically converts these tags to
		// their modern counterparts and we don't have to care about it.
		//
		// We also need to split the selected language into two parts to
		// convert the format from language-region (e.g. en-us) to
		// language-REGION (e.g. en-US)
		final String[] parts = language.split("-");
		final Locale selectedLanguage = new Locale(parts[0], parts[1].toUpperCase(Locale.ENGLISH));

		// Get the available languages from the language utility and compare it
		// with the user-specified language
		final LinkedList<Locale> availableLanguages = new LinkedList<Locale>(LanguageUtility.getLanguages());

		for (final Locale availableLanguage : availableLanguages) {
			// LOGGER.info(availableLanguage.toLanguageTag());
			if (availableLanguage.toLanguageTag().equals(selectedLanguage.toLanguageTag())) {
				LOGGER.info("FOUND LANGUAGE: " + selectedLanguage.toLanguageTag());
				session.setAttribute("language", selectedLanguage);
				break;
			}
		}

		return URLUtility.redirect(request.getHeader("referer"));
	}

}
