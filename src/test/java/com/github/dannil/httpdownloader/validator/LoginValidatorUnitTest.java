package com.github.dannil.httpdownloader.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for login validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
        "classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class LoginValidatorUnitTest {

    @Autowired
    private LoginValidator loginValidator;

    @Test(expected = ClassCastException.class)
    public void tryToValidateNonUserObjectLoggingIn() {
        Download download = TestUtility.getDownload();
        this.loginValidator.validate(download, null);
    }

    @Test
    public void validateUserLoggingInWithNullValues() {
        User user = TestUtility.getUser();
        user.setEmail(null);
        user.setPassword(null);

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.loginValidator.validate(user, result);

        Assert.assertTrue(result.hasFieldErrors("email"));
        Assert.assertTrue(result.hasFieldErrors("password"));
    }

    @Test
    public void validateUserLoggingInWithMalformedValues() {
        User user = TestUtility.getUser();
        user.setEmail("");
        user.setPassword("");

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.loginValidator.validate(user, result);

        Assert.assertTrue(result.hasFieldErrors("email"));
        Assert.assertTrue(result.hasFieldErrors("password"));
    }

    @Test
    public void validateUserLoggingInSuccess() {
        User user = TestUtility.getUser();

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        this.loginValidator.validate(user, result);

        Assert.assertFalse(result.hasErrors());
    }

}
