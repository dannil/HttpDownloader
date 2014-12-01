package org.dannil.httpdownloader.utility;

import org.dannil.httpdownloader.controller.IndexController;

/**
 * Class for saving paths to be used throughout the application.
 * 
 * @author Daniel Nilsson
 */
public final class PathUtility {

	// --- PATHS INSIDE WEBAPP --- //

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

	// URL webapp
	public static final String URL_DOWNLOADS = "/downloads";
	public static final String URL_DOWNLOADS_ADD = URL_DOWNLOADS + "/add";

	public static final String URL_INDEX = "/index";

	public static final String URL_LOGIN = "/login";

	// PATHS WHICH MAY BE OUTSIDE WEBAPP

	// Downloads path
	// This path will be the folder where the application saves the downloads
	// to. This may be outside the webapp folder, but be sure that the webapp
	// has sufficient rights to write to the specified directory.
	public static final String DOWNLOADS_PATH = IndexController.class.getClassLoader().getResource("").getPath() + "../downloads";

	private PathUtility() {
		throw new AssertionError();
	}

}
