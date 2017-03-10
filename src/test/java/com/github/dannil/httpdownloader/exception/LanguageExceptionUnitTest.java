package com.github.dannil.httpdownloader.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for language exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@RunWith(JUnit4.class)
public class LanguageExceptionUnitTest {

    @Test
    public void createException() {
        LanguageException e = new LanguageException();

        Assert.assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Language exception occured";

        LanguageException e = new LanguageException(message);

        Assert.assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Language exception occured";

        Throwable cause = new IllegalArgumentException();
        LanguageException e = new LanguageException(message, cause);

        Assert.assertEquals(message, e.getMessage());
        Assert.assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        LanguageException e = new LanguageException(cause);

        Assert.assertEquals(cause, e.getCause());
    }

}
