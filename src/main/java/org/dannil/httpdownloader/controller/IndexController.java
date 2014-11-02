// Author: 	Daniel Nilsson
// Date: 	2014-07-09
// Changed: 2014-08-23

package org.dannil.httpdownloader.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "IndexController")
@RequestMapping("/index")
public final class IndexController {
	
	// Loads index.xhtml from /WEB-INF/view
	@RequestMapping(method = RequestMethod.GET)
	public final void indexGET(final HttpSession session) {
		final ResourceBundle languageBundle = ResourceBundle.getBundle("/WEB-INF/conf/languages/language", new Locale("sv", "SE"));
		System.out.println(languageBundle.getString("hello_world"));
		
		session.setAttribute("languageBundle", languageBundle);
		
		System.out.println("Loading " + PathUtility.VIEW_FOLDER + "/index.xhtml...");
	}
	
}
