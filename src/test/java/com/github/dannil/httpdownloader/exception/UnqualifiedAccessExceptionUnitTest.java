package com.github.dannil.httpdownloader.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for unqualified access exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class UnqualifiedAccessExceptionUnitTest {

    @Test
    public void createException() {
        UnqualifiedAccessException e = new UnqualifiedAccessException();

        Assert.assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Unqualified access to a resource occured";

        UnqualifiedAccessException e = new UnqualifiedAccessException(message);

        Assert.assertEquals(message, e.getMessage());
    }
}
