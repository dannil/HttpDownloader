package com.github.dannil.httpdownloader.test.utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

public final class TestUtility {

	// TODO Change this to a URL pointing to the project repo
	private static final String DOWNLOAD_URL = "https://raw.githubusercontent.com/dannil/HttpDownloader/dev/resources/TEST_MAVEN_README.md";

	private static long USER_ID = 1;
	private static long DOWNLOAD_ID = 1;

	private static final List<User> users;
	private static final List<Download> downloads;

	static {
		users = new LinkedList<User>();
		User user = new User("example@example.com", "test", "exampleFirst", "exampleLast");
		user.setId(USER_ID);
		users.add(user);

		USER_ID++;

		downloads = new LinkedList<Download>();
		final Download download = new Download("README", DOWNLOAD_URL, new DateTime(), new DateTime(), null);
		download.setId(DOWNLOAD_ID);
		downloads.add(download);

		DOWNLOAD_ID++;
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
