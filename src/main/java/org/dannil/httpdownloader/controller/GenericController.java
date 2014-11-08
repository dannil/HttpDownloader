package org.dannil.httpdownloader.controller;

import java.util.ResourceBundle;

import org.dannil.httpdownloader.utility.LanguageUtility;

/**
 * Generic controller for methods to be used on all controllers.
 * 
 * @author Daniel
 *
 */
public abstract class GenericController {

	protected ResourceBundle languageBundle;

	protected GenericController() {
		this.languageBundle = LanguageUtility.getLanguageBundle();
	}

}
