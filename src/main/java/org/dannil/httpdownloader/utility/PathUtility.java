// Author: 	Daniel Nilsson
// Date: 	2014-08-21
// Changed: 2014-11-02

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

	// Languages folder
	public static final String LANGUAGES_PATH = CONFIGURATION_PATH + "/languages";
	public static final String LANGUAGE_PATH = LANGUAGES_PATH + "/language";

}
