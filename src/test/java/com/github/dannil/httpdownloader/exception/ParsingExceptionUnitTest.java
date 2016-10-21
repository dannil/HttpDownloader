package com.github.dannil.httpdownloader.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Unit tests for parsing exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.1-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class ParsingExceptionUnitTest {

	@Test
	public void createException() {
		ParsingException e = new ParsingException();

		Assert.assertNotNull(e);
	}

	@Test
	public void createExceptionWithMessage() {
		String message = "Parsing exception occured";

		ParsingException e = new ParsingException(message);

		Assert.assertEquals(message, e.getMessage());
	}

	@Test
	public void createExceptionWithMessageAndCause() {
		String message = "Parsing exception occured";

		Throwable cause = new IllegalArgumentException();
		ParsingException e = new ParsingException(message, cause);

		Assert.assertEquals(message, e.getMessage());
		Assert.assertEquals(cause, e.getCause());
	}

	@Test
	public void createExceptionWithCause() {
		Throwable cause = new IllegalArgumentException();
		ParsingException e = new ParsingException(cause);

		Assert.assertEquals(cause, e.getCause());
	}

}
