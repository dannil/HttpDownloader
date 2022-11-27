package com.github.dannil.httpdownloader.utility;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.exception.ParsingException;

/**
 * Unit tests for XML utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class XMLUtilityUnitTest {

    @Test
    public void illegalXPathExpression() {
        XMLUtility utility = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
        assertThrows(ParsingException.class, () -> {
            utility.getElementValue("!#¤%&/()=?`");
        });
    }

}
