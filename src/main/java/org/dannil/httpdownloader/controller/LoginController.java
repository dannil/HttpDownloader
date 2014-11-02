// Author: 	Daniel Nilsson
// Date: 	2014-08-18
// Changed: 2014-08-21

package org.dannil.httpdownloader.controller;

import org.dannil.httpdownloader.utility.PathUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "LoginController")
@RequestMapping("/login")
public final class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public final void loginGET() {
		System.out.println("Loading " + PathUtility.VIEW_FOLDER + "/login.xhtml...");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public final void loginPOST() {
		
	}
	
}
