package org.dannil.httpdownloader.utility;

import org.dannil.httpdownloader.controller.IndexController;

/**
 * Class for saving paths to be used throughout the application.
 * 
 * @author Daniel Nilsson
 */
public final class PathUtility {

	// --- PATHS INSIDE WEBAPP --- //

	/**
	 * Absolute root for the webapp. This path resolves to the WEB-INF-folder inside the classpath.
	 */
	public static final String APP_ROOT = IndexController.class.getClassLoader().getResource("").getPath() + "..";

	// Web root
	public static final String WEB_ROOT = "/WEB-INF";
	public static final String ABSOLUTE_WEB_ROOT = APP_ROOT + "/WEB-INF";

	// Configuration folder
	public static final String PATH_CONFIGURATION = WEB_ROOT + "/conf";
	public static final String ABSOLUTE_PATH_CONFIGURATION = APP_ROOT + "/conf";

	// Properties
	public static final String PATH_PROPERTIES = PATH_CONFIGURATION + "/properties";
	public static final String ABSOLUTE_PATH_PROPERTIES = ABSOLUTE_PATH_CONFIGURATION + "/properties";

	// Languages folder
	public static final String PATH_LANGUAGE = PATH_PROPERTIES + "/language";
	public static final String ABSOLUTE_PATH_LANGUAGE = ABSOLUTE_PATH_PROPERTIES + "/language";

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
	public static final String PATH_DOWNLOADS = APP_ROOT + "/downloads";

	private PathUtility() {
		throw new UnsupportedOperationException();
	}

}
