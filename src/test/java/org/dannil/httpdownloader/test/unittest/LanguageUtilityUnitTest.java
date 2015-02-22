package org.dannil.httpdownloader.test.unittest;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.dannil.httpdownloader.utility.ConfigUtility;
import org.dannil.httpdownloader.utility.FileUtility;
import org.dannil.httpdownloader.utility.LanguageUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for language utility
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class LanguageUtilityUnitTest {

	@Test(expected = Exception.class)
	public final void languageUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<LanguageUtility> constructor = LanguageUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public final void getDefaultLanguage() {
		final ResourceBundle language = LanguageUtility.getLanguage(LanguageUtility.getDefaultLanguageFromConfigFile());

		Assert.assertTrue(language.getString("languagetag").equals(LanguageUtility.getDefaultLanguageFromConfigFile().toLanguageTag()));
	}

	@Test
	public final void getNonExistingLanguage() {
		final ResourceBundle language = LanguageUtility.getLanguage(Locale.forLanguageTag("blabla-blabla"));

		final Locale defaultLocale = Locale.forLanguageTag("en-US");

		Assert.assertTrue(language.getString("languagetag").equals(defaultLocale.toLanguageTag()));
	}

	// TODO finish test
	@Test(expected = RuntimeException.class)
	public final void getDefaultLanguageWithNonExistingProperties() throws IOException {
		// Steps:
		// 1. Load all the property files from the disk and save them in a list
		// 2. Delete all the property files from the disk
		// 3. Run the getLanguage(HttpSession) method; this should now throw an
		// exception as no property files can be found
		// 4. Restore all the property files by using the previous mentioned
		// list

		LinkedList<Properties> properties = new LinkedList<Properties>(FileUtility.getProperties(ConfigUtility.getPropertiesAbsolutePath()));

		throw new RuntimeException();
	}

}
