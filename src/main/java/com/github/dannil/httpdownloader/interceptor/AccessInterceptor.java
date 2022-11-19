package com.github.dannil.httpdownloader.interceptor;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.github.dannil.httpdownloader.utility.LanguageUtility;

/**
 * Class for operations to perform on access, such as login and register.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(AccessInterceptor.class.getName());

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        LOGGER.info("Trying to load language...");
        request.setAttribute("language", LanguageUtility.getLanguage((Locale) request.getSession().getAttribute("language")));
    }
}
