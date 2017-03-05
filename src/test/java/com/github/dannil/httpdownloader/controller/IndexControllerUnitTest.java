package com.github.dannil.httpdownloader.controller;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.dannil.httpdownloader.controller.IndexController;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for index controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:/WEB-INF/configuration/framework/bean-context.xml",
        "classpath:/WEB-INF/configuration/framework/application-context.xml" })
public class IndexControllerUnitTest {

    @Autowired
    private IndexController indexController;

    @Test
    public void loadIndexPage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        String path = this.indexController.indexGET(request, session);
        Assert.assertEquals(URLUtility.getUrl(URL.INDEX), path);
    }

}
