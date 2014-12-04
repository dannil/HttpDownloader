package org.dannil.httpdownloader.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.dannil.httpdownloader.model.Download;

/**
 * Class which handles operations on files.
 * 
 * @author Daniel Nilsson
 */
public class FileUtility {

	private FileUtility() {
		throw new UnsupportedOperationException(ResourceUtility.getErrorBundle().getString("disallowed_instantiation"));
	}

	/** 
	 * Get a file from the specified download's URL.
	 * 
	 * @param download
	 * 					- The download to fetch the URL from
	 * 
	 * @return A file with the contents of the specified URL
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
	 * 				- The file to be saved
	 * 
	 * @return A file which reflects the file that was saved to the disk
	 * 
	 * @throws IOException 
	 * 				if the file couldn't be saved
	 */
	public static final File saveToDrive(final File file) throws IOException {
		final File destination = new File(PathUtility.DOWNLOADS_PATH);

		FileUtils.copyFileToDirectory(file, destination);

		return file;
	}

}
