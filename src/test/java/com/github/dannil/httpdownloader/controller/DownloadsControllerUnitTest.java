package com.github.dannil.httpdownloader.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.controller.DownloadsController;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for downloads controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class DownloadsControllerUnitTest {

	@Autowired
	private DownloadsController downloadsController;

	@Test
	public void loadDownloadsPage() {
		User user = new User(TestUtility.getUser());
		Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		HttpServletRequest request = mock(HttpServletRequest.class);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		String path = this.downloadsController.downloadsGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.DOWNLOADS), path);
	}

	@Test
	public void loadAddDownloadsPage() {
		User user = new User(TestUtility.getUser());
		HttpServletRequest request = mock(HttpServletRequest.class);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		String path = this.downloadsController.downloadsAddGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.DOWNLOADS_ADD), path);
	}

}
