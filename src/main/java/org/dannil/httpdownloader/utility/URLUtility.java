package org.dannil.httpdownloader.utility;

import java.util.Arrays;

import org.dannil.httpdownloader.model.URL;

/**
 * Class which constructs strings for URL operations.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     0.0.1-SNAPSHOT
 * @since       0.0.1-SNAPSHOT
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
	 * Return the specific URL string which matches the specified enumerable and
	 * prepend a redirect clause.
	 * 
	 * @param url
	 * 				the URL to fetch
	 * 
	 * @return the URL from the config file which corresponds with the specified enumerable,
	 * 		   with a redirect clause prepended to it
	 * 
	 * @see org.dannil.httpdownloader.utility.URLUtility#getUrl(URL)
	 * @see org.dannil.httpdownloader.utility.URLUtility#redirect(String)
	 */
	public static final String getUrlRedirect(final URL url) {
		return redirect(getUrl(url));
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
		if (!Arrays.asList(URL.values()).contains(url)) {
			throw new IllegalArgumentException(url + " is not an existing enumerable for " + URL.class.getName());
		}
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
				break;
		}
		return null;
	}

}
