package org.dannil.httpdownloader.utility;

/**
 * Class for saving paths to be used throughout the application.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     0.0.1-SNAPSHOT
 * @since       0.0.1-SNAPSHOT
 */
public final class PathUtility {

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

	public static final String getRelativePathToConfiguration() {
		return xmlUtility.getElementValue("/configuration/project/paths/configuration");
	}

	public static final String getAbsolutePathToConfiguration() {
		return getAbsolutePath() + getRelativePathToConfiguration();
	}

	// Currently unused
	// public static final String getAbsolutePathToAppConfiguration() {
	// return getAbsolutePath() +
	// xmlUtility.getElementValue("/configuration/app/paths/configuration");
	// }

	public static final String getRelativePathToProperties() {
		return xmlUtility.getElementValue("/configuration/app/paths/properties");
	}

	public static final String getAbsolutePathToProperties() {
		return getAbsolutePath() + getRelativePathToProperties();
	}

	public static final String getRelativePathToLanguage() {
		return xmlUtility.getElementValue("/configuration/app/paths/language");
	}

	public static final String getAbsolutePathToLanguage() {
		return getAbsolutePath() + getRelativePathToLanguage();
	}

	public static final String getRelativePathToDownloads() {
		return xmlUtility.getElementValue("/configuration/app/paths/downloads");
	}

	public static final String getAbsolutePathToDownloads() {
		return getAbsolutePath() + getRelativePathToDownloads();
	}
}
