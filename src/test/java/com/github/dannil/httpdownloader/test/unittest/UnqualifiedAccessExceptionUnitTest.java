package com.github.dannil.httpdownloader.test.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.exception.UnqualifiedAccessException;

/**
 * Unit tests for unqualified access exception
 * 
 * @author Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
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
