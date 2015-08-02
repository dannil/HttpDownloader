package com.github.dannil.httpdownloader.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.dannil.httpdownloader.utility.LanguageUtility;

/**
 * Class for operations to perform on access, such as login and register.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Component
public final class AccessInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger(AccessInterceptor.class.getName());

	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		return super.preHandle(request, response, handler);
	}

	@Override
	public final void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
		LOGGER.info("Trying to load language...");
		request.setAttribute("language", LanguageUtility.getLanguage((Locale) request.getSession().getAttribute("language")));
		super.postHandle(request, response, handler, modelAndView);
	}
}
