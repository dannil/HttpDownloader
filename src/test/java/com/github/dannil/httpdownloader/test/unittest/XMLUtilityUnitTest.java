package com.github.dannil.httpdownloader.test.unittest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.utility.ConfigUtility;
import com.github.dannil.httpdownloader.utility.XMLUtility;

/**
 * Unit tests for XML utility
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
		"classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class XMLUtilityUnitTest {

	@Test(expected = RuntimeException.class)
	public  void illegalXPathExpression() {
		XMLUtility utility = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
		utility.getElementValue("!#¤%&/()=?`");
	}

}
