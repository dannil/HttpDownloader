package com.github.dannil.httpdownloader.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for download validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class DownloadValidatorUnitTest {

    @Autowired
    private DownloadValidator downloadValidator;

    @Test
    public void tryToValidateNonDownloadObject() {
        User user = TestUtility.getUser();
        assertThrows(ClassCastException.class, () -> {
            this.downloadValidator.validate(user, null);
        });
    }

    @Test
    public void validateDownloadWithMalformedTitleAndUrl() {
        Download download = TestUtility.getDownload();
        download.setTitle(null);
        download.setUrl(null);

        BindingResult result = new BeanPropertyBindingResult(download, "download");
        this.downloadValidator.validate(download, result);

        assertTrue(result.hasFieldErrors("title"));
        assertTrue(result.hasFieldErrors("url"));
    }

    @Test
    public void validateDownloadSuccess() {
        Download download = TestUtility.getDownload();

        BindingResult result = new BeanPropertyBindingResult(download, "download");
        this.downloadValidator.validate(download, result);

        assertFalse(result.hasErrors());
    }

}
