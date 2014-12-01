package org.dannil.httpdownloader.utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class which handles operations on language bundles.
 * 
 * @author Daniel Nilsson
 */
public final class ResourceUtility {

	private static final Locale DEFAULT_LOCALE;
	private static final List<Locale> availableLanguages;

	static {
		DEFAULT_LOCALE = new Locale("en", "US");

		availableLanguages = new LinkedList<Locale>();
		availableLanguages.add(new Locale("en", "US"));
		availableLanguages.add(new Locale("sv", "SE"));
	}

	private ResourceUtility() {
		throw new AssertionError();
	}

	/**
	 * Returns a bundle with the language which matches the users current display language.
	 * 
	 * @return ResourceBundle with default language
	 */
	public static final ResourceBundle getLanguageBundle() {
		return getLanguageBundle(Locale.getDefault());
	}

	/**
	 * Returns a bundle with the language which matches the inputed locale.
	 * If the language file doesn't exist, return a standard language (enUS).
	 * 
	 * @param locale
	 * 					- The language to load
	 * 
	 * @return ResourceBundle with inputed locale
	 */
	public static final ResourceBundle getLanguageBundle(final Locale locale) {
		return getResourceBundle(PathUtility.LANGUAGE_PATH, locale);
	}

	public static final ResourceBundle getErrorBundle() {
		return getErrorBundle(Locale.getDefault());
	}

	private static final ResourceBundle getErrorBundle(final Locale locale) {
		return getResourceBundle(PathUtility.ERROR_PATH, locale);
	}

	private static final ResourceBundle getResourceBundle(final String path, final Locale locale) {
		if (availableLanguages.contains(locale)) {
			return ResourceBundle.getBundle(path, locale);
		}
		return getResourceBundle(PathUtility.LANGUAGE_PATH, DEFAULT_LOCALE);
	}

}
