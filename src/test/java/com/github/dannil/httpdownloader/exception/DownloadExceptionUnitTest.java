package com.github.dannil.httpdownloader.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Unit tests for download exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class DownloadExceptionUnitTest {

	@Test
	public void createException() {
		DownloadException e = new DownloadException();

		Assert.assertNotNull(e);
	}

	@Test
	public void createExceptionWithMessage() {
		String message = "Download exception occured";

		DownloadException e = new DownloadException(message);

		Assert.assertEquals(message, e.getMessage());
	}

	@Test
	public void createExceptionWithMessageAndCause() {
		String message = "Download exception occured";

		Throwable cause = new IllegalArgumentException();
		DownloadException e = new DownloadException(message, cause);

		Assert.assertEquals(message, e.getMessage());
		Assert.assertEquals(cause, e.getCause());
	}

	@Test
	public void createExceptionWithCause() {
		Throwable cause = new IllegalArgumentException();
		DownloadException e = new DownloadException(cause);

		Assert.assertEquals(cause, e.getCause());

	}

}
