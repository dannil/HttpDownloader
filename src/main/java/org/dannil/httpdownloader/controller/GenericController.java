package org.dannil.httpdownloader.controller;

import java.util.ResourceBundle;

import org.dannil.httpdownloader.utility.LanguageUtility;

/**
 * Generic controller for methods to be used on all controllers
 * 
 * @author Daniel
 *
 */
public class GenericController {

	protected ResourceBundle languageBundle;

	/**
	 * Initialize a language
	 */
	public void initializeLanguage() {
		this.languageBundle = LanguageUtility.getLanguageBundle();
	}

}
