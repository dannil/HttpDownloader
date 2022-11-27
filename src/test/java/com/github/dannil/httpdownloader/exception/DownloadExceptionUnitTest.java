package com.github.dannil.httpdownloader.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for download exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@SpringBootTest
public class DownloadExceptionUnitTest {

    @Test
    public void createException() {
        DownloadException e = new DownloadException();

        assertNotNull(e);
    }

    @Test
    public void createExceptionWithMessage() {
        String message = "Download exception occured";

        DownloadException e = new DownloadException(message);

        assertEquals(message, e.getMessage());
    }

    @Test
    public void createExceptionWithMessageAndCause() {
        String message = "Download exception occured";

        Throwable cause = new IllegalArgumentException();
        DownloadException e = new DownloadException(message, cause);

        assertEquals(message, e.getMessage());
        assertEquals(cause, e.getCause());
    }

    @Test
    public void createExceptionWithCause() {
        Throwable cause = new IllegalArgumentException();
        DownloadException e = new DownloadException(cause);

        assertEquals(cause, e.getCause());

    }

}
