package org.dannil.httpdownloader.validator;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.dannil.httpdownloader.model.Download;
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
		return Download.class.equals(clazz);
	}

	@Override
	public final void validate(final Object target, final Errors errors) {
		Download download = null;
		if (this.supports(target.getClass())) {
			download = (Download) target;
		} else {
			throw new ClassCastException("Can't convert " + target.getClass().getName() + " to a Download object");
		}

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "invalid_title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "invalid_url");

		// COMPLEX VALIDATIONS
		try {
			new URL(download.getUrl());
		} catch (MalformedURLException e) {
			errors.rejectValue("url", "invalid_url");
			LOGGER.error(e);
		}
	}
}
