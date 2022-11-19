package com.github.dannil.httpdownloader.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Controller for mappings on index.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Controller(value = "IndexController")
@RequestMapping("/index")
public class IndexController {

    // Landing controller for application, loads index.xhtml from /WEB-INF/view
    @RequestMapping(method = GET)
    public String indexGET(HttpServletRequest request, HttpSession session) {
        return URLUtility.getUrl(URL.INDEX);
    }

}
