package com.github.dannil.httpdownloader.test.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.exception.UnqualifiedAccessException;

/**
 * Unit tests for unqualified access exception
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public final class UnqualifiedAccessExceptionUnitTest {

	@Test
	public final void createException() {
		final UnqualifiedAccessException e = new UnqualifiedAccessException();

		Assert.assertNotNull(e);
	}

	@Test
	public final void createExceptionWithMessage() {
		final String message = "Unqualified access to a resource occured";

		final UnqualifiedAccessException e = new UnqualifiedAccessException(message);

		Assert.assertEquals(message, e.getMessage());
	}
}
