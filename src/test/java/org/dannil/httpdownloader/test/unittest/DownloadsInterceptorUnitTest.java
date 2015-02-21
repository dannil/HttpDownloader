package org.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.interceptor.DownloadsInterceptor;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.test.utility.TestUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

/**
 * Unit tests for downloads interceptor
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadsInterceptorUnitTest {

	@Autowired
	private DownloadsInterceptor downloadsInterceptor;

	@Test
	public final void userAttributeIsNullAtInterceptor() throws Exception {
		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("user")).thenReturn(null);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final boolean result = this.downloadsInterceptor.preHandle(request, response, null);

		Assert.assertFalse(result);
	}

	@Test
	public final void userAttributeIsNotNullAtInterceptor() throws Exception {
		final User user = new User(TestUtility.getUser());

		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);
		when(request.getSession().getAttribute("user")).thenReturn(user);

		final HttpServletResponse response = mock(HttpServletResponse.class);

		final boolean result = this.downloadsInterceptor.preHandle(request, response, null);

		Assert.assertTrue(result);
	}

	@Test
	public final void postHandleOnDownloads() throws Exception {
		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = new Object();
		final ModelAndView modelAndView = new ModelAndView();

		this.downloadsInterceptor.postHandle(request, response, handler, modelAndView);
	}

}
