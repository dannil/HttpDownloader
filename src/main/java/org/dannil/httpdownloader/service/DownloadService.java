package org.dannil.httpdownloader.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.dannil.httpdownloader.utility.FileUtility;
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
	DownloadRepository downloadRepository;

	@Override
	public final Download findById(final long downloadId) {
		return this.downloadRepository.findOne(downloadId);
	}

	@Override
	public final LinkedList<Download> findByUserId(final long userId) {
		return new LinkedList<Download>(this.downloadRepository.findByUserId(userId));
	}

	@Override
	public final void delete(final Download download) {
		this.downloadRepository.delete(download);
	}

	@Override
	public void delete(final long downloadId) {
		this.downloadRepository.delete(downloadId);
	}

	@Override
	public final Download save(final User user, final Download download) {
		Download tempDownload = null;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Download tempDownload = download;
				tempDownload.setUserId(user.getUserId());
				tempDownload = DownloadService.this.downloadRepository.save(tempDownload);

				final File file;
				try {
					file = FileUtility.getFileFromURL(download);
					FileUtility.saveToDrive(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				;
			}
		});
		t.start();

		return tempDownload;
	}

}
