package com.github.dannil.httpdownloader.test.integrationtest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.controller.DownloadsController;
import com.github.dannil.httpdownloader.exception.UnqualifiedAccessException;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IDownloadService;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Integration tests for downloads controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class DownloadsControllerIntegrationTest {

	@Autowired
	private DownloadsController downloadsController;

	@Autowired
	private IDownloadService downloadService;

	@Autowired
	private IRegisterService registerService;

	@Test
	public final void startDownloadExistingOnFileSystem() throws InterruptedException, UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User registered = this.registerService.save(user);

		download.setUser(registered);

		final Download saved = this.downloadService.save(download);

		registered.addDownload(saved);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(registered);

		this.downloadsController.downloadsStartIdGET(session, registered.getDownloads().get(0).getId());

		Thread.sleep(1000);

		final String secondPath = this.downloadsController.downloadsStartIdGET(session, registered.getDownloads()
				.get(0).getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), secondPath);
	}

	@Test
	public final void addDownloadWithErrors() {
		final Download download = new Download(TestUtility.getDownload());

		download.setTitle(null);
		download.setUrl(null);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);
		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		final String path = this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS_ADD), path);
	}

	@Test
	public final void addDownloadStartDownloadingProcessAndValidateLandingPage() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn("start");

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		final String path = this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public final void addDownloadStartDownloadingProcess() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final DateTime startDate = download.getStartDate();

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn("start");

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(-1, startDate.compareTo(download.getStartDate()));
	}

	@Test
	public final void addDownloadDoNotStartDownloadingProcess() {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final DateTime startDate = download.getStartDate();

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn(null);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final BindingResult errors = new BeanPropertyBindingResult(download, "download");

		this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(0, startDate.compareTo(download.getStartDate()));
	}

	@Test
	public final void getDownloadWithoutCorrespondingOnFileSystem() throws InterruptedException, IOException,
			UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		saved.addDownload(download);

		final ServletOutputStream stream = mock(ServletOutputStream.class);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, saved.getDownloads().get(0)
				.getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public final void getDownloadByIdSuccess() throws InterruptedException, IOException, UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final Download savedDownload = this.downloadService.saveToDisk(download);
		saved.addDownload(savedDownload);

		Thread.sleep(1000);

		final ServletOutputStream stream = mock(ServletOutputStream.class);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, saved.getDownloads().get(0)
				.getId());

		// if the test goes well, we should recieve null back as the path, as
		// getting a download doesn't redirect us to any page.
		Assert.assertNull(path);
	}

	@Test
	public final void deleteDownloadByIdSuccess() throws InterruptedException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final Download savedDownload = this.downloadService.saveToDisk(download);

		Thread.sleep(1000);

		saved.addDownload(savedDownload);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsDeleteIdGET(session, download.getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

}
