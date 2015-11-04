package com.github.dannil.httpdownloader.handler;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.repository.DownloadRepository;
import com.github.dannil.httpdownloader.utility.FileUtility;

/**
 * Middleware class that acts between the service layer and the persistence layer. This class
 * makes sure that several downloads can be initiated at once, each one in a separate thread.
 * 
 * @author 		Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@Component
public final class DownloadThreadHandler {

	final static Logger LOGGER = Logger.getLogger(DownloadThreadHandler.class.getName());

	private static DownloadThreadHandler downloadThreadHandler;

	private List<Thread> threads;

	@Autowired
	private DownloadSaveToDisk saveToDisk;

	@Autowired
	private DownloadDeleteFromDisk deleteFromDisk;

	public static DownloadThreadHandler getInstance() {
		if (downloadThreadHandler == null) {
			downloadThreadHandler = new DownloadThreadHandler();
		}
		return downloadThreadHandler;
	}

	private DownloadThreadHandler() {
		this.threads = new LinkedList<Thread>();
	}

	public final synchronized void saveToDisk(final Download download) {
		if (download == null) {
			throw new NullPointerException("Download can't be null");
		}

		this.saveToDisk.setDownload(download);

		final Thread t = new Thread(this.saveToDisk, download.getFormat());

		this.threads.add(t);

		t.start();
	}

	public final synchronized void deleteFromDisk(final Download download) {
		if (download == null) {
			throw new NullPointerException("Download can't be null");
		}

		this.deleteFromDisk.setDownload(download);

		final Thread t = new Thread(this.deleteFromDisk, download.getFormat());

		this.threads.add(t);

		t.start();
	}

	/**
	 * Interrupts a thread with the specified name.
	 * 
	 * @param threadName the name of the thread to interrupt
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

	final static Logger LOGGER = Logger.getLogger(DownloadSaveToDisk.class.getName());

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

	final static Logger LOGGER = Logger.getLogger(DownloadSaveToDisk.class.getName());

	@Autowired
	private DownloadRepository repository;

	private Download download;

	private DownloadDeleteFromDisk() {

	}

	@Override
	public void run() {
		LOGGER.info("Trying to delete download " + this.download.getFormat());

		boolean isDeleted = FileUtility.deleteFromDrive(this.download);
		if (!isDeleted) {
			throw new RuntimeException("Couldn't delete download " + this.download);
		}
	}

	// public final Download getDownload() {
	// return this.download;
	// }

	public final void setDownload(final Download download) {
		this.download = download;
	}

}