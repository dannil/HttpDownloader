package org.dannil.httpdownloader.utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class which handles operations on resource bundles.
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
		throw new UnsupportedOperationException();
	}

	/**
	 * Return a language bundle which matches the user's current display language.
	 * 
	 * @return a ResourceBundle with a collection of localized language strings, in the default language
	 */
	public static final ResourceBundle getLanguageBundle() {
		return getLanguageBundle(Locale.getDefault());
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
	public static final ResourceBundle getLanguageBundle(final Locale locale) {
		return getResourceBundle(PathUtility.PATH_LANGUAGE, locale);
	}

	/**
	 * Return a error bundle which matches the user's current display language.
	 * 
	 * @return a ResourceBundle with a collection of localized errors, in the default language
	 */
	public static final ResourceBundle getErrorBundle() {
		return getErrorBundle(Locale.getDefault());
	}

	/**
	 * Return a error bundle which matches the inputed locale.
	 * 
	 * @param locale
	 * 					the error file to load 
	 * 
	 * @return a ResourceBundle with a collection of localized errors, in the the inputed locale
	 * 
	 * @see org.dannil.httpdownloader.utility.ResourceUtility#getResourceBundle(String, Locale)
	 */
	private static final ResourceBundle getErrorBundle(final Locale locale) {
		return getResourceBundle(PathUtility.PATH_ERROR, locale);
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
	private static final ResourceBundle getResourceBundle(final String path, final Locale locale) {
		if (availableLanguages.contains(locale)) {
			return ResourceBundle.getBundle(path, locale);
		}
		return getResourceBundle(path, DEFAULT_LOCALE);
	}

}
