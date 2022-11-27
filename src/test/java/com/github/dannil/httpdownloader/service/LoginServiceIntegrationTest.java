package com.github.dannil.httpdownloader.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Integration tests for login service
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class LoginServiceIntegrationTest {

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private ILoginService loginService;

    @Test
    public void loginUser() {
        User user = TestUtility.getUser();

        // Save password in a variable as the register operation replaces the user
        // password with the hashed variant
        String password = user.getPassword();

        this.registerService.save(user);

        User login = this.loginService.login(user.getEmail(), password);

        assertNotEquals(null, login);
    }

    @Test
    public void loginUserWithId() {
        User user = TestUtility.getUser();
        User registered = this.registerService.save(user);

        User login = this.loginService.findById(registered.getId());

        assertNotEquals(null, login);
    }

    @Test
    public void loginUserWithNonExistingEmail() {
        User user = TestUtility.getUser();
        this.registerService.save(user);

        User login = this.loginService.login("placeholder@placeholder.com", user.getPassword());

        assertEquals(null, login);
    }

    @Test
    public void loginUserWithIncorrectPassword() {
        User user = TestUtility.getUser();
        this.registerService.save(user);

        User login = this.loginService.login(user.getEmail(), "placeholder");

        assertEquals(null, login);
    }

}
