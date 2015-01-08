package org.dannil.httpdownloader.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public final class DownloadsInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger(DownloadsInterceptor.class.getName());

	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		if (request.getSession().getAttribute("user") == null) {
			LOGGER.error("Couldn't find a user object within the session");
			response.sendRedirect(request.getContextPath() + PathUtility.URL_LOGIN);
			return false;
		}
		return true;
	}

	@Override
	public final void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
