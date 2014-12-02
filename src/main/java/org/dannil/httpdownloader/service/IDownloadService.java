package org.dannil.httpdownloader.service;

import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;

/**
 * Interface for DownloadService.
 * 
 * @author Daniel Nilsson
 */
public interface IDownloadService {

	// Others, defined in DownloadRepository
	public Download findById(final long downloadId);

	public LinkedList<Download> findByUser(final User user);

	public Download save(final Download download);

	public void delete(final Download download);

	public void delete(final long downloadId);

	// Delegated to DownloadService
	public Download saveToDisk(final Download download);

}
