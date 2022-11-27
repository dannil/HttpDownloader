package com.github.dannil.httpdownloader.interceptor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for downloads interceptor
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class DownloadsInterceptorUnitTest {

    @Autowired
    private DownloadsInterceptor downloadsInterceptor;

    @Test
    public void userAttributeIsNullAtInterceptor() throws Exception {
        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(null);

        HttpServletResponse response = mock(HttpServletResponse.class);

        boolean result = this.downloadsInterceptor.preHandle(request, response, null);

        assertFalse(result);
    }

    @Test
    public void userAttributeIsNotNullAtInterceptor() throws Exception {
        User user = TestUtility.getUser();

        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(user);

        HttpServletResponse response = mock(HttpServletResponse.class);

        boolean result = this.downloadsInterceptor.preHandle(request, response, null);

        assertTrue(result);
    }

    @Test
    public void postHandleOnDownloads() throws Exception {
        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = new Object();
        ModelAndView modelAndView = new ModelAndView();

        this.downloadsInterceptor.postHandle(request, response, handler, modelAndView);
    }

}
