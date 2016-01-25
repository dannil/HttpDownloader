package com.github.dannil.httpdownloader.interceptor;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.LanguageUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Class for handling operations to perform on download access, such as 
 * listing all downloads and fetching a download.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Component
public final class DownloadsInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger(DownloadsInterceptor.class.getName());

	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws IOException {
		if (request.getSession().getAttribute("user") == null) {
			LOGGER.error("Couldn't find a user object within the session");

			response.sendRedirect(request.getContextPath() + URLUtility.getUrl(URL.LOGIN));
			return false;
		}
		return true;
	}

	@Override
	public final void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
		LOGGER.info("Trying to load language...");
		request.setAttribute("language", LanguageUtility.getLanguage((Locale) request.getSession().getAttribute("language")));
		super.postHandle(request, response, handler, modelAndView);
	}
}
