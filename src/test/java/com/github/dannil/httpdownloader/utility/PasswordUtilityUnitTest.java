package com.github.dannil.httpdownloader.utility;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for password utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class PasswordUtilityUnitTest {

    @Test
    public void passwordUtilityConstructorThrowsExceptionOnInstantiation()
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Constructor<PasswordUtility> constructor = PasswordUtility.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> {
            constructor.newInstance();
        });
    }

    @Test
    public void getHashedPassword() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String password = "pass";
        String hash = PasswordUtility.getHashedPassword(password);

        assertNotNull(hash);
    }

    @Test
    public void validateHashedPasswordCorrect()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String password = "pass";
        String hash = PasswordUtility.getHashedPassword(password);

        assertTrue(PasswordUtility.compareHashedPasswords(password, hash));
    }

    @Test
    public void validateHashedPasswordIncorrect()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        String password1 = "pass1";
        String password2 = "pass2";

        String hash = PasswordUtility.getHashedPassword(password1);

        assertFalse(PasswordUtility.compareHashedPasswords(password2, hash));
    }

}
