package com.github.dannil.httpdownloader.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.utility.ConfigUtility;

/**
 * Unit tests for config utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class ConfigUtilityUnitTest {

	@Test(expected = Exception.class)
	public void configUtilityConstructorThrowsExceptionOnInstantiation()
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<ConfigUtility> constructor = ConfigUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void absolutePathIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getAbsolutePath());
	}

	@Test
	public void absolutePathToConfigurationIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getConfigurationAbsolutePath());
	}

	@Test
	public void absolutePathToPropertiesIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getPropertiesAbsolutePath());
	}

	@Test
	public void absolutePathToLanguageIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getLanguageAbsolutePath());
	}

	@Test
	public void absolutePathToDownloadsIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getDownloadsAbsolutePath());
	}

}
