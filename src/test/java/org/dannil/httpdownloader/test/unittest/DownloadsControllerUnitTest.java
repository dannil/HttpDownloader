package org.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.controller.DownloadsController;
import org.dannil.httpdownloader.exception.UnqualifiedAccessException;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.dannil.httpdownloader.utility.URLUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

/**
 * Unit tests for downloads controller
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadsControllerUnitTest {

	@Autowired
	private DownloadsController downloadsController;

	@Test
	public final void loadDownloadsPage() {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final HttpServletRequest request = mock(HttpServletRequest.class);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		final String path = this.downloadsController.downloadsGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.DOWNLOADS), path);
	}

	@Test
	public final void loadAddDownloadsPage() {
		final User user = new User(TestUtility.getUser());
		final HttpServletRequest request = mock(HttpServletRequest.class);

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		final String path = this.downloadsController.downloadsAddGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.DOWNLOADS_ADD), path);
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

	@Test(expected = UnqualifiedAccessException.class)
	public final void validateRequestNullDownload() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = null;

		this.downloadsController.validateRequest(user, download);
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void validateRequestNullUserOnDownload() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		download.setUser(null);

		this.downloadsController.validateRequest(user, download);
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void validateRequestNonMatchingId() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		download.setUser(user);

		final User attempt = new User(user);
		attempt.setId(attempt.getId() + 1);

		this.downloadsController.validateRequest(attempt, download);
	}

	@Test
	public final void validateRequestMatchingId() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		download.setUser(user);

		this.downloadsController.validateRequest(user, download);
	}

	@Test
	public final void handleUnqualifiedAccessException() {
		final String path = this.downloadsController.handleUnqualifiedAccessException(new UnqualifiedAccessException());

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

}
