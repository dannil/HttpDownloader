package com.github.dannil.httpdownloader.service;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for DownloadService.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
public interface IDownloadService {

	// Others, defined in DownloadRepository
	public Download findById(final long downloadId);

	public LinkedList<Download> findByUser(final User user);

	public Download save(final Download download);

	public void delete(final Download download);

	public void delete(final long downloadId);

	// Delegated to DownloadService
	/**
	 * Initiate the specified download and save it to the disk.
	 * 
	 * @param download
	 * 					the download to save
	 * 
	 * @return the saved download
	 */
	public Download saveToDisk(final Download download);

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
	public void serveDownload(final ServletContext context, final HttpServletResponse response, final Download download) throws IOException;

}