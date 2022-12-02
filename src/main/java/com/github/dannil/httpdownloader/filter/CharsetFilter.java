package com.github.dannil.httpdownloader.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.springframework.stereotype.Component;

/**
 * Class for handling application-wide encoding, which ensures that all data is treated
 * with the same encoding.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class CharsetFilter implements Filter {

    private String encoding;

    /**
     * Default constructor.
     */
    public CharsetFilter() {

    }

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

    /**
     * Returns the discovered encoding.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

}
