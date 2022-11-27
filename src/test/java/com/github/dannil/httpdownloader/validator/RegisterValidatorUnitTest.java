package com.github.dannil.httpdownloader.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.service.IRegisterService;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for register validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class RegisterValidatorUnitTest {

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private RegisterValidator registerValidator;

    @Test
    public void tryToValidateNonUserObjectRegistering() {
        Download download = TestUtility.getDownload();
        assertThrows(ClassCastException.class, () -> {
            this.registerValidator.validate(download, null);
        });
    }

    @Test
    public void validateUserRegisteringExistingEmail() {
        User user = TestUtility.getUser();

        User attempt = this.registerService.save(user);

        BindingResult result = new BeanPropertyBindingResult(attempt, "user");
        this.registerValidator.validate(attempt, result);

        assertTrue(result.hasFieldErrors("email"));
    }

    @Test
    public void validateUserRegisteringWithMalformedFirstnameAndLastname() {
        User user = TestUtility.getUser();
        user.setFirstname(null);
        user.setLastname(null);

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.registerValidator.validate(user, result);

        assertTrue(result.hasFieldErrors("firstname"));
        assertTrue(result.hasFieldErrors("lastname"));
    }

    @Test
    public void validateUserRegisteringWithMalformedEmail() {
        User user = TestUtility.getUser();
        user.setEmail("abc@abc");

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.registerValidator.validate(user, result);

        assertTrue(result.hasFieldErrors("email"));
    }

}
