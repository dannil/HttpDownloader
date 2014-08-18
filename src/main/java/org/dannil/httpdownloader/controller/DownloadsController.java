// Author: 	Daniel Nilsson
// Date: 	2014-07-09
// Changed: 2014-08-18

package org.dannil.httpdownloader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "DownloadsController")
@RequestMapping("/downloads")
public final class DownloadsController {

	@RequestMapping(method = RequestMethod.GET)
	public final void downloadsGET() {
		// Loads downloads.xhtml from /WEB-INF/view
		System.out.println("Loading downloads.xhtml...");
	}
	
}
