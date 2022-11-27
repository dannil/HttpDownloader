package com.github.dannil.httpdownloader.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

import jakarta.servlet.http.HttpSession;

/**
 * Integration tests for access controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
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
        assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
    }

    @Test
    public void loginExistingUser() {
        User user = TestUtility.getUser();
        HttpSession session = mock(HttpSession.class);
        BindingResult result = mock(BindingResult.class);

        this.registerService.save(user);

        String path = this.accessController.loginPOST(session, user, result);

        assertEquals(URLUtility.getUrlRedirect(URL.DOWNLOADS), path);
    }

    @Test
    public void loginNonExistingUser() {
        HttpSession session = mock(HttpSession.class);
        User user = TestUtility.getUser();
        BindingResult result = mock(BindingResult.class);

        String path = this.accessController.loginPOST(session, user, result);
        assertEquals(URLUtility.getUrlRedirect(URL.LOGIN), path);
    }

    @Test
    public void loginUserWithErrors() {
        HttpSession session = mock(HttpSession.class);

        User user = TestUtility.getUser();
        user.setEmail(null);
        user.setPassword(null);

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.accessController.loginPOST(session, user, result);

        assertTrue(result.hasErrors());
    }

}
