package org.dannil.httpdownloader.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.exception.UnqualifiedAccessException;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Class for handling operations to perform on download access, such as 
 * listing all downloads and fetching a download.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@Component
public final class DownloadsAccessInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = Logger.getLogger(DownloadsAccessInterceptor.class.getName());

	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws UnqualifiedAccessException {
		@SuppressWarnings("unchecked")
		final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		final Long id = Long.parseLong(pathVariables.get("id"));

		final User user = (User) request.getSession().getAttribute("user");
		final Download download = user.getDownload(id);

		if (download == null || download.getUser() == null) {
			throw new UnqualifiedAccessException();
		}

		if (user.getId() != download.getUser().getId()) {
			throw new UnqualifiedAccessException();
		}

		return true;
	}

	@Override
	public final void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}