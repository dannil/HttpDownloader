package com.github.dannil.httpdownloader.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for access controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
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

        assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
    }

    @Test
    public void loadLoginPageUserNotSet() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        String path = this.accessController.loginGET(request, session);

        assertEquals(URLUtility.getUrl(URL.LOGIN), path);
    }

    @Test
    public void loadLogoutPage() {
        HttpSession session = mock(HttpSession.class);

        String path = this.accessController.logoutGET(session);
        assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
    }

    @Test
    public void loadRegisterPage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        String path = this.accessController.registerGET(request, session);
        assertEquals(URLUtility.getUrl(URL.REGISTER), path);
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
        assertEquals(URLUtility.getUrlRedirect(URL.REGISTER), path);
    }

}
