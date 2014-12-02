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
 * @author Daniel Nilsson
 */
@Service(value = "DownloadService")
public final class DownloadService implements IDownloadService {

	// private final static Logger LOGGER =
	// Logger.getLogger(DownloadService.class.getName());

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
	 * @see org.dannil.httpdownloader.repository.DownloadRepository#findByUserOrderByDownloadIdDesc(User)
	 */
	@Override
	public final LinkedList<Download> findByUser(final User user) {
		return new LinkedList<Download>(this.downloadRepository.findByUserOrderByDownloadIdDesc(user));
	}

	/**
	 * Delete a persisted download which matches the specified download.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#delete(Object)
	 */
	@Override
	public final void delete(final Download download) {
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
	public Download saveToDisk(final Download download) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
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

		return download;
	}

}
