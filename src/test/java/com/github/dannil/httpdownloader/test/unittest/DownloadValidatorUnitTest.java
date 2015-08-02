package com.github.dannil.httpdownloader.test.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.validator.DownloadValidator;

/**
 * Unit tests for download validator
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class DownloadValidatorUnitTest {

	@Autowired
	private DownloadValidator downloadValidator;

	@Test(expected = ClassCastException.class)
	public final void tryToValidateNonDownloadObject() {
		final User user = new User(TestUtility.getUser());
		this.downloadValidator.validate(user, null);
	}

	@Test
	public final void validateDownloadWithMalformedTitleAndUrl() {
		final Download download = new Download(TestUtility.getDownload());
		download.setTitle(null);
		download.setUrl(null);

		final BindingResult result = new BeanPropertyBindingResult(download, "download");
		this.downloadValidator.validate(download, result);

		Assert.assertTrue(result.hasFieldErrors("title"));
		Assert.assertTrue(result.hasFieldErrors("url"));
	}

	@Test
	public final void validateDownloadSuccess() {
		final Download download = new Download(TestUtility.getDownload());

		final BindingResult result = new BeanPropertyBindingResult(download, "download");
		this.downloadValidator.validate(download, result);

		Assert.assertFalse(result.hasErrors());
	}

}
