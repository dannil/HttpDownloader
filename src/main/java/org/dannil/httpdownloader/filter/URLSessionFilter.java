package org.dannil.httpdownloader.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public final class URLSessionFilter implements Filter {

	public final void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
			public final String encodeRedirectUrl(final String url) {
				return url;
			}

			public final String encodeRedirectURL(final String url) {
				return url;
			}

			public final String encodeUrl(final String url) {
				return url;
			}

			public final String encodeURL(final String url) {
				return url;
			}
		};
		chain.doFilter(request, wrappedResponse);
	}

	public void init(final FilterConfig filterConfig) {
		//
	}

	public void destroy() {
		//
	}
}