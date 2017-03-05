package com.github.dannil.httpdownloader.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for config utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
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
