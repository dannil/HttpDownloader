package org.dannil.httpdownloader.validator;

import org.apache.commons.io.FilenameUtils;
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
 * @author Daniel
 */
@Component(value = "DownloadValidator")
public final class DownloadValidator extends GenericValidator implements Validator {

	private final static Logger LOGGER = Logger.getLogger(DownloadValidator.class.getName());

	@Autowired
	private IDownloadService downloadService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final Download download = (Download) target;

		// SIMPLE VALIDATIONS

		// Null validations
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "invalid_title");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, FilenameUtils.getBaseName(download.getUrl()), "invalid_basename");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, FilenameUtils.getExtension(download.getUrl()), "invalid_extension");

		// COMPLEX VALIDATIONS
	}
}
