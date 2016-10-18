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
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.filter.CharsetFilter;

/**
 * Unit tests for charset filter
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class CharsetFilterUnitTest {

	@Test
	public  void charsetFilterWithNullCharacterEncoding() throws IOException, ServletException {
		 ServletRequest request = mock(ServletRequest.class);
		 ServletResponse response = mock(ServletResponse.class);
		 FilterChain chain = mock(FilterChain.class);

		 FilterConfig config = mock(FilterConfig.class);

		 CharsetFilter filter = new CharsetFilter();

		filter.init(config);
		filter.doFilter(request, response, chain);

		Assert.assertEquals("UTF-8", filter.getEncoding());
	}

	@Test
	public  void charsetFilterWithLatin1CharacterEncoding() throws IOException, ServletException {
		 ServletRequest request = mock(ServletRequest.class);
		when(request.getCharacterEncoding()).thenReturn("latin1");

		 ServletResponse response = mock(ServletResponse.class);
		 FilterChain chain = mock(FilterChain.class);

		 FilterConfig config = mock(FilterConfig.class);
		when(config.getInitParameter("requestEncoding")).thenReturn("latin1");

		 CharsetFilter filter = new CharsetFilter();

		filter.init(config);
		filter.doFilter(request, response, chain);

		Assert.assertEquals("latin1", filter.getEncoding());
	}

	@Test
	public  void charsetFilterDestroy() {
		 CharsetFilter filter = new CharsetFilter();
		filter.destroy();

		Assert.assertEquals("", filter.getEncoding());
	}

}
