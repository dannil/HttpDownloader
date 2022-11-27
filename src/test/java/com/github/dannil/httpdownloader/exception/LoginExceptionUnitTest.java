package com.github.dannil.httpdownloader.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for login exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@SpringBootTest
public class LoginExceptionUnitTest {

    @Test
    public void createException() {
        LoginException e = new LoginException();

        assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Login exception occured";

        LoginException e = new LoginException(message);

        assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Login exception occured";

        Throwable cause = new IllegalArgumentException();
        LoginException e = new LoginException(message, cause);

        assertEquals(message, e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        LoginException e = new LoginException(cause);

        assertEquals(cause, e.getCause());

    }

}
