package com.github.dannil.httpdownloader.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

/**
 * Unit tests for index controller
 * 
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 1.0.0
 */
@SpringBootTest
public class IndexControllerUnitTest {

    @Autowired
    private IndexController indexController;

    @Test
    public void loadIndexPage() {
        String path = this.indexController.indexGET();
        assertEquals(URLUtility.getUrl(URL.INDEX), path);
    }

}
