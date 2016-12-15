package com.github.dannil.httpdownloader.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for download validator
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class DownloadValidatorUnitTest {

	@Autowired
	private DownloadValidator downloadValidator;

	@Test(expected = ClassCastException.class)
	public void tryToValidateNonDownloadObject() {
		User user = TestUtility.getUser();
		this.downloadValidator.validate(user, null);
	}

	@Test
	public void validateDownloadWithMalformedTitleAndUrl() {
		Download download = TestUtility.getDownload();
		download.setTitle(null);
		download.setUrl(null);

		BindingResult result = new BeanPropertyBindingResult(download, "download");
		this.downloadValidator.validate(download, result);

		Assert.assertTrue(result.hasFieldErrors("title"));
		Assert.assertTrue(result.hasFieldErrors("url"));
	}

	@Test
	public void validateDownloadSuccess() {
		Download download = TestUtility.getDownload();

		BindingResult result = new BeanPropertyBindingResult(download, "download");
		this.downloadValidator.validate(download, result);

		Assert.assertFalse(result.hasErrors());
	}

}
