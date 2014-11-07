package org.dannil.httpdownloader.utility;

/**
 * Class for saving search paths to be used throughout the application
 * 
 * @author Daniel
 *
 */
public final class PathUtility {

	// Web root
	public static final String WEB_ROOT = "/WEB-INF";

	// Configuration folder
	public static final String CONFIGURATION_PATH = WEB_ROOT + "/conf";

	// View folder
	public static final String VIEW_PATH = WEB_ROOT + "/view";
	public static final String VIEW_DOWNLOADS_PATH = VIEW_PATH + "/downloads";

	// Languages folder
	public static final String LANGUAGES_PATH = CONFIGURATION_PATH + "/languages";
	public static final String LANGUAGE_PATH = LANGUAGES_PATH + "/language";

	// URL inside webapp
	public static final String URL_DOWNLOADS = "/downloads";
	public static final String URL_DOWNLOADS_ADD = URL_DOWNLOADS + "/add";

	public static final String URL_INDEX = "/index";

	public static final String URL_LOGIN = "/login";

	/**
	 * Private constructor to make the class a true singleton
	 */
	private PathUtility() {
		throw new AssertionError();
	}

}
