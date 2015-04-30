package org.dannil.httpdownloader.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;

/**
 * Class which handles operations on files.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
public class FileUtility {

	private final static Logger LOGGER = Logger.getLogger(FileUtility.class.getName());

	private FileUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	/** 
	 * Get a file from the specified download's URL.
	 * 
	 * @param download
	 * 					the download to fetch the URL from
	 * 
	 * @return a file with the contents of the specified URL
	 * 
	 * @throws IOException 
	 * 				if the file can't be fetched
	 */
	public static final File getFileFromURL(final Download download) throws IOException {
		final File file = new File(download.getFormat());
		FileUtils.copyURLToFile(new URL(download.getUrl()), file, 5000, 5000);
		return file;
	}

	/**
	 * Save a file to the disk.
	 * 
	 * @param file
	 * 				the file to be saved
	 * 
	 * @return a file which reflects the file that was saved to the disk
	 * 
	 * @throws IOException 
	 * 				if the file couldn't be saved
	 */
	public static final File saveToDrive(final File file) throws IOException {
		final File destination = new File(ConfigUtility.getDownloadsAbsolutePath());

		LOGGER.info("Saving download to " + ConfigUtility.getDownloadsAbsolutePath());

		FileUtils.copyFileToDirectory(file, destination);

		return file;
	}

	/**
	 * Fetches a file from the disk.
	 * 
	 * @param download
	 * 					the download to fetch
	 * 
	 * @return a file which reflects the specified download
	 */
	public static final File getFromDrive(final Download download) {
		File file = FileUtils.getFile(ConfigUtility.getDownloadsAbsolutePath(), download.getFormat());
		return file;
	}

	/**
	 * Deletes a file from the disk.
	 * 
	 * @param download
	 * 					the download to delete
	 */
	public static final void deleteFromDrive(final Download download) {
		final String path = ConfigUtility.getDownloadsAbsolutePath() + "/" + download.getFormat();
		final File file = new File(path);
		file.delete();
	}

	/**
	 * Returns all properties with the specified path.
	 * 
	 * @param path
	 * 				the path of the properties
	 * 
	 * @return a list of Properties
	 * 
	 * @throws IOException
	 * 						if the properties file couldn't be found
	 * 
	 * @see org.dannil.httpdownloader.utility.FileUtility#getProperties(String, String)
	 */
	public static final List<Properties> getProperties(final String path) throws IOException {
		return getProperties(path, "");
	}

	/**
	 * Returns all properties with the specified path and the specified starting string in a list.
	 * 
	 * @param path
	 * 						the path of the properties		
	 * @param startsWith
	 * 						the string pattern which the property should start with
	 * 	
	 * @return a list of Properties
	 * 
	 * @throws IOException
	 * 						if the properties file couldn't be found
	 */
	public static final List<Properties> getProperties(final String path, final String startsWith) throws IOException {
		final List<Properties> properties = new LinkedList<Properties>();
		final File[] files = new File(path).listFiles();

		for (final File file : files) {
			if (file.isFile() && file.getName().startsWith(startsWith) && file.getName().endsWith(".properties")) {
				try (FileInputStream inputStream = new FileInputStream(path + "/" + file.getName())) {
					Properties prop = new Properties();
					prop.load(inputStream);
					properties.add(prop);
					inputStream.close();
				}
			}
		}
		return properties;

	}
}
