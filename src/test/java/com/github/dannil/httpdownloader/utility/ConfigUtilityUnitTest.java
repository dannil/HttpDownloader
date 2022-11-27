package com.github.dannil.httpdownloader.utility;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit tests for config utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class ConfigUtilityUnitTest {

    @Test
    public void configUtilityConstructorThrowsExceptionOnInstantiation()
            throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Constructor<ConfigUtility> constructor = ConfigUtility.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> {
            constructor.newInstance();
        });
    }

    @Test
    public void absolutePathIsNotNull() {
        assertNotNull(ConfigUtility.getAbsolutePath());
    }

    @Test
    public void absolutePathToConfigurationIsNotNull() {
        assertNotNull(ConfigUtility.getConfigurationAbsolutePath());
    }

    @Test
    public void absolutePathToPropertiesIsNotNull() {
        assertNotNull(ConfigUtility.getPropertiesAbsolutePath());
    }

    @Test
    public void absolutePathToDownloadsIsNotNull() {
        assertNotNull(ConfigUtility.getDownloadsAbsolutePath());
    }

}
