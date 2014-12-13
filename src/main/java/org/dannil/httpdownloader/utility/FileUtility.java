package org.dannil.httpdownloader.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;

/**
 * Class which handles operations on files.
 * 
 * @author Daniel Nilsson
 */
public class FileUtility {

	private final static Logger LOGGER = Logger.getLogger(FileUtility.class.getName());

	private FileUtility() {
		throw new UnsupportedOperationException();
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
		final File destination = new File(PathUtility.PATH_DOWNLOADS);

		LOGGER.info("Saving download to " + PathUtility.PATH_DOWNLOADS);

		FileUtils.copyFileToDirectory(file, destination);

		return file;
	}

	/**
	 * Deletes a file from the disk.
	 * 
	 * @param download
	 * 					the download to delete
	 * @throws IOException 
	 * 				if the file can't be found
	 */
	public static final void deleteFromDrive(final Download download) throws IOException {
		final String path = PathUtility.PATH_DOWNLOADS + "/" + download.getFormat();
		final File file = new File(path);
		FileUtils.forceDelete(file);
	}
}
