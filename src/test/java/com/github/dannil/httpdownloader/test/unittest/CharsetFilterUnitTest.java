package com.github.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.filter.CharsetFilter;

/**
 * Unit tests for charset filter
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class CharsetFilterUnitTest {

	@Test
	public final void charsetFilterWithNullCharacterEncoding() throws IOException, ServletException {
		final ServletRequest request = mock(ServletRequest.class);
		final ServletResponse response = mock(ServletResponse.class);
		final FilterChain chain = mock(FilterChain.class);

		final FilterConfig config = mock(FilterConfig.class);

		final CharsetFilter filter = new CharsetFilter();

		filter.init(config);
		filter.doFilter(request, response, chain);

		Assert.assertEquals("UTF-8", filter.getEncoding());
	}

	@Test
	public final void charsetFilterWithLatin1CharacterEncoding() throws IOException, ServletException {
		final ServletRequest request = mock(ServletRequest.class);
		when(request.getCharacterEncoding()).thenReturn("latin1");

		final ServletResponse response = mock(ServletResponse.class);
		final FilterChain chain = mock(FilterChain.class);

		final FilterConfig config = mock(FilterConfig.class);
		when(config.getInitParameter("requestEncoding")).thenReturn("latin1");

		final CharsetFilter filter = new CharsetFilter();

		filter.init(config);
		filter.doFilter(request, response, chain);

		Assert.assertEquals("latin1", filter.getEncoding());
	}

	@Test
	public final void charsetFilterDestroy() {
		final CharsetFilter filter = new CharsetFilter();
		filter.destroy();

		Assert.assertEquals("", filter.getEncoding());
	}

}
