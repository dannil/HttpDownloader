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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for access controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class AccessControllerUnitTest {

	@Autowired
	private AccessController accessController;

	@Test
	public void loadLoginPageUserSet() {
		User user = TestUtility.getUser();

		HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		HttpServletRequest request = mock(HttpServletRequest.class);

		session.setAttribute("user", user);

		String path = this.accessController.loginGET(request, session);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public void loadLoginPageUserNotSet() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);

		String path = this.accessController.loginGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.LOGIN), path);
	}

	@Test
	public void loadLogoutPage() {
		HttpSession session = mock(HttpSession.class);

		String path = this.accessController.logoutGET(session);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public void loadRegisterPage() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);

		String path = this.accessController.registerGET(request, session);
		Assert.assertEquals(URLUtility.getUrl(URL.REGISTER), path);
	}

	@Test
	public void registerUserWithMalformedValues() {
		HttpSession session = mock(HttpSession.class);

		User user = TestUtility.getUser();
		user.setFirstname("");
		user.setLastname("");
		user.setEmail("");
		user.setPassword("");

		BindingResult result = new BeanPropertyBindingResult(user, "user");

		String path = this.accessController.registerPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.REGISTER), path);
	}

}
