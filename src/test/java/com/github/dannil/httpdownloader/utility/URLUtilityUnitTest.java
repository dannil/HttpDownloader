package com.github.dannil.httpdownloader.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.URL;

/**
 * Unit tests for URL utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class URLUtilityUnitTest {

    @Test
    public void urlUtilityConstructorThrowsExceptionOnInstantiation() throws NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor<URLUtility> constructor = URLUtility.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThrows(Exception.class, () -> {
            constructor.newInstance();
        });
    }

    @Test
    public void listUrls() {
        assertNotNull(URL.values());
    }

    @Test
    public void convertStringToEnum() {
        assertEquals(URL.INDEX, URL.valueOf("INDEX"));
    }

    @Test
    public void getUrlWithNullInput() {
        assertThrows(Exception.class, () -> {
            URLUtility.getUrl(null);
        });
    }

}
