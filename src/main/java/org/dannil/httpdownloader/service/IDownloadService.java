package org.dannil.httpdownloader.service;

import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;

public interface IDownloadService {

	// Others, defined in DownloadRepository
	public Download findById(final long downloadId);

	public LinkedList<Download> findByUserId(final long userId);

	public Download save(final User user, final Download download);

}
