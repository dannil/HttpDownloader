package org.dannil.httpdownloader.utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class which handles operations on language bundles.
 * 
 * @author Daniel
 *
 */
public final class LanguageUtility {

	private static final Locale DEFAULT_LOCALE;
	private static final List<Locale> availableLanguages;

	static {
		DEFAULT_LOCALE = new Locale("en", "US");

		availableLanguages = new LinkedList<Locale>();
		availableLanguages.add(new Locale("en", "US"));
		availableLanguages.add(new Locale("sv", "SE"));
	}

	/**
	 * Private constructor to make the class a singleton.
	 */
	private LanguageUtility() {
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
	 * @param language
	 * 					- The language to load
	 * 
	 * @return ResourceBundle with inputed locale
	 */
	public static final ResourceBundle getLanguageBundle(final Locale language) {
		if (availableLanguages.contains(language)) {
			return ResourceBundle.getBundle(PathUtility.LANGUAGE_PATH, language);
		}
		return ResourceBundle.getBundle(PathUtility.LANGUAGE_PATH, DEFAULT_LOCALE);
	}

	/**
	 * Convert a locale to its IETF BCP 47 string representation.
	 * 
	 * @param language
	 * 					- The locale to be converted
	 * 
	 * @return A String of the locale in IETF BCP 47 language tag representation
	 */
	public static final String toString(final Locale language) {
		return language.toLanguageTag();
	}

	/**
	 * Convert a language string in IETF BCP 47 representation to the correct corresponding locale.
	 * 
	 * @param language
	 * 					- The string to be converted
	 * 
	 * @return A Locale converted from the language string
	 */
	public static final Locale toLocale(final String language) {
		return Locale.forLanguageTag(language);
	}

}
