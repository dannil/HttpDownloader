package com.github.dannil.httpdownloader.test.utility;

import org.joda.time.DateTime;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

public class TestUtility {

	private static String DOWNLOAD_URL = "https://raw.githubusercontent.com/dannil/HttpDownloader/dev/resources/TEST_MAVEN_README.md";

	private static long USER_ID = 1;
	private static long DOWNLOAD_ID = 1;

	public static User getUser() {
		User user = new User("example" + USER_ID + "@example.com", "test", "exampleFirst", "exampleLast");
		user.setId(USER_ID);

		USER_ID++;

		User returnUser = TestUtility.deepCopy(user);
		return returnUser;
	}

	public static Download getDownload() {
		Download download = new Download("README", DOWNLOAD_URL, new DateTime(), new DateTime(), null);
		download.setId(DOWNLOAD_ID);

		DOWNLOAD_ID++;

		Download returnDownload = TestUtility.deepCopy(download);
		return returnDownload;
	}

	public static User deepCopy(User user) {
		User returnUser = new User(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(),
				user.getDownloads());
		returnUser.setId(user.getId());
		return returnUser;
	}

	public static Download deepCopy(Download download) {
		Download returnDownload = new Download(download.getTitle(), download.getUrl(), download.getStartDate(),
				download.getEndDate(), download.getUser());
		returnDownload.setId(download.getId());
		return returnDownload;
	}

}
