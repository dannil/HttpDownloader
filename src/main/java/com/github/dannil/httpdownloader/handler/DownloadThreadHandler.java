package com.github.dannil.httpdownloader.handler;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dannil.httpdownloader.exception.DownloadException;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.repository.DownloadRepository;
import com.github.dannil.httpdownloader.utility.FileUtility;

/**
 * Middleware class that acts between the service layer and the persistence layer. This class
 * makes sure that several downloads can be initiated at once, each one in a separate thread.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public final class DownloadThreadHandler {

	private final static Logger LOGGER = Logger.getLogger(DownloadThreadHandler.class.getName());

	private static DownloadThreadHandler downloadThreadHandlerInstance;

	private List<Thread> threads;

	@Autowired
	private DownloadSaveToDisk saveToDiskInstance;

	@Autowired
	private DownloadDeleteFromDisk deleteFromDiskInstance;

	public synchronized static DownloadThreadHandler getInstance() {
		if (downloadThreadHandlerInstance == null) {
			downloadThreadHandlerInstance = new DownloadThreadHandler();
		}
		return downloadThreadHandlerInstance;
	}

	private DownloadThreadHandler() {
		this.threads = new LinkedList<Thread>();
	}

	public final synchronized void saveToDisk(final Download download) {
		if (download == null) {
			throw new IllegalArgumentException("Download can't be null");
		}

		this.saveToDiskInstance.setDownload(download);

		final Thread t = new Thread(this.saveToDiskInstance, download.getFormat());

		this.threads.add(t);

		t.start();
	}

	public final synchronized void deleteFromDisk(final Download download) {
		if (download == null) {
			throw new IllegalArgumentException("Download can't be null");
		}

		this.deleteFromDiskInstance.setDownload(download);

		final Thread t = new Thread(this.deleteFromDiskInstance, download.getFormat());

		this.threads.add(t);

		t.start();
	}

	/**
	 * Interrupts a thread with the specified name.
	 * 
	 * @param threadName
	 *            the name of the thread to interrupt
	 */
	public final synchronized void interrupt(final String threadName) {
		for (final Thread t : this.threads) {
			if (t.getName().equals(threadName)) {
				LOGGER.info("Found thread " + threadName + ", interrupting...");
				t.interrupt();

				this.threads.remove(t);
				break;
			}
		}
	}

}

@Component
class DownloadSaveToDisk implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(DownloadSaveToDisk.class.getName());

	@Autowired
	private DownloadRepository repository;

	private Download download;

	private DownloadSaveToDisk() {

	}

	@Override
	public void run() {
		LOGGER.info("Trying to save download " + this.download.getFormat());

		File file;
		try {
			file = FileUtility.getFileFromURL(this.download);
			FileUtility.saveToDrive(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.download.setEndDate(new DateTime());
		this.repository.save(this.download);
	}

	// public final Download getDownload() {
	// return this.download;
	// }

	public final void setDownload(final Download download) {
		this.download = download;
	}

}

@Component
class DownloadDeleteFromDisk implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(DownloadSaveToDisk.class.getName());

	// @Autowired
	// private DownloadRepository repository;

	private Download download;

	private DownloadDeleteFromDisk() {

	}

	@Override
	public void run() {
		LOGGER.info("Trying to delete download " + this.download.getFormat());

		boolean isDeleted = FileUtility.deleteFromDrive(this.download);
		if (!isDeleted) {
			throw new DownloadException("Couldn't delete download " + this.download);
		}
	}

	// public final Download getDownload() {
	// return this.download;
	// }

	public final void setDownload(final Download download) {
		this.download = download;
	}

}