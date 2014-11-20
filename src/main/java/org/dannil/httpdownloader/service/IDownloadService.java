package org.dannil.httpdownloader.service;

import java.net.URL;

import org.dannil.httpdownloader.model.Download;

public interface IDownloadService {

	// Others, defined in DownloadRepository
	public Download findById(final long id);

	public Download save(final Download download);

	// Delegated to DownloadService
	public String getContentFromURL(final URL url);

}
