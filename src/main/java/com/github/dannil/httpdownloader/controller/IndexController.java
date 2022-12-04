package com.github.dannil.httpdownloader.controller;

import com.github.dannil.httpdownloader.model.URL;
import com.github.dannil.httpdownloader.utility.URLUtility;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for mappings on index.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Controller(value = "IndexController")
@RequestMapping("/index")
public class IndexController {

    /**
     * Default constructor.
     */
    public IndexController() {

    }

    /**
     * Landing controller for application.
     *
     * @return an URL to the index page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String indexGET() {
        return URLUtility.getUrl(URL.INDEX);
    }

}
