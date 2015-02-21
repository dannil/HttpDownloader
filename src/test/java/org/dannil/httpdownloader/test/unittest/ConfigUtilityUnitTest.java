package org.dannil.httpdownloader.test.unittest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.dannil.httpdownloader.utility.ConfigUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for config utility
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class ConfigUtilityUnitTest {

	@Test(expected = Exception.class)
	public final void configUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
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
