package com.github.dannil.httpdownloader.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.github.dannil.httpdownloader.model.Download;

/**
 * Class which handles operations on files.
 *
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class FileUtility {

    private static final Logger LOGGER = Logger.getLogger(FileUtility.class.getName());

    private FileUtility() throws IllegalAccessException {
        throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
    }

    /**
     * Get a file from the specified download's URL.
     *
     * @param download
     *            the download to fetch the URL from
     *
     * @return a file with the contents of the specified URL
     *
     * @throws IOException
     *             if the file can't be fetched
     */
    public static File getFileFromURL(Download download) throws IOException {
        String path = ConfigUtility.getDownloadsAbsolutePath() + "/" + download.getFormat();
        File file = new File(path);
        FileUtils.copyURLToFile(new URL(download.getUrl()), file, 5000, 5000);
        return file;
    }

    /**
     * Save a file to the disk.
     *
     * @param file
     *            the file to be saved
     *
     * @return a file which reflects the file that was saved to the disk
     *
     * @throws IOException
     *             if the file couldn't be saved
     */
    public static File saveToDrive(File file) throws IOException {
        File destination = new File(ConfigUtility.getDownloadsAbsolutePath());

        LOGGER.info("Saving download to " + ConfigUtility.getDownloadsAbsolutePath());

        FileUtils.copyFileToDirectory(file, destination);

        return file;
    }

    /**
     * Fetches a file from the disk.
     *
     * @param download
     *            the download to fetch
     *
     * @return a file which reflects the specified download
     */
    public static File getFromDrive(Download download) {
        return FileUtils.getFile(ConfigUtility.getDownloadsAbsolutePath(), download.getFormat());
    }

    /**
     * Deletes a file from the disk.
     *
     * @param download
     *            the download to delete
     *
     * @return true if the download could be deleted; otherwise false
     */
    public static boolean deleteFromDrive(Download download) {
        String path = ConfigUtility.getDownloadsAbsolutePath() + "/" + download.getFormat();
        File file = new File(path);
        return file.delete();
    }

    /**
     * Returns all properties with the specified path.
     *
     * @param path
     *            the path of the properties
     *
     * @return a list of Properties
     *
     * @throws IOException
     *             if the properties file couldn't be found
     *
     * @see com.github.dannil.httpdownloader.utility.FileUtility#getProperties(String,
     *      String)
     */
    public static List<Properties> getProperties(String path) throws IOException {
        return getProperties(path, "");
    }

    /**
     * Returns all properties with the specified path and the specified starting string in
     * a list.
     *
     * @param path
     *            the path of the properties
     * @param startsWith
     *            the string pattern which the property should start with
     *
     * @return a list of Properties
     *
     * @throws IOException
     *             if the properties file couldn't be found
     */
    public static List<Properties> getProperties(String path, String startsWith) throws IOException {
        List<Properties> properties = new ArrayList<>();
        File[] files = new File(path).listFiles();

        if (files == null) {
            throw new IOException();
        }
        for (File f : files) {
            if (f.getName().startsWith(startsWith) && f.getName().endsWith(".properties")) {
                try (FileInputStream inputStream = new FileInputStream(path + "/" + f.getName())) {
                    Properties prop = new Properties();
                    prop.load(inputStream);
                    properties.add(prop);
                }
            }
        }
        return properties;
    }
}
