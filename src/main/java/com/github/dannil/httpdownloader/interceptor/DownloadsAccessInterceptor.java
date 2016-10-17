package com.github.dannil.httpdownloader.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.github.dannil.httpdownloader.exception.UnqualifiedAccessException;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;

/**
 * Class for handling operations to perform on download access which involves
 * fetching data from an user, such as deleting a download. This is important;
 * otherwise a malicious user could attempt to delete another user's download
 * by injection.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public final class DownloadsAccessInterceptor extends HandlerInterceptorAdapter {

	// private final static Logger LOGGER =
	// Logger.getLogger(DownloadsAccessInterceptor.class.getName());

	@Override
	public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler) throws UnqualifiedAccessException {
		@SuppressWarnings("unchecked")
		final Map<String, String> pathVariables = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		final Long id = Long.parseLong(pathVariables.get("id"));

		final User user = (User) request.getSession().getAttribute("user");
		final Download download = user.getDownload(id);

		if (download == null || download.getUser() == null) {
			throw new UnqualifiedAccessException();
		}

		if (!user.getId().equals(download.getUser().getId())) {
			throw new UnqualifiedAccessException();
		}

		return true;
	}

}