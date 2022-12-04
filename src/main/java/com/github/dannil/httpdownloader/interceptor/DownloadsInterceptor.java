package com.github.dannil.httpdownloader.interceptor;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Class for handling operations to perform on download access, such as listing all
 * downloads and fetching a download.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class DownloadsInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadsInterceptor.class.getName());

    /**
     * Default constructor.
     */
    public DownloadsInterceptor() {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (request.getSession().getAttribute("user") == null) {
            LOGGER.error("Couldn't find a user object within the session");

            response.sendRedirect(request.getContextPath() + URLUtility.getUrl(URL.LOGIN));
            return false;
        }
        return true;
    }

}
