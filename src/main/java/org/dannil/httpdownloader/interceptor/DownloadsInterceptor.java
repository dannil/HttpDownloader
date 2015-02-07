package org.dannil.httpdownloader.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.dannil.httpdownloader.utility.PathUtility;
import org.dannil.httpdownloader.utility.XMLUtility;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Class for handling operations to perform on download access, such as 
 * listing all downloads and fetching a download.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     0.0.1-SNAPSHOT
 * @since       0.0.1-SNAPSHOT
 */
@Component
public final class DownloadsInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger(DownloadsInterceptor.class.getName());

	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
		if (request.getSession().getAttribute("user") == null) {
			LOGGER.error("Couldn't find a user object within the session");

			final XMLUtility xmlUtility = new XMLUtility(PathUtility.getAbsolutePathToConfiguration() + "config.xml");

			response.sendRedirect(request.getContextPath() + xmlUtility.getElementValue("/configuration/app/urls/login"));
			return false;
		}
		return true;
	}

	@Override
	public final void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
		LOGGER.info("Trying to load language...");
		request.setAttribute("language", LanguageUtility.getLanguage(request.getSession()));
		super.postHandle(request, response, handler, modelAndView);
	}
}
