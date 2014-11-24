package org.dannil.httpdownloader.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.dannil.httpdownloader.model.Download;

/**
 * Class which handles operations on files.
 * 
 * @author Daniel Nilsson
 */
public class FileUtility {

	/** 
	 * Get a file from the specified downloads URL.
	 * 
	 * @param download
	 * 					- The download to fetch the URL from
	 * 
	 * @return A file with the contents of the specified URL
	 * 
	 * @throws IOException 
	 */
	public static final File getFileFromURL(final Download download) throws IOException {
		final String name = FilenameUtils.getBaseName(download.getUrl());
		final String extension = FilenameUtils.getExtension(download.getUrl());

		final File file = new File(download.hashCode() + "_" + name + "." + extension);
		FileUtils.copyURLToFile(new URL(download.getUrl()), file, 5000, 5000);
		return file;
	}

	public static final File saveToDrive(final File file) throws IOException {
		final File destination = new File(PathUtility.DOWNLOADS_PATH);

		FileUtils.copyFileToDirectory(file, destination);

		return file;
	}

}
