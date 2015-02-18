package org.dannil.httpdownloader.handler;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.repository.DownloadRepository;
import org.dannil.httpdownloader.utility.FileUtility;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Middleware class that acts between the service layer and the persistence layer. This class
 * makes sure that several downloads can be initiated at once, each one in a separate thread.
 * 
 * @author 		Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       1.0.0
 */
@Component
public final class DownloadThreadHandler {

	final static Logger LOGGER = Logger.getLogger(DownloadThreadHandler.class.getName());

	private static List<Thread> threads;

	@Autowired
	private DownloadSaveToDisk saveToDisk;

	@Autowired
	private DownloadDeleteFromDisk deleteFromDisk;

	public DownloadThreadHandler() {
		if (threads == null) {
			threads = new LinkedList<Thread>();
		}
	}

	public final void saveToDisk(final Download download) {
		if (download == null) {
			throw new NullPointerException("Download can't be null");
		}

		this.saveToDisk.setDownload(download);

		final Thread t = new Thread(this.saveToDisk, download.getFormat());

		threads.add(t);

		t.start();
	}

	public final void deleteFromDisk(final Download download) {
		if (download == null) {
			throw new NullPointerException("Download can't be null");
		}

		this.deleteFromDisk.setDownload(download);

		final Thread t = new Thread(this.deleteFromDisk, download.getFormat());

		threads.add(t);

		t.start();
	}

	/**
	 * Interrupts a thread with the specified name.
	 * 
	 * @param threadName the name of the thread to interrupt
	 */
	public final void interrupt(final String threadName) {
		for (final Thread t : threads) {
			if (t.getName().equals(threadName)) {
				LOGGER.info("Found thread " + threadName + ", interrupting...");
				t.interrupt();

				threads.remove(t);
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

		FileUtility.deleteFromDrive(this.download);
	}

	// public final Download getDownload() {
	// return this.download;
	// }

	public final void setDownload(final Download download) {
		this.download = download;
	}

}