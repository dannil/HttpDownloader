package org.dannil.httpdownloader.test.unittest;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Properties;

import org.dannil.httpdownloader.utility.ConfigUtility;
import org.dannil.httpdownloader.utility.FileUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for file utility
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class FileUtilityUnitTest {

	@Test(expected = Exception.class)
	public final void fileUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<FileUtility> constructor = FileUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = NullPointerException.class)
	public final void getAllPropertyFilesNonExistingDirectory() throws IOException {
		FileUtility.getProperties("blabla/blabla");
	}

	@Test
	public final void getAllPropertyFiles() throws IOException {
		LinkedList<Properties> properties = new LinkedList<Properties>(FileUtility.getProperties(ConfigUtility.getPropertiesAbsolutePath()));

		Assert.assertNotEquals(0, properties.size());
	}

}
