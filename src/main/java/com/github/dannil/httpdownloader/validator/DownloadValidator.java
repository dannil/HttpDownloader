package com.github.dannil.httpdownloader.validator;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.dannil.httpdownloader.model.Download;

/**
 * Class which handles validation for downloads.
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Component(value = "DownloadValidator")
public class DownloadValidator extends GenericValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadValidator.class.getName());

    @Override
    public boolean supports(Class<?> clazz) {
        return Download.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Download download;
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
            String errorCode = "invalid_url";
            errors.rejectValue("url", errorCode);
            LOGGER.error(errorCode, e);
        }
    }
}
