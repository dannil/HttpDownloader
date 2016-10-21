package com.github.dannil.httpdownloader.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Unit tests for language exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
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
