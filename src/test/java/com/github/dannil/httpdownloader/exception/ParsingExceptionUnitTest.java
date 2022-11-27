package com.github.dannil.httpdownloader.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for parsing exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@SpringBootTest
public class ParsingExceptionUnitTest {

    @Test
    public void createException() {
        ParsingException e = new ParsingException();

        assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Parsing exception occured";

        ParsingException e = new ParsingException(message);

        assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Parsing exception occured";

        Throwable cause = new IllegalArgumentException();
        ParsingException e = new ParsingException(message, cause);

        assertEquals(message, e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        ParsingException e = new ParsingException(cause);

        assertEquals(cause, e.getCause());
    }

}
