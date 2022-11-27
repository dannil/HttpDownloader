package com.github.dannil.httpdownloader.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for login validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class LoginValidatorUnitTest {

    @Autowired
    private LoginValidator loginValidator;

    @Test
    public void tryToValidateNonUserObjectLoggingIn() {
        Download download = TestUtility.getDownload();
        assertThrows(ClassCastException.class, () -> {
            this.loginValidator.validate(download, null);
        });
    }

    @Test
    public void validateUserLoggingInWithNullValues() {
        User user = TestUtility.getUser();
        user.setEmail(null);
        user.setPassword(null);

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.loginValidator.validate(user, result);

        assertTrue(result.hasFieldErrors("email"));
        assertTrue(result.hasFieldErrors("password"));
    }

    @Test
    public void validateUserLoggingInWithMalformedValues() {
        User user = TestUtility.getUser();
        user.setEmail("");
        user.setPassword("");

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.loginValidator.validate(user, result);

        assertTrue(result.hasFieldErrors("email"));
        assertTrue(result.hasFieldErrors("password"));
    }

    @Test
    public void validateUserLoggingInSuccess() {
        User user = TestUtility.getUser();

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.loginValidator.validate(user, result);

        assertFalse(result.hasErrors());
    }

}
