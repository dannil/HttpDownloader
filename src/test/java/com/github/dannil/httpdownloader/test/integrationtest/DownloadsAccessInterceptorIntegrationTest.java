package com.github.dannil.httpdownloader.test.integrationtest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.dannil.httpdownloader.exception.UnqualifiedAccessException;
import com.github.dannil.httpdownloader.interceptor.DownloadsAccessInterceptor;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for downloads access interceptor
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public final class DownloadsAccessInterceptorIntegrationTest {

	@Autowired
	private DownloadsAccessInterceptor downloadsAccessInterceptor;

	@Test(expected = UnqualifiedAccessException.class)
	public final void validateRequestNullDownload() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());

		final Map<String, String> map = new HashMap<String, String>();
		map.put("id", "1");

		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final Object handler = mock(Object.class);

		this.downloadsAccessInterceptor.preHandle(request, response, handler);
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void validateRequestDownloadHasNullUser() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		download.setUser(null);

		final Map<String, String> map = new HashMap<String, String>();
		map.put("id", download.getId().toString());

		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final Object handler = mock(Object.class);

		this.downloadsAccessInterceptor.preHandle(request, response, handler);
	}

	@Test(expected = UnqualifiedAccessException.class)
	public final void validateRequestNonMatchingId() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final User attempt = new User(user);
		attempt.setId(user.getId() + 1);

		final Map<String, String> map = new HashMap<String, String>();
		map.put("id", download.getId().toString());

		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(attempt);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final Object handler = mock(Object.class);

		this.downloadsAccessInterceptor.preHandle(request, response, handler);
	}

	@Test
	public final void validateRequestMatchingId() throws UnqualifiedAccessException {
		final User user = new User(TestUtility.getUser());
		final Download download = new Download(TestUtility.getDownload());

		user.addDownload(download);

		final Map<String, String> map = new HashMap<String, String>();
		map.put("id", download.getId().toString());

		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("user")).thenReturn(user);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final Object handler = mock(Object.class);

		final boolean result = this.downloadsAccessInterceptor.preHandle(request, response, handler);

		Assert.assertTrue(result);
	}

	@Test
	public final void postHandle() throws Exception {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = mock(Object.class);
		final ModelAndView modelAndView = mock(ModelAndView.class);

		this.downloadsAccessInterceptor.postHandle(request, response, handler, modelAndView);
	}

}