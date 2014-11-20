package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles backend operations for downloads.
 * 
 * @author Daniel
 */
@Service(value = "DownloadService")
public final class DownloadService implements IDownloadService {

	@Autowired
	private DownloadRepository downloadRepository;

	@Override
	public final Download findById(final long id) {
		return this.downloadRepository.findByDownloadId(id);
	}

	@Override
	public final Download save(final Download download) {
		return this.downloadRepository.save(download);
	}

}
