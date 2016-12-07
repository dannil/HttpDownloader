package com.github.dannil.httpdownloader.utility;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.utility.ConfigUtility;
import com.github.dannil.httpdownloader.utility.FileUtility;
import com.github.dannil.httpdownloader.utility.LanguageUtility;

/**
 * Unit tests for language utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class LanguageUtilityUnitTest {

	@Test(expected = Exception.class)
	public void languageUtilityConstructorThrowsExceptionOnInstantiation()
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<LanguageUtility> constructor = LanguageUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void getDefaultLanguage() {
		ResourceBundle language = LanguageUtility.getLanguage(LanguageUtility.getDefaultLanguageFromConfigFile());

		Assert.assertTrue(language.getString("languagetag").equals(LanguageUtility.getDefaultLanguageFromConfigFile().toLanguageTag()));
	}

	@Test
	public void getNonExistingLanguage() {
		ResourceBundle language = LanguageUtility.getLanguage(Locale.forLanguageTag("blabla-blabla"));

		Locale defaultLocale = Locale.forLanguageTag("en-US");

		Assert.assertTrue(language.getString("languagetag").equals(defaultLocale.toLanguageTag()));
	}

	// TODO finish test
	@Test(expected = RuntimeException.class)
	public void getDefaultLanguageWithNonExistingProperties() throws IOException {
		// Steps:
		// 1. Load all the property files from the disk and save them in a list
		// 2. Delete all the property files from the disk
		// 3. Run the getLanguage(HttpSession) method; this should now throw an
		// exception as no property files can be found
		// 4. Restore all the property files by using the previous mentioned
		// list

		LinkedList<Properties> properties = new LinkedList<Properties>(
				FileUtility.getProperties(ConfigUtility.getPropertiesAbsolutePath()));

		throw new RuntimeException();
	}

}
