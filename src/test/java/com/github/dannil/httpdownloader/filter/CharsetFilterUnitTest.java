package com.github.dannil.httpdownloader.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Unit tests for charset filter
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class CharsetFilterUnitTest {

    @Test
    public void charsetFilterWithNullCharacterEncoding() throws IOException, ServletException {
        ServletRequest request = mock(ServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        FilterConfig config = mock(FilterConfig.class);

        CharsetFilter filter = new CharsetFilter();

        filter.init(config);
        filter.doFilter(request, response, chain);

        assertEquals("UTF-8", filter.getEncoding());
    }

    @Test
    public void charsetFilterWithLatin1CharacterEncoding() throws IOException, ServletException {
        ServletRequest request = mock(ServletRequest.class);
        when(request.getCharacterEncoding()).thenReturn("latin1");

        ServletResponse response = mock(ServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        FilterConfig config = mock(FilterConfig.class);
        when(config.getInitParameter("requestEncoding")).thenReturn("latin1");

        CharsetFilter filter = new CharsetFilter();

        filter.init(config);
        filter.doFilter(request, response, chain);

        assertEquals("latin1", filter.getEncoding());
    }

    @Test
    public void charsetFilterDestroy() {
        CharsetFilter filter = new CharsetFilter();
        filter.destroy();

        assertEquals("", filter.getEncoding());
    }

}
