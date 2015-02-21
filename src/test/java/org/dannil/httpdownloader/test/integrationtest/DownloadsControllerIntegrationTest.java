package org.dannil.httpdownloader.test.integrationtest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.controller.DownloadsController;
import org.dannil.httpdownloader.exception.UnqualifiedAccessException;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IDownloadService;
import org.dannil.httpdownloader.service.IRegisterService;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * Integration tests for downloads controller
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
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

		final String secondPath = this.downloadsController.downloadsStartIdGET(session, registered.getDownloads().get(0).getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), secondPath);
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
	public final void getDownloadWithoutCorrespondingOnFileSystem() throws InterruptedException, IOException, UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		saved.addDownload(download);

		final ServletOutputStream stream = mock(ServletOutputStream.class);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, saved.getDownloads().get(0).getId());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public final void getDownloadByIdSuccess() throws InterruptedException, IOException, UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final Download savedDownload = this.downloadService.saveToDisk(download);

		Thread.sleep(1000);

		saved.addDownload(savedDownload);

		final ServletOutputStream stream = mock(ServletOutputStream.class);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getOutputStream()).thenReturn(stream);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, saved.getDownloads().get(0).getId());

		// if the test goes well, we should recieve null back as the path, as
		// getting
		// a download doesn't redirect us to any page.
		Assert.assertNull(path);
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void startDownloadByIdInjectionAttemptDownloadsUserIdDoesNotEqualSessionUserId() throws IOException, UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User injector = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		final User injectorSaved = this.registerService.save(injector);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);
		injectorSaved.addDownload(savedDownload);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsStartIdGET(session, injectorSaved.getDownloads().get(0).getId());
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void getDownloadByIdInjectionAttemptDownloadsUserIdDoesNotEqualSessionUserId() throws IOException, UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User injector = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		final User injectorSaved = this.registerService.save(injector);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);
		injectorSaved.addDownload(savedDownload);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsGetIdGET(response, session, injectorSaved.getDownloads().get(0).getId());
	}

	@Test
	public final void deleteDownloadByIdSuccess() throws UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());
		final User user = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		this.downloadsController.downloadsDeleteIdGET(session, saved.getDownloads().get(0).getId());

		Assert.assertNull(this.downloadService.findById(savedDownload.getId()));
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void deleteDownloadByIdInjectionAttemptDownloadsUserIdDoesNotEqualSessionUserId() throws UnqualifiedAccessException {
		final Download download = new Download(TestUtility.getDownload());

		final User user = new User(TestUtility.getUser());
		final User injector = new User(TestUtility.getUser());

		final User saved = this.registerService.save(user);
		final User injectorSaved = this.registerService.save(injector);

		final Download savedDownload = this.downloadService.save(download);

		saved.addDownload(savedDownload);
		injectorSaved.addDownload(savedDownload);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(saved);

		final String path = this.downloadsController.downloadsDeleteIdGET(session, injectorSaved.getDownloads().get(0).getId());
	}

}
