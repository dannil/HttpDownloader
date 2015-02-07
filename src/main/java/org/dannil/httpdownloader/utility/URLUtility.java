package org.dannil.httpdownloader.utility;

import org.dannil.httpdownloader.model.URL;

/**
 * Class which constructs strings for URL operations.
 * 
 * @author Daniel Nilsson
 */
public final class URLUtility {

	private static final XMLUtility xmlUtility;

	static {
		xmlUtility = new XMLUtility(PathUtility.getAbsolutePathToConfiguration() + "config.xml");
	}

	private URLUtility() throws IllegalAccessException {
		throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
	}

	/**
	 * Construct a string where to redirect the user.
	 * 
	 * @param destination 
	 *						which URL to redirect the user to
	 *
	 * @return a string where to redirect the user
	 */
	public static final String redirect(final String destination) {
		return "redirect:" + destination;
	}

	/**
	 * Return a specific URL string, which is decided by the specified enumerable. By
	 * utilizing this class, we achieve a layer between the controllers and the 
	 * config file, and can therefore change implementation easily if needed.
	 * 
	 * @param url
	 * 				the URL to fetch
	 * 
	 * @return the URL from the config file which corresponds with the specified enumerable
	 */
	public static final String getUrl(final URL url) {
		switch (url) {
			case INDEX:
				return xmlUtility.getElementValue("/configuration/app/urls/index");

			case DOWNLOADS:
				return xmlUtility.getElementValue("/configuration/app/urls/downloads");

			case DOWNLOADS_ADD:
				return xmlUtility.getElementValue("/configuration/app/urls/downloadsadd");

			case LOGIN:
				return xmlUtility.getElementValue("/configuration/app/urls/login");

			case REGISTER:
				return xmlUtility.getElementValue("/configuration/app/urls/register");

			default:
				return xmlUtility.getElementValue("/configuration/app/urls/index");
		}
	}

}
