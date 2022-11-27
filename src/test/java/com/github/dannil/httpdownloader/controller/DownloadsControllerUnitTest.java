package com.github.dannil.httpdownloader.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.Download;
import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.test.utility.TestUtility;
import com.github.dannil.httpdownloader.utility.URLUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Unit tests for downloads controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class DownloadsControllerUnitTest {

    @Autowired
    private DownloadsController downloadsController;

    @Test
    public void loadDownloadsPage() {
        User user = TestUtility.getUser();
        Download download = TestUtility.getDownload();

        user.addDownload(download);

        HttpServletRequest request = mock(HttpServletRequest.class);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);

        String path = this.downloadsController.downloadsGET(request, session);

        assertEquals(URLUtility.getUrl(URL.DOWNLOADS), path);
    }

    @Test
    public void loadAddDownloadsPage() {
        User user = TestUtility.getUser();
        HttpServletRequest request = mock(HttpServletRequest.class);

        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);

        String path = this.downloadsController.downloadsAddGET(request, session);

        assertEquals(URLUtility.getUrl(URL.DOWNLOADS_ADD), path);
    }

}
