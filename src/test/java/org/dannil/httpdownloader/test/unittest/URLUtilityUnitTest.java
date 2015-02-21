package org.dannil.httpdownloader.test.unittest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.utility.URLUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for URL utility
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class URLUtilityUnitTest {

	@Test(expected = Exception.class)
	public final void urlUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Constructor<URLUtility> constructor = URLUtility.class.getDeclaredConstructor();
		Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public final void listUrls() {
		Assert.assertNotNull(URL.values());
	}

	@Test
	public final void convertStringToEnum() {
		Assert.assertEquals(URL.INDEX, URL.valueOf("INDEX"));
	}

	@Test
	public final void getUrlWithNone() {
		Assert.assertNull(URLUtility.getUrl(URL.NONE));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void getUrlWithNullInput() {
		URLUtility.getUrl(null);
	}

}
