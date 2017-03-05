package com.github.dannil.httpdownloader.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for XML utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class XMLUtilityUnitTest {

    @Test(expected = RuntimeException.class)
    public void illegalXPathExpression() {
        XMLUtility utility = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
        utility.getElementValue("!#¤%&/()=?`");
    }

}
