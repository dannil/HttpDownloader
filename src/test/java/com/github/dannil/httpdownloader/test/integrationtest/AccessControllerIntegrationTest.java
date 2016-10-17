package com.github.dannil.httpdownloader.test.integrationtest;

import static org.mockito.Mockito.mock;

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

import com.github.dannil.httpdownloader.controller.AccessController;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Integration tests for access controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/spring-context.xml" })
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"file:src/test/resources/WEB-INF/configuration/framework/application-context.xml" })
public class AccessControllerIntegrationTest {

	@Autowired
	private AccessController accessController;

	@Autowired
	private IRegisterService registerService;

	@Test
	public final void registerUserSuccess() {
		final HttpSession session = mock(HttpSession.class);
		final User user = new User(TestUtility.getUser());

		final BindingResult result = new BeanPropertyBindingResult(user, "user");

		final String path = this.accessController.registerPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public final void loginExistingUser() {
		final User user = new User(TestUtility.getUser());
		final HttpSession session = mock(HttpSession.class);
		final BindingResult result = mock(BindingResult.class);

		this.registerService.save(user);

		final String path = this.accessController.loginPOST(session, user, result);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public final void loginNonExistingUser() {
		final HttpSession session = mock(HttpSession.class);
		final User user = new User(TestUtility.getUser());
		final BindingResult result = mock(BindingResult.class);

		final String path = this.accessController.loginPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public final void loginUserWithErrors() {
		final HttpSession session = mock(HttpSession.class);

		final User user = new User(TestUtility.getUser());
		user.setEmail(null);
		user.setPassword(null);

		final BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.accessController.loginPOST(session, user, result);

		Assert.assertTrue(result.hasErrors());
	}

}
