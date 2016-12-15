package com.github.dannil.httpdownloader.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
	public void startDownloadExistingOnFileSystem() throws InterruptedException, UnqualifiedAccessException {
		Download download = TestUtility.getDownload();

		User user = TestUtility.getUser();
		User registered = this.registerService.save(user);

		download.setUser(registered);

		Download saved = this.downloadService.save(download);

		registered.addDownload(saved);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(registered);

		this.downloadsController.downloadsStartIdGET(session, saved.getId());

		TimeUnit.SECONDS.sleep(1);

		String secondPath = this.downloadsController.downloadsStartIdGET(session, saved.getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), secondPath);
	}

	@Test
	public void addDownloadWithErrors() {
		Download download = TestUtility.getDownload();

		download.setTitle(null);
		download.setUrl(null);

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		BindingResult errors = new BeanPropertyBindingResult(download, "download");

		String path = this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS_ADD), path);
	}

	@Test
	public void addDownloadStartDownloadingProcessAndValidateLandingPage() {
		Download download = TestUtility.getDownload();
		User user = TestUtility.getUser();

		User saved = this.registerService.save(user);

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn("start");

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		BindingResult errors = new BeanPropertyBindingResult(download, "download");

		String path = this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public void addDownloadStartDownloadingProcess() {
		Download download = TestUtility.getDownload();
		User user = TestUtility.getUser();

		User saved = this.registerService.save(user);

		DateTime startDate = download.getStartDate();

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn("start");

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		BindingResult errors = new BeanPropertyBindingResult(download, "download");

		this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(-1, startDate.compareTo(download.getStartDate()));
	}

	@Test
	public void addDownloadDoNotStartDownloadingProcess() {
		Download download = TestUtility.getDownload();
		User user = TestUtility.getUser();

		User saved = this.registerService.save(user);

		DateTime startDate = download.getStartDate();

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter("start")).thenReturn(null);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		BindingResult errors = new BeanPropertyBindingResult(download, "download");

		this.downloadsController.downloadsAddPOST(request, session, download, errors);

		Assert.assertEquals(0, startDate.compareTo(download.getStartDate()));
	}

	@Test
	public void getDownloadWithoutCorrespondingOnFileSystem()
			throws InterruptedException, IOException, UnqualifiedAccessException {
		Download download = TestUtility.getDownload();
		User user = TestUtility.getUser();

		User saved = this.registerService.save(user);
		saved.addDownload(download);

		ServletOutputStream stream = mock(ServletOutputStream.class);

		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		String path = this.downloadsController.downloadsGetIdGET(response, session, download.getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public void getDownloadByIdSuccess() throws InterruptedException, IOException, UnqualifiedAccessException {
		Download download = TestUtility.getDownload();
		User user = TestUtility.getUser();

		User saved = this.registerService.save(user);

		Download savedDownload = this.downloadService.saveToDisk(download);
		saved.addDownload(savedDownload);

		TimeUnit.SECONDS.sleep(1);

		ServletOutputStream stream = mock(ServletOutputStream.class);

		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		String path = this.downloadsController.downloadsGetIdGET(response, session, savedDownload.getId());

		// if the test goes well, we should recieve null back as the path, as
		// getting a download doesn't redirect us to any page.
		Assert.assertNull(path);
	}

	@Test
	public void deleteDownloadByIdSuccess() throws InterruptedException {
		Download download = TestUtility.getDownload();
		User user = TestUtility.getUser();

		User saved = this.registerService.save(user);

		Download savedDownload = this.downloadService.saveToDisk(download);

		TimeUnit.SECONDS.sleep(1);

		saved.addDownload(savedDownload);

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		String path = this.downloadsController.downloadsDeleteIdGET(session, download.getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

}
