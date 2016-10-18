package com.github.dannil.httpdownloader.utility;

import com.github.dannil.httpdownloader.exception.ConfigException;

/**
 * Class which acts as a middle-layer between the config file and
 * the Java code.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class ConfigUtility {

	private static final XMLUtility xmlUtility;

	static {
		xmlUtility = new XMLUtility(getConfigFileAbsolutePath());
	}

	private ConfigUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	public static String getAbsolutePath() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader != null && loader.getResource("") != null) {
			return loader.getResource("").getPath();
		}
		throw new ConfigException("Classloader for thread is unavailable");
	}

	public static String getConfigFileAbsolutePath() {
		return getAbsolutePath() + "WEB-INF/configuration/config.xml";
	}

	public static String getConfigurationRelativePath() {
		return xmlUtility.getElementValue("/configuration/project/paths/configuration");
	}

	public static String getConfigurationAbsolutePath() {
		return getAbsolutePath() + getConfigurationRelativePath();
	}

	// Currently unused
	// public static final String getAbsolutePathToAppConfiguration() {
	// return getAbsolutePath() +
	// xmlUtility.getElementValue("/configuration/app/paths/configuration");
	// }

	public static String getPropertiesRelativePath() {
		return xmlUtility.getElementValue("/configuration/app/paths/properties");
	}

	public static String getPropertiesAbsolutePath() {
		return getAbsolutePath() + getPropertiesRelativePath();
	}

	public static String getLanguageRelativePath() {
		return xmlUtility.getElementValue("/configuration/app/paths/language");
	}

	public static String getLanguageAbsolutePath() {
		return getAbsolutePath() + getLanguageRelativePath();
	}

	public static String getDownloadsRelativePath() {
		return xmlUtility.getElementValue("/configuration/app/paths/downloads");
	}

	public static String getDownloadsAbsolutePath() {
		return getAbsolutePath() + getDownloadsRelativePath();
	}

	public static String getDefaultLanguage() {
		return xmlUtility.getElementValue("configuration/app/defaults/language");
	}
}
