package com.github.dannil.httpdownloader.test.unittest;

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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.controller.AccessController;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for access controller
 * 
 * @author Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class AccessControllerUnitTest {

	@Autowired
	private AccessController accessController;

	@Test
	public final void loadLoginPageUserSet() {
		final User user = new User(TestUtility.getUser());

		final HttpSession session = mock(HttpSession.class);
		when(session.getAttribute("user")).thenReturn(user);

		final HttpServletRequest request = mock(HttpServletRequest.class);

		session.setAttribute("user", user);

		final String path = this.accessController.loginGET(request, session);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public final void loadLoginPageUserNotSet() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);

		final String path = this.accessController.loginGET(request, session);

		Assert.assertEquals(URLUtility.getUrl(URL.LOGIN), path);
	}

	@Test
	public final void loadLogoutPage() {
		final HttpSession session = mock(HttpSession.class);

		final String path = this.accessController.logoutGET(session);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public final void loadRegisterPage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);

		final String path = this.accessController.registerGET(request, session);
		Assert.assertEquals(URLUtility.getUrl(URL.REGISTER), path);
	}

	@Test
	public final void registerUserWithMalformedValues() {
		final HttpSession session = mock(HttpSession.class);

		final User user = new User(TestUtility.getUser());
		user.setFirstname("");
		user.setLastname("");
		user.setEmail("");
		user.setPassword("");

		final BindingResult result = new BeanPropertyBindingResult(user, "user");

		final String path = this.accessController.registerPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.REGISTER), path);
	}

}
