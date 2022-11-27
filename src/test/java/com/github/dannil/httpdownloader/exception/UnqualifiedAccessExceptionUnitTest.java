package com.github.dannil.httpdownloader.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for unqualified access exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class UnqualifiedAccessExceptionUnitTest {

    @Test
    public void createException() {
        UnqualifiedAccessException e = new UnqualifiedAccessException();

        assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Unqualified access to a resource occured";

        UnqualifiedAccessException e = new UnqualifiedAccessException(message);

        assertEquals(message, e.getMessage());
    }
}
