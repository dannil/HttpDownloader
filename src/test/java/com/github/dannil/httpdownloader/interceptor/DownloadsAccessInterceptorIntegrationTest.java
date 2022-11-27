package com.github.dannil.httpdownloader.interceptor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.github.dannil.httpdownloader.exception.UnqualifiedAccessException;
import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;

/**
 * Unit tests for downloads access interceptor
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class DownloadsAccessInterceptorIntegrationTest {

    @Autowired
    private DownloadsAccessInterceptor downloadsAccessInterceptor;

    @Test
    public void validateRequestNullDownload() throws UnqualifiedAccessException {
        User user = TestUtility.getUser();

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "1");

        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        HttpServletResponse response = mock(HttpServletResponse.class);

        Object handler = mock(Object.class);

        assertThrows(UnqualifiedAccessException.class, () -> {
            this.downloadsAccessInterceptor.preHandle(request, response, handler);
        });
    }

    @Test
    public void validateRequestDownloadHasNullUser() throws UnqualifiedAccessException {
        User user = TestUtility.getUser();
        Download download = TestUtility.getDownload();

        user.addDownload(download);

        download.setUser(null);

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", download.getId().toString());

        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        HttpServletResponse response = mock(HttpServletResponse.class);

        Object handler = mock(Object.class);

        assertThrows(UnqualifiedAccessException.class, () -> {
            this.downloadsAccessInterceptor.preHandle(request, response, handler);
        });
    }

    // After rewritten logic in both User and Download class to better handle data
    // integrity, this case is not more possible
    //
    // @Test(expected = UnqualifiedAccessException.class)
    // public void validateRequestNonMatchingId() throws UnqualifiedAccessException {
    // User user = TestUtility.getUser();
    // Download download = TestUtility.getDownload();
    //
    // user.addDownload(download);
    //
    // User attempt = TestUtility.deepCopy(user);
    // attempt.setId(user.getId() + 1);
    //
    // Map<String, String> map = new HashMap<String, String>();
    // map.put("id", download.getId().toString());
    //
    // HttpSession session = mock(HttpSession.class);
    //
    // HttpServletRequest request = mock(HttpServletRequest.class);
    // when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
    // when(request.getSession()).thenReturn(session);
    // when(session.getAttribute("user")).thenReturn(attempt);
    //
    // HttpServletResponse response = mock(HttpServletResponse.class);
    //
    // Object handler = mock(Object.class);
    //
    // this.downloadsAccessInterceptor.preHandle(request, response, handler);
    // }

    @Test
    public void validateRequestMatchingId() throws UnqualifiedAccessException {
        User user = TestUtility.getUser();
        Download download = TestUtility.getDownload();

        user.addDownload(download);

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", download.getId().toString());

        HttpSession session = mock(HttpSession.class);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(map);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        HttpServletResponse response = mock(HttpServletResponse.class);

        Object handler = mock(Object.class);

        boolean result = this.downloadsAccessInterceptor.preHandle(request, response, handler);

        assertTrue(result);
    }

    @Test
    public void postHandle() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = mock(Object.class);
        ModelAndView modelAndView = mock(ModelAndView.class);

        this.downloadsAccessInterceptor.postHandle(request, response, handler, modelAndView);
    }

}
