package org.dannil.httpdownloader.validator;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.service.IDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class which handles validation for downloads.
 * 
 * @author Daniel Nilsson
 */
@Component(value = "DownloadValidator")
public final class DownloadValidator extends GenericValidator implements Validator {

	private final static Logger LOGGER = Logger.getLogger(DownloadValidator.class.getName());

	@Autowired
	private IDownloadService downloadService;

	@Override
	public final boolean supports(final Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public final void validate(final Object target, final Errors errors) {
		final Download download = (Download) target;

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "invalid_title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "invalid_url");

		// COMPLEX VALIDATIONS
		try {
			URL url = new URL(download.getUrl());
			if (url.getProtocol().equals("")) {
				errors.rejectValue("url", "invalid_url");
			}
		} catch (MalformedURLException e) {
			errors.rejectValue("url", "invalid_url");
			LOGGER.error(e);
		}
	}
}
