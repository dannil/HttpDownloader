package org.dannil.httpdownloader.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {

	final Logger logger = LogManager.getLogger(IndexController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public final void indexGET() {
		this.logger.debug("Hello servlet!");
		System.out.println("Loading index.xhtml...");
		
		System.out.println(this.logger.getLevel());
		
		System.out.println(logger.isErrorEnabled());
		System.out.println(logger.isDebugEnabled());
		
		//logs debug message
		if(this.logger.isDebugEnabled()){
			this.logger.debug("getWelcome is executed!");
		} else {
			System.out.println("not enabled");
		}
 
		//logs exception
		//this.logger.error("This is Error message", new Exception("Testing"));
	}
	
}
