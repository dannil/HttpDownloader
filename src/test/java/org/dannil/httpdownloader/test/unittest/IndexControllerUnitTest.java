package org.dannil.httpdownloader.test.unittest;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.controller.IndexController;
import org.dannil.httpdownloader.model.URL;
import org.dannil.httpdownloader.utility.URLUtility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/WEB-INF/configuration/framework/spring-context.xml")
public class IndexControllerUnitTest {

	@Autowired
	private IndexController indexController;

	@Test
	public final void loadIndexPage() {
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpSession session = mock(HttpSession.class);

		final String path = this.indexController.indexGET(request, session);
		Assert.assertEquals(URLUtility.getUrl(URL.INDEX), path);
	}

}
