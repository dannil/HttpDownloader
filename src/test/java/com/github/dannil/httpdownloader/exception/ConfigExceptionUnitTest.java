package com.github.dannil.httpdownloader.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for config exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@SpringBootTest
public class ConfigExceptionUnitTest {

    @Test
    public void createException() {
        ConfigException e = new ConfigException();

        assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Config exception occured";

        ConfigException e = new ConfigException(message);

        assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Config exception occured";

        Throwable cause = new IllegalArgumentException();
        ConfigException e = new ConfigException(message, cause);

        assertEquals(message, e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        ConfigException e = new ConfigException(cause);

        assertEquals(cause, e.getCause());
    }

}
