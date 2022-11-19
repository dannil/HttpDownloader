package com.github.dannil.httpdownloader.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Class for handling application-wide encoding, which ensures that all data is treated
 * with the same encoding.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class CharsetFilter implements Filter {

    private String encoding;

    @Override
    public final void init(FilterConfig config) throws ServletException {
        this.encoding = config.getInitParameter("requestEncoding");

        if (this.encoding == null) {
            this.encoding = "UTF-8";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        // Respect the client-specified character encoding
        // (see HTTP specification section 3.4.1)
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(this.encoding);
        }

        /**
         * Set the default response content type and encoding
         */
        response.setContentType("text/html; charset=" + this.encoding);
        response.setCharacterEncoding(this.encoding);

        next.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encoding = "";
    }

    public String getEncoding() {
        return this.encoding;
    }

}
