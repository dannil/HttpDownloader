package com.github.dannil.httpdownloader.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.dannil.httpdownloader.model.URL;

/**
 * Unit tests for URL utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class URLUtilityUnitTest {

    @Test(expected = Exception.class)
    public void urlUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<URLUtility> constructor = URLUtility.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void listUrls() {
        Assert.assertNotNull(URL.values());
    }

    @Test
    public void convertStringToEnum() {
        Assert.assertEquals(URL.INDEX, URL.valueOf("INDEX"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUrlWithNullInput() {
        URLUtility.getUrl(null);
    }

}
