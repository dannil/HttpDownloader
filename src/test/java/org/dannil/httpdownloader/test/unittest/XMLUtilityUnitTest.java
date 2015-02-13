package org.dannil.httpdownloader.test.unittest;

import org.dannil.httpdownloader.utility.ConfigUtility;
import org.dannil.httpdownloader.utility.XMLUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class XMLUtilityUnitTest {

	@Test(expected = RuntimeException.class)
	public final void illegalXPathExpression() {
		XMLUtility utility = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
		utility.getElementValue("!#¤%&/()=?`");
	}

}
