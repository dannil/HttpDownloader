package com.github.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith; import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import com.github.dannil.httpdownloader.interceptor.DownloadsInterceptor;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for downloads interceptor
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class) @WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml", "classpath:/WEB-INF/configuration/framework/application-context.xml" })
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
