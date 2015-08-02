package com.github.dannil.httpdownloader.test.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.dannil.httpdownloader.utility.ConfigUtility;
import com.github.dannil.httpdownloader.utility.XMLUtility;

/**
 * Unit tests for XML utility
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class XMLUtilityUnitTest {

	@Test(expected = RuntimeException.class)
	public final void illegalXPathExpression() {
		XMLUtility utility = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
		utility.getElementValue("!#¤%&/()=?`");
	}

}
