package org.dannil.HttpDownloader.controller;

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
