package org.dannil.httpdownloader.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;

public interface IDownloadService {

	// Others, defined in DownloadRepository
	public Download findByDownloadId(final long downloadId);

	public LinkedList<Download> findByUserId(final long userId);

	public Download save(final User user, final Download download);

	// Delegated to DownloadService
	public File getFileFromURL(final Download download) throws IOException;

	public File saveToDrive(final File file) throws IOException;

}
