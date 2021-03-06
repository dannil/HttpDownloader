package com.github.dannil.httpdownloader.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for config exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@RunWith(JUnit4.class)
public class ConfigExceptionUnitTest {

    @Test
    public void createException() {
        ConfigException e = new ConfigException();

        Assert.assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Config exception occured";

        ConfigException e = new ConfigException(message);

        Assert.assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Config exception occured";

        Throwable cause = new IllegalArgumentException();
        ConfigException e = new ConfigException(message, cause);

        Assert.assertEquals(message, e.getMessage());
        Assert.assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        ConfigException e = new ConfigException(cause);

        Assert.assertEquals(cause, e.getCause());
    }

}
