package com.github.dannil.httpdownloader.test.unittest;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.utility.ConfigUtility;
import com.github.dannil.httpdownloader.utility.FileUtility;

/**
 * Unit tests for file utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class FileUtilityUnitTest {

	@Test(expected = Exception.class)
	public void fileUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<FileUtility> constructor = FileUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = IOException.class)
	public void getAllPropertyFilesNonExistingDirectory() throws IOException {
		FileUtility.getProperties("blabla/blabla");
	}

	@Test
	public void getAllPropertyFiles() throws IOException {
		LinkedList<Properties> properties = new LinkedList<Properties>(FileUtility.getProperties(ConfigUtility
				.getPropertiesAbsolutePath()));

		Assert.assertNotEquals(0, properties.size());
	}

}
