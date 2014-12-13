package org.dannil.httpdownloader.utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public final class LanguageUtility {

	private final static Logger LOGGER = Logger.getLogger(LanguageUtility.class.getName());

	private static final Locale DEFAULT_LOCALE;
	private static final List<Locale> availableLanguages;

	private LanguageUtility() {
		throw new UnsupportedOperationException();
	}

	static {
		DEFAULT_LOCALE = new Locale("en", "US");

		availableLanguages = new LinkedList<Locale>();
		availableLanguages.add(new Locale("en", "US"));
		availableLanguages.add(new Locale("sv", "SE"));
	}

	/**
	 * Return a language bundle which matches the user's current display language.
	 * 
	 * @return a ResourceBundle with a collection of localized language strings, in the default language
	 */
	public static final ResourceBundle getLanguage() {
		return getLanguage(Locale.getDefault());
	}

	/**
	 * Return a language bundle which matches the inputed locale.
	 * 
	 * @param locale
	 * 					the language file to load
	 * 
	 * @return a ResourceBundle with a collection of localized language strings, in the inputed locale
	 * 
	 * @see org.dannil.httpdownloader.utility.ResourceUtility#getResourceBundle(String, Locale)
	 */
	public static final ResourceBundle getLanguage(final Locale locale) {
		return getLanguageBundle(PathUtility.PATH_LANGUAGE, locale);
	}

	/**
	 * Return a language bundle which matches the language currently in the session. If there isn't
	 * a language in the session, return the default display language.
	 * 
	 * @param session
	 * 					the session to check for the language
	 * 
	 * @return a language bundle which matches either the language in the session or the default display language
	 */
	public static final ResourceBundle getLanguage(final HttpSession session) {
		if (!session.getAttribute("language").equals(getLanguage())) {
			// The user has specifically entered another language in the session
			// which differs from the default display language. We proceed to
			// load the specified language instead of the default
			return getLanguage((Locale) session.getAttribute("language"));
		}
		// The user hasn't specified another language; load the default
		// display language
		return getLanguage();
	}

	/**
	 * Return a resource bundle from the specified path with matches the specified locale.
	 * If the file for the inputed locale doesn't exist, return a standard language (enUS).
	 * 
	 * @param path
	 * 				the path of the file to load
	 * @param locale
	 * 				the locale of the file to load
	 * 
	 * @return a ResourceBundle containing the file which matches the specified path and locale
	 */
	private static final ResourceBundle getLanguageBundle(final String path, final Locale locale) {
		if (availableLanguages.contains(locale)) {
			return ResourceBundle.getBundle(path, locale);
		}
		return getLanguageBundle(path, DEFAULT_LOCALE);
	}

}
