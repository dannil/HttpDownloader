package org.dannil.httpdownloader.utility;

/**
 * Class for saving paths to be used throughout the application.
 * 
 * @author Daniel Nilsson
 */
public final class PathUtility {

	// --- PATHS INSIDE WEBAPP --- //

	// PATHS WHICH MAY BE OUTSIDE WEBAPP

	/**
	* This path will be the folder where the application saves the downloads
	* to. This may be outside the webapp, but be sure that the webapp
	* has sufficient rights to write to the specified directory.
	*/
	private static final String PATH_DOWNLOADS = "downloads";

	private static final XMLUtility xmlUtility;

	static {
		xmlUtility = new XMLUtility(getAbsolutePath() + "WEB-INF/configuration/config.xml");
	}

	private PathUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	public static final String getAbsolutePath() {
		return Thread.currentThread().getContextClassLoader().getResource("").getPath();
	}

	public static final String getAbsolutePathToConfiguration() {
		return getAbsolutePath() + xmlUtility.getElementValue("/configuration/project/paths/configuration");
	}

	// public static final String getAbsolutePathToAppConfiguration() {
	// return getAbsolutePath() +
	// xmlUtility.getElementValue("/configuration/app/paths/configuration");
	// }

	public static final String getAbsolutePathToProperties() {
		return getAbsolutePath() + xmlUtility.getElementValue("/configuration/app/paths/properties");
	}

	public static final String getAbsolutePathToLanguage() {
		return getAbsolutePath() + xmlUtility.getElementValue("/configuration/app/paths/language");
	}

	public static final String getAbsolutePathToDownloads() {
		return getAbsolutePath() + xmlUtility.getElementValue("/configuration/app/paths/downloads");
	}

}
