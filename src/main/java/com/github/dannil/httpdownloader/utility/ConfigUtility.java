package com.github.dannil.httpdownloader.utility;

import com.github.dannil.httpdownloader.exception.ConfigException;

/**
 * Class which acts as a middle-layer between the config file and the Java code.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public final class ConfigUtility {

    private static final XMLUtility XML_UTILITY;

    static {
        XML_UTILITY = new XMLUtility(getConfigFileAbsolutePath());
    }

    private ConfigUtility() throws IllegalAccessException {
        throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
    }

    /**
     * Get the absolute path of the classpath.
     *
     * @return the absolute path of the classpath
     */
    public static String getAbsolutePath() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null && loader.getResource("") != null) {
            return loader.getResource("").getPath();
        }
        throw new ConfigException("Classloader for thread is unavailable");
    }

    /**
     * Get the absolute path to the config file.
     *
     * @return the absolute path to the config file
     */
    public static String getConfigFileAbsolutePath() {
        return getAbsolutePath() + "/configuration/config.xml";
    }

    /**
     * Get the relative path to the configuration folder.
     *
     * @return the relative path to the configuration folder
     */
    public static String getConfigurationRelativePath() {
        return XML_UTILITY.getElementValue("/configuration/project/paths/configuration");
    }

    /**
     * Get the absolute path to the configuration folder.
     *
     * @return the absolute path to the configuration folder
     */
    public static String getConfigurationAbsolutePath() {
        return getAbsolutePath() + getConfigurationRelativePath();
    }

    /**
     * Get the relative path to the properties folder.
     *
     * @return the relative path to the properties folder
     */
    public static String getPropertiesRelativePath() {
        return XML_UTILITY.getElementValue("/configuration/app/paths/properties");
    }

    /**
     * Get the absolute path to the properties folder.
     *
     * @return the absolute path to the properties folder
     */
    public static String getPropertiesAbsolutePath() {
        return getAbsolutePath() + getPropertiesRelativePath();
    }

    /**
     * Get the relative path to the downloads folder.
     *
     * @return the relative path to the downloads folder
     */
    public static String getDownloadsRelativePath() {
        return XML_UTILITY.getElementValue("/configuration/app/paths/downloads");
    }

    /**
     * Get the absolute path to the downloads folder.
     *
     * @return the absolute path to the downloads folder
     */
    public static String getDownloadsAbsolutePath() {
        return getAbsolutePath() + getDownloadsRelativePath();
    }

    /**
     * Get the default language of the application.
     *
     * @return the default language of the application
     */
    public static String getDefaultLanguage() {
        return XML_UTILITY.getElementValue("configuration/app/defaults/language");
    }
}
