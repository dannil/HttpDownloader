package org.dannil.httpdownloader.utility;

/**
 * Class for saving paths to be used throughout the application.
 * 
 * @author Daniel Nilsson
 */
public final class PathUtility {

	// --- PATHS INSIDE WEBAPP --- //

	// Web root
	public static final String WEB_ROOT = "WEB-INF";

	// Configuration folder
	public static final String PATH_CONFIGURATION = WEB_ROOT + "/conf";

	// Properties
	public static final String PATH_PROPERTIES = PATH_CONFIGURATION + "/properties";

	// Languages folder
	public static final String PATH_LANGUAGE = PATH_PROPERTIES + "/language";

	// View folder
	public static final String PATH_VIEW = WEB_ROOT + "/view";
	public static final String PATH_VIEW_DOWNLOADS = PATH_VIEW + "/downloads";

	// URL webapp
	public static final String URL_DOWNLOADS = "/downloads";
	public static final String URL_DOWNLOADS_ADD = URL_DOWNLOADS + "/add";

	public static final String URL_INDEX = "/index";

	public static final String URL_LOGIN = "/login";

	public static final String URL_REGISTER = "/register";

	// PATHS WHICH MAY BE OUTSIDE WEBAPP

	/**
	* This path will be the folder where the application saves the downloads
	* to. This may be outside the webapp, but be sure that the webapp
	* has sufficient rights to write to the specified directory.
	*/
	private static final String PATH_DOWNLOADS = "downloads";

	private PathUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	public static final String getAbsolutePath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	public static final String getAbsolutePathToConfiguration() {
		return getAbsolutePath() + PATH_CONFIGURATION;
	}

	public static final String getAbsolutePathToProperties() {
		return getAbsolutePath() + PATH_PROPERTIES;
	}

	public static final String getAbsolutePathToLanguage() {
		return getAbsolutePath() + PATH_LANGUAGE;
	}

	public static final String getAbsolutePathToDownloads() {
		return getAbsolutePath() + PATH_DOWNLOADS;
	}

}
