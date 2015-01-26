package org.dannil.httpdownloader.test.utility;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;

public final class TestUtility {

	private static final String DOWNLOAD_URL = "http://dannils.se/pi.zip";

	private static long USER_ID = 1;
	private static long DOWNLOAD_ID = 1;

	private static final List<User> users;
	private static final List<Download> downloads;

	static {
		users = new LinkedList<User>();
		User user = new User("example@example.com", "test", "exampleFirst", "exampleLast");
		users.add(user);

		downloads = new LinkedList<Download>();
		final Download download = new Download("pi", DOWNLOAD_URL, new Date(), new Date(), user);
		downloads.add(download);
	}

	public static final User getUser() {
		Random random = new Random();
		final User user = users.get(random.nextInt(users.size()));
		user.setId(USER_ID);
		user.setEmail("example" + USER_ID + "@example.com");
		USER_ID++;

		return user;
	}

	public static final Download getDownload() {
		Random random = new Random();
		Download download = downloads.get(random.nextInt(downloads.size()));
		download.setId(DOWNLOAD_ID);
		DOWNLOAD_ID++;

		return download;
	}
}
