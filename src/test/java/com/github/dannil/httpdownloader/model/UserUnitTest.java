package com.github.dannil.httpdownloader.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.PasswordUtility;

/**
 * Unit tests for user
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class UserUnitTest {

    @Test
    public void createUserWithConstructor() {
        User user = TestUtility.getUser();

        User userConstructor = new User(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(),
                user.getDownloads());
        userConstructor.setId(user.getId());

        assertEquals(user, userConstructor);
    }

    @Test
    public void createUserWithMethods() {
        User userUtility = TestUtility.getUser();
        User userMethods = new User(userUtility.getEmail(), userUtility.getPassword(), userUtility.getFirstname(),
                userUtility.getLastname());

        userMethods.setId(userUtility.getId());
        userMethods.setDownloads(userUtility.getDownloads());

        assertEquals(userUtility, userMethods);
    }

    @Test
    public void userEquals() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        assertTrue(userEquals1.equals(userEquals2));
    }

    @Test
    public void userEqualsItself() {
        User userEquals1 = TestUtility.getUser();

        assertTrue(userEquals1.equals(userEquals1));
    }

    @Test
    public void userNotEqualsWithNull() {
        User userEquals1 = TestUtility.getUser();

        assertFalse(userEquals1.equals(null));
    }

    @Test
    public void userNotEqualsWithIncompatibleObject() {
        User userEquals1 = TestUtility.getUser();
        Download downloadEquals1 = TestUtility.getDownload();

        assertFalse(userEquals1.equals(downloadEquals1));
    }

    @Test
    public void userNotEqualsOnEmail() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals2.setEmail(userEquals1.getEmail() + "a");

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnNullEmail() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setEmail(null);

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userEqualsOnBothNullEmail() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setEmail(null);
        userEquals2.setEmail(null);

        assertTrue(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnPassword()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
        userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

        userEquals2.setPassword(userEquals1.getPassword() + "a");

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnNullPassword()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
        userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

        userEquals1.setPassword(null);

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userEqualsOnBothNullPassword()
            throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setPassword(PasswordUtility.getHashedPassword(userEquals1.getPassword()));
        userEquals2.setPassword(PasswordUtility.getHashedPassword(userEquals2.getPassword()));

        userEquals1.setPassword(null);
        userEquals2.setPassword(null);

        assertTrue(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnFirstname() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals2.setFirstname(userEquals1.getFirstname() + "a");

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnNullFirstname() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setFirstname(null);

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userEqualsOnBothNullFirstname() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setFirstname(null);
        userEquals2.setFirstname(null);

        assertTrue(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnLastname() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals2.setLastname(userEquals1.getId() + "a");

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnNullLastname() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setLastname(null);

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userEqualsOnBothNullLastname() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setLastname(null);
        userEquals2.setLastname(null);

        assertTrue(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnDownloads() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        Download download = TestUtility.getDownload();
        userEquals2.addDownload(download);

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userNotEqualsOnNullDownloads() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setDownloads(null);

        assertFalse(userEquals1.equals(userEquals2));
    }

    @Test
    public void userEqualsOnBothNullDownloads() {
        User userEquals1 = TestUtility.getUser();
        User userEquals2 = TestUtility.deepCopy(userEquals1);

        userEquals1.setDownloads(null);
        userEquals2.setDownloads(null);

        assertTrue(userEquals1.equals(userEquals2));
    }

    @Test
    public void userHashCode() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userHashCodeNullId() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        userHashCode1.setId(null);
        userHashCode2.setId(null);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userHashCodeNullEmail() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        userHashCode1.setEmail(null);
        userHashCode2.setEmail(null);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userHashCodeNullPassword() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        userHashCode1.setPassword(null);
        userHashCode2.setPassword(null);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userHashCodeNullFirstname() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        userHashCode1.setFirstname(null);
        userHashCode2.setFirstname(null);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userHashCodeNullLastname() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        userHashCode1.setLastname(null);
        userHashCode2.setLastname(null);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userHashCodeNullDownloads() {
        User userHashCode1 = TestUtility.getUser();
        User userHashCode2 = TestUtility.deepCopy(userHashCode1);

        userHashCode1.setDownloads(null);
        userHashCode2.setDownloads(null);

        assertEquals(userHashCode1.hashCode(), userHashCode2.hashCode());
    }

    @Test
    public void userToString() {
        User userToString1 = TestUtility.getUser();
        User userToString2 = TestUtility.deepCopy(userToString1);

        assertEquals(userToString1.toString(), userToString2.toString());
    }

    @Test
    public void addDownloadToUser() {
        User user = TestUtility.getUser();
        Download download = TestUtility.getDownload();

        user.addDownload(download);

        assertEquals(1, user.getDownloads().size());
    }

    @Test
    public void addDownloadToUserWithConstructor() {
        User user = TestUtility.getUser();
        Download download = TestUtility.getDownload();

        user.addDownload(download);

        User check = TestUtility.deepCopy(user);

        assertEquals(1, check.getDownloads().size());
    }

    @Test
    public void addDownloadToUserNull() {
        User user = TestUtility.getUser();

        user.addDownload(null);

        assertEquals(0, user.getDownloads().size());
    }

    @Test
    public void addDownloadToUserWithNullId() {
        User user = TestUtility.getUser();
        Download download = TestUtility.getDownload();

        download.setId(null);

        assertThrows(IllegalArgumentException.class, () -> {
            user.addDownload(download);
        });
    }

}
