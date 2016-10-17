package com.github.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.controller.LanguageController;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for language controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class LanguageControllerUnitTest {

	@Autowired
	private LanguageController languageController;

	@Test
	public final void loadLanguageWithNullReferer() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("referer")).thenReturn(null);

		final HttpSession session = mock(HttpSession.class);
		final Locale language = Locale.getDefault();

		final String path = this.languageController.languageGET(request, session, language.toLanguageTag());
		Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
	}

	@Test
	public final void loadLanguageWithExistingLanguage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("referer")).thenReturn("/downloads");

		final HttpSession session = mock(HttpSession.class);
		final Locale language = Locale.forLanguageTag("en-US");

		final String path = this.languageController.languageGET(request, session, language.toLanguageTag());
		Assert.assertEquals(URLUtility.redirect(request.getHeader("referer")), path);
	}

	@Test
	public final void loadLanguageWithNonExistingLanguage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("referer")).thenReturn("/downloads");

		final HttpSession session = mock(HttpSession.class);
		final Locale language = Locale.forLanguageTag("fo-FO");

		final String path = this.languageController.languageGET(request, session, language.toLanguageTag());
		Assert.assertEquals(URLUtility.redirect(request.getHeader("referer")), path);
	}

}
