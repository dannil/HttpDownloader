package org.dannil.httpdownloader.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class which handles operations on language bundles
 * 
 * @author Daniel
 *
 */
public final class LanguageUtility {

	private static final Locale DEFAULT_LOCALE;
	private static final List<Locale> availableLanguages;

	static {
		DEFAULT_LOCALE = new Locale("en", "US");

		availableLanguages = new ArrayList<Locale>();
		availableLanguages.add(new Locale("en", "US"));
		availableLanguages.add(new Locale("sv", "SE"));
	}

	/**
	 * Private constructor to make the class a true singleton
	 */
	private LanguageUtility() {
		throw new AssertionError();
	}

	/**
	 * Returns a bundle with the language which matches the users current display language.
	 * @return ResourceBundle with default language
	 */
	public static final ResourceBundle getLanguageBundle() {
		return getLanguageBundle(Locale.getDefault());
	}

	/**
	 * Returns a bundle with the language which matches the inputed locale.
	 * If the language file doesn't exist, return a standard language (enUS).
	 * @param language
	 * 					- The language to load
	 * @return ResourceBundle with inputed locale
	 */
	public static final ResourceBundle getLanguageBundle(final Locale language) {
		if (availableLanguages.contains(language)) {
			return ResourceBundle.getBundle(PathUtility.LANGUAGE_PATH, language);
		}
		return ResourceBundle.getBundle(PathUtility.LANGUAGE_PATH, DEFAULT_LOCALE);
	}

}
