package com.github.dannil.httpdownloader.controller;

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
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class AccessControllerIntegrationTest {

	@Autowired
	private AccessController accessController;

	@Autowired
	private IRegisterService registerService;

	@Test
	public void registerUserSuccess() {
		HttpSession session = mock(HttpSession.class);
		User user = TestUtility.getUser();

		BindingResult result = new BeanPropertyBindingResult(user, "user");

		String path = this.accessController.registerPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public void loginExistingUser() {
		User user = TestUtility.getUser();
		HttpSession session = mock(HttpSession.class);
		BindingResult result = mock(BindingResult.class);

		this.registerService.save(user);

		String path = this.accessController.loginPOST(session, user, result);

		Assert.assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
	}

	@Test
	public void loginNonExistingUser() {
		HttpSession session = mock(HttpSession.class);
		User user = TestUtility.getUser();
		BindingResult result = mock(BindingResult.class);

		String path = this.accessController.loginPOST(session, user, result);
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public void loginUserWithErrors() {
		HttpSession session = mock(HttpSession.class);

		User user = TestUtility.getUser();
		user.setEmail(null);
		user.setPassword(null);

		BindingResult result = new BeanPropertyBindingResult(user, "user");
		this.accessController.loginPOST(session, user, result);

		Assert.assertTrue(result.hasErrors());
	}

}
