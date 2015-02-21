package org.dannil.httpdownloader.test.integrationtest;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.controller.AccessController;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IRegisterService;
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
 * Integration tests for access controller
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
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
