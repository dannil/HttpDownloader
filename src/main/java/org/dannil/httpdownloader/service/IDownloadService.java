package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.Download;

public interface IDownloadService {

	// Others, defined in DownloadRepository
	public Download findById(final long id);

	// Delegated to DownloadService

}
