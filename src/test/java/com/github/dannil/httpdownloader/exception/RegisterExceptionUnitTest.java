package com.github.dannil.httpdownloader.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for register exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@SpringBootTest
public class RegisterExceptionUnitTest {

    @Test
    public void createException() {
        RegisterException e = new RegisterException();

        assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Register exception occured";

        RegisterException e = new RegisterException(message);

        assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Register exception occured";

        Throwable cause = new IllegalArgumentException();
        RegisterException e = new RegisterException(message, cause);

        assertEquals(message, e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        RegisterException e = new RegisterException(cause);

        assertEquals(cause, e.getCause());
    }

}
