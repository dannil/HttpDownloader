package org.dannil.httpdownloader.utility;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Class which handles language operations.
 * 
 * @author Daniel Nilsson
 */
public final class LanguageUtility {

	private final static Logger LOGGER = Logger.getLogger(LanguageUtility.class.getName());

	private static XMLUtility xmlUtility = new XMLUtility(PathUtility.getAbsolutePath() + "WEB-INF/configuration/config.xml");;

	private LanguageUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	static {
		Locale.setDefault(getDefault());
	}

	/**
	 * Return a language bundle which matches the inputed locale.
	 * 
	 * @param locale
	 * 					the language file to load
	 * 
	 * @return a ResourceBundle with a collection of localized language strings, in the inputed locale
	 */
	private static final ResourceBundle getLanguage(final Locale locale) {
		final LinkedList<Locale> availableLanguages = new LinkedList<Locale>(getLanguages());
		if (availableLanguages.contains(locale)) {
			// Return the specified language as a localized ResourceBundle
			return ResourceBundle.getBundle(xmlUtility.getElementValue("/configuration/app/paths/language"), locale);
			// xmlUtility.getElementValue("/configuration/app/paths/language")
		}
		// Return a ResourceBundle for the default language (default en-US)
		return ResourceBundle.getBundle(xmlUtility.getElementValue("/configuration/app/paths/language"), Locale.forLanguageTag("en-US"));
	}

	/**
	 * Return a language bundle which matches the language currently in the session. If there isn't
	 * a language in the session, return the default language.
	 * 
	 * @param session
	 * 					the session to check for the language
	 * 
	 * @return a language bundle which matches either the language in the session or the default language
	 * 
	 * @see org.dannil.httpdownloader.utility.LanguageUtility#getLanguage(Locale)
	 */
	public static final ResourceBundle getLanguage(final HttpSession session) {
		final Locale locale = (Locale) session.getAttribute("language");
		if (locale != null && !locale.equals(getDefault())) {
			LOGGER.info("Loading specific language: " + locale.toLanguageTag());
			// The user has specifically entered another language in the session
			// which differs from the default display language. We proceed to
			// load the specified language instead of the default
			return getLanguage(locale);
		}
		// The user hasn't specified another language; load the default
		// display language
		LOGGER.info("Loading default language: " + getDefault().toLanguageTag());
		return getLanguage(getDefault());
	}

	/**
	 * <p>Returns a list of all available languages by loading the properties files dynamically from the file system.</p>
	 * <p>The method checks every single loaded properties file for their language and converts this into
	 * a usable Locale.</p>
	 * 
	 * @return all the available languages, saved as list of Locales
	 * 
	 * @see org.dannil.httpdownloader.utility.FileUtility#getProperties(String, String)
	 */
	public static final List<Locale> getLanguages() {
		LinkedList<Properties> properties = null;

		try {
			properties = new LinkedList<Properties>(FileUtility.getProperties(PathUtility.getAbsolutePathToProperties(), "language"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		final LinkedList<Locale> languages = new LinkedList<Locale>();
		for (final Properties property : properties) {
			final String languageTag = property.getProperty("languagetag");
			final String[] parts = languageTag.split("-");
			final Locale locale = new Locale(parts[0], parts[1]);

			languages.add(locale);
		}

		return languages;
	}

	/**
	 * <p>Returns the default language as saved in the configuration file. If there isn't a
	 * default display language specified, return a default display language (i.e. en-US).</p>
	 * 
	 * @return a Locale representation of the default language string
	 */
	public static final Locale getDefault() {
		final String language = xmlUtility.getElementValue("configuration/app/defaults/language");

		if (language == "") {
			return Locale.forLanguageTag("en-US");
		} else {
			return Locale.forLanguageTag(language);
		}
	}
}
