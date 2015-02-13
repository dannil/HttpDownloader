package org.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.interceptor.AccessInterceptor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class AccessInterceptorUnitTest {

	@Autowired
	private AccessInterceptor accessInterceptor;

	@Test
	public final void preHandleOnAccess() throws Exception {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = new Object();

		final boolean result = this.accessInterceptor.preHandle(request, response, handler);

		Assert.assertTrue(result);
	}

	@Test
	public final void postHandleOnAccess() throws Exception {
		final HttpSession session = mock(HttpSession.class);

		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);

		final HttpServletResponse response = mock(HttpServletResponse.class);
		final Object handler = new Object();
		final ModelAndView modelAndView = new ModelAndView();

		this.accessInterceptor.postHandle(request, response, handler, modelAndView);
	}

}
