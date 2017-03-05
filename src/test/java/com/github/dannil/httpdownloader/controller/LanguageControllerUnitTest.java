package com.github.dannil.httpdownloader.controller;

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
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.controller.LanguageController;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for language controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
        "classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class LanguageControllerUnitTest {

    @Autowired
    private LanguageController languageController;

    @Test
    public void loadLanguageWithNullReferer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("referer")).thenReturn(null);

        HttpSession session = mock(HttpSession.class);
        Locale language = Locale.getDefault();

        String path = this.languageController.languageGET(request, session, language.toLanguageTag());
        Assert.assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
    }

    @Test
    public void loadLanguageWithExistingLanguage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("referer")).thenReturn("/downloads");

        HttpSession session = mock(HttpSession.class);
        Locale language = Locale.forLanguageTag("en-US");

        String path = this.languageController.languageGET(request, session, language.toLanguageTag());
        Assert.assertEquals(URLUtility.redirect(request.getHeader("referer")), path);
    }

    @Test
    public void loadLanguageWithNonExistingLanguage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("referer")).thenReturn("/downloads");

        HttpSession session = mock(HttpSession.class);
        Locale language = Locale.forLanguageTag("fo-FO");

        String path = this.languageController.languageGET(request, session, language.toLanguageTag());
        Assert.assertEquals(URLUtility.redirect(request.getHeader("referer")), path);
    }

}
