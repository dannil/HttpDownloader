package org.dannil.httpdownloader.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
		request.setAttribute("language", LanguageUtility.getLanguage(request.getSession()));
		super.postHandle(request, response, handler, modelAndView);
	}
}
