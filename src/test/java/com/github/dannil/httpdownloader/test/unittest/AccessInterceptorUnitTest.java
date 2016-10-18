package com.github.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import com.github.dannil.httpdownloader.interceptor.AccessInterceptor;

/**
 * Unit tests for access interceptor
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class AccessInterceptorUnitTest {

	@Autowired
	private AccessInterceptor accessInterceptor;

	@Test
	public  void preHandleOnAccess() throws Exception {
		 HttpServletRequest request = mock(HttpServletRequest.class);
		 HttpServletResponse response = mock(HttpServletResponse.class);
		 Object handler = new Object();

		 boolean result = this.accessInterceptor.preHandle(request, response, handler);

		Assert.assertTrue(result);
	}

	@Test
	public  void postHandleOnAccess() throws Exception {
		 HttpSession session = mock(HttpSession.class);

		 HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession()).thenReturn(session);

		 HttpServletResponse response = mock(HttpServletResponse.class);
		 Object handler = new Object();
		 ModelAndView modelAndView = new ModelAndView();

		this.accessInterceptor.postHandle(request, response, handler, modelAndView);
	}

}
