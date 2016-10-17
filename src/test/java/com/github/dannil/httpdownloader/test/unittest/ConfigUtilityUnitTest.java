package com.github.dannil.httpdownloader.test.unittest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith; import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.utility.ConfigUtility;

/**
 * Unit tests for config utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class) @WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml", "classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class ConfigUtilityUnitTest {

	@Test(expected = Exception.class)
	public final void configUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<ConfigUtility> constructor = ConfigUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public final void absolutePathIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getAbsolutePath());
	}

	@Test
	public final void absolutePathToConfigurationIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getConfigurationAbsolutePath());
	}

	@Test
	public final void absolutePathToPropertiesIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getPropertiesAbsolutePath());
	}

	@Test
	public final void absolutePathToLanguageIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getLanguageAbsolutePath());
	}

	@Test
	public final void absolutePathToDownloadsIsNotNull() {
		Assert.assertNotNull(ConfigUtility.getDownloadsAbsolutePath());
	}

}
