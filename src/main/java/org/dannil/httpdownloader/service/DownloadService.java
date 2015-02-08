package org.dannil.httpdownloader.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.dannil.httpdownloader.utility.FileUtility;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles back end operations for downloads.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Service(value = "DownloadService")
public final class DownloadService implements IDownloadService {

	final static Logger LOGGER = Logger.getLogger(DownloadService.class.getName());

	@Autowired
	DownloadRepository downloadRepository;

	/**
	 * Find a download by it's id.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public final Download findById(final long downloadId) {
		return this.downloadRepository.findOne(downloadId);
	}

	/**
	 * Find downloads for the specified user.
	 * 
	 * @see org.dannil.httpdownloader.repository.DownloadRepository#findByUser(User)
	 */
	@Override
	public final LinkedList<Download> findByUser(final User user) {
		return new LinkedList<Download>(this.downloadRepository.findByUser(user));
	}

	/**
	 * Delete a persisted download which matches the specified download.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(Object)
	 */
	@Override
	public final void delete(final Download download) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					FileUtility.deleteFromDrive(download);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();

		this.downloadRepository.delete(download);
	}

	/**
	 * Delete a persisted download with the specified id.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(Object)
	 * 
	 */
	@Override
	public final void delete(final long downloadId) {
		this.downloadRepository.delete(downloadId);
	}

	/**
	 * Persist the specified download.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#save(Object)
	 */
	@Override
	public final Download save(final Download download) {
		return this.downloadRepository.save(download);
	}

	/**
	 * Initiate the specified download and save it to the disk.
	 * 
	 * @see org.dannil.httpdownloader.utility.FileUtility#saveToDrive(File)
	 */
	@Override
	public final Download saveToDisk(final Download download) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				final File file;
				try {
					LOGGER.info("Trying to save download...");
					file = FileUtility.getFileFromURL(download);
					FileUtility.saveToDrive(file);
					download.setEndDate(new DateTime());
					save(download);
				} catch (IOException e) {
					e.printStackTrace();
				}
				;
			}
		});
		t.start();

		return download;
	}

	/**
	 * Display a download dialog to the user.
	 * 
	 * @param context
	 * 					the current servlet context
	 * @param response
	 * 					the response to serve the dialog to
	 * @param download
	 * 					the download to serve
	 * 
	 * @throws IOException
	 * 						if the download for some reason can't be found
	 */
	@Override
	public final void serveDownload(final ServletContext context, final HttpServletResponse response, final Download download) throws IOException {
		final File file = FileUtility.getFromDrive(download);

		try (FileInputStream inStream = new FileInputStream(file)) {
			String mimeType = context.getMimeType(file.getAbsolutePath());
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}

			// modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) file.length());

			// forces download
			final String headerKey = "Content-Disposition";
			final String headerValue = String.format("attachment; filename=\"%s\"", download.getFilename());
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			final OutputStream outStream = response.getOutputStream();

			final byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		}
	}

}
