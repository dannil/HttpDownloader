package org.dannil.httpdownloader.utility;

/**
 * Class which constructs strings for redirects.
 * 
 * @author Daniel
 */
public final class RedirectUtility {

	/**
	 * Private constructor to make the class a singleton.
	 */
	private RedirectUtility() {
		throw new AssertionError();
	}

	/**
	 * Construct a string where to send the user.
	 * 
	 * @param destination 
	 *						- where to send the user 
	 *
	 * @return A string where to send the user
	 */
	public static final String redirect(final String destination) {
		return "redirect:" + destination;
	}

}
