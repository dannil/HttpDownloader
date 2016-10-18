package com.github.dannil.httpdownloader.interceptor;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.dannil.httpdownloader.exception.InterceptorException;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.LanguageUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Class for handling operations to perform on download access, such as
 * listing all downloads and fetching a download.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class DownloadsInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger(DownloadsInterceptor.class.getName());

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

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws InterceptorException {
		LOGGER.info("Trying to load language...");
		request.setAttribute("language",
				LanguageUtility.getLanguage((Locale) request.getSession().getAttribute("language")));
		// super.postHandle(request, response, handler, modelAndView);
	}
}
