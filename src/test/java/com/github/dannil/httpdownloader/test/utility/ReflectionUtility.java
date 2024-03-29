package com.github.dannil.httpdownloader.test.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Utility class for testing not otherwise testable code, with the use of reflection. Such
 * things include, but not limited to, private constructors and private variables.
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public class ReflectionUtility {

    public static final void setValueToFinalStaticField(final Field field, final Object value) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }

}
