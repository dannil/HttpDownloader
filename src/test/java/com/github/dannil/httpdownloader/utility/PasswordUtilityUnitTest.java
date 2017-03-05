package com.github.dannil.httpdownloader.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for password utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class PasswordUtilityUnitTest {

    @Test(expected = Exception.class)
    public void passwordUtilityConstructorThrowsExceptionOnInstantiation()
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Constructor<PasswordUtility> constructor = PasswordUtility.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void getHashedPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String password = "pass";
        String hash = PasswordUtility.getHashedPassword(password);

        Assert.assertNotNull(hash);
    }

    @Test
    public void validateHashedPasswordCorrect()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String password = "pass";
        String hash = PasswordUtility.getHashedPassword(password);

        Assert.assertTrue(PasswordUtility.compareHashedPasswords(password, hash));
    }

    @Test
    public void validateHashedPasswordIncorrect()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String password1 = "pass1";
        String password2 = "pass2";

        String hash = PasswordUtility.getHashedPassword(password1);

        Assert.assertFalse(PasswordUtility.compareHashedPasswords(password2, hash));
    }

}
