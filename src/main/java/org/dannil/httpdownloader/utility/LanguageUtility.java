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
public class LanguageUtility {

	private final Locale DEFAULT_LOCALE = new Locale("en", "US");

	private final List<Locale> availableLanguages;

	public LanguageUtility() {
		this.availableLanguages = new ArrayList<Locale>();
		this.availableLanguages.add(new Locale("en", "US"));
		this.availableLanguages.add(new Locale("sv", "SE"));
	}

	/**
	 * Returns a bundle with the language which matches the users current display language.
	 * @return ResourceBundle with default language
	 */
	public final ResourceBundle getBundle() {
		return this.getBundle(Locale.getDefault());
	}

	/**
	 * Returns a bundle with the language which matches the inputed locale.
	 * If the language file doesn't exist, return a standard language (enUS).
	 * @param language
	 * 					- The language to load
	 * @return ResourceBundle with inputed locale
	 */
	public final ResourceBundle getBundle(final Locale language) {
		if (this.availableLanguages.contains(language)) {
			return ResourceBundle.getBundle(PathUtility.LANGUAGE_PATH, language);
		}
		return ResourceBundle.getBundle(PathUtility.LANGUAGE_PATH, this.DEFAULT_LOCALE);
	}

}
