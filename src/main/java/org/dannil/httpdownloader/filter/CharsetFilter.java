package org.dannil.httpdownloader.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public final class CharsetFilter implements Filter {

	private String encoding;

	@Override
	public final void init(final FilterConfig config) throws ServletException {
		this.encoding = config.getInitParameter("requestEncoding");

		if (this.encoding == null) {
			this.encoding = "UTF-8";
		}
	}

	@Override
	public final void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain next) throws IOException, ServletException {
		// Respect the client-specified character encoding
		// (see HTTP specification section 3.4.1)
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(this.encoding);
		}

		/**
		* Set the default response content type and encoding
		*/
		response.setContentType("text/html; charset=" + this.encoding + "");
		response.setCharacterEncoding(this.encoding);
		// response.setContentType("text/html; charset=UTF-8");
		// response.setCharacterEncoding("UTF-8");

		next.doFilter(request, response);
	}

	@Override
	public void destroy() {
		//
	}

	public final String getEncoding() {
		return this.encoding;
	}

}
