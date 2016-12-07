package com.github.dannil.httpdownloader.utility;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.github.dannil.httpdownloader.exception.LanguageException;

/**
 * Class which handles language operations.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class LanguageUtility {

	private static final Logger LOGGER = Logger.getLogger(LanguageUtility.class.getName());

	private LanguageUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	/**
	 * <p> Return a language bundle which matches the inputed locale. </p>
	 * 
	 * @param locale
	 *            the language file to load
	 * 
	 * @return a ResourceBundle with a collection of localized language strings, in the
	 *         inputed locale
	 */
	private static ResourceBundle getLanguageBundleFromDisk(Locale locale) {
		LinkedList<Locale> availableLanguages = new LinkedList<Locale>(getLanguages());
		if (availableLanguages.contains(locale)) {
			// Return the specified language as a localized ResourceBundle
			return ResourceBundle.getBundle(ConfigUtility.getLanguageRelativePath(), locale);
			// xmlUtility.getElementValue("/configuration/app/paths/language")
		}
		// Return a ResourceBundle for the default language (default en-US)
		return ResourceBundle.getBundle(ConfigUtility.getLanguageRelativePath(), Locale.forLanguageTag("en-US"));
	}

	/**
	 * <p> Return a language bundle which matches the specified locale. If the specified
	 * locale should be null, return the default language. </p>
	 * 
	 * @param locale
	 *            the locale to translate to a resource bundle
	 * 
	 * @return a language bundle which matches either the locale or the default language
	 * 
	 * @see com.github.dannil.httpdownloader.utility.LanguageUtility#getLanguage(Locale)
	 */
	public static ResourceBundle getLanguage(Locale locale) {
		if (locale != null) {
			LOGGER.info("Loading specific language: " + locale.toLanguageTag());
			// The user has specifically entered another language which differs
			// from the default language. We proceed to load the specified
			// language instead of the default
			//
			// Note that this also matches if the specified locale should be the
			// same as the default language; the logic for implementing that
			// case is also covered by this branch.
			return getLanguageBundleFromDisk(locale);
		}
		// The user hasn't specified another language; load the default
		LOGGER.info("Loading default language: " + getDefaultLanguageFromConfigFile().toLanguageTag());
		return getLanguageBundleFromDisk(getDefaultLanguageFromConfigFile());
	}

	/**
	 * <p> Returns a list of all available languages by loading the properties files
	 * dynamically from the file system. </p> <p> The method checks every single loaded
	 * properties file for their language and converts this into a usable Locale. </p>
	 * 
	 * @return all the available languages, saved as list of Locales
	 * 
	 * @see com.github.dannil.httpdownloader.utility.FileUtility#getProperties(String,
	 *      String)
	 */
	public static List<Locale> getLanguages() {
		LinkedList<Properties> properties = null;

		try {
			properties = new LinkedList<Properties>(
					FileUtility.getProperties(ConfigUtility.getPropertiesAbsolutePath(), "language"));
		} catch (IOException e) {
			throw new LanguageException(e);
		}

		LinkedList<Locale> languages = new LinkedList<Locale>();
		for (Properties property : properties) {
			String languageTag = property.getProperty("languagetag");
			String[] parts = languageTag.split("-");
			Locale locale = new Locale(parts[0], parts[1]);

			languages.add(locale);
		}

		return languages;
	}

	/**
	 * <p> Returns the default language as saved in the configuration file. If there isn't
	 * a default display language specified, return a default display language (i.e.
	 * en-US). </p>
	 * 
	 * @return a Locale representation of the default language string
	 */
	public static Locale getDefaultLanguageFromConfigFile() {
		String language = ConfigUtility.getDefaultLanguage();

		if ("".equals(language)) {
			return Locale.forLanguageTag("en-US");
		} else {
			return Locale.forLanguageTag(language);
		}
	}
}
