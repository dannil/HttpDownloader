package org.dannil.httpdownloader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {
	
	@RequestMapping(method = RequestMethod.GET)
	public final void indexGET() {
		System.out.println("Loading index.xhtml...");
	}
	
}
