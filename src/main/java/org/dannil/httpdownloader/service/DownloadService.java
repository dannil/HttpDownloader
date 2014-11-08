package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;

public final class DownloadService implements IDownloadService {

	@Autowired
	DownloadRepository repository;

	@Override
	public final Download findById(final long id) {
		return this.repository.findById(id);
	}

}
