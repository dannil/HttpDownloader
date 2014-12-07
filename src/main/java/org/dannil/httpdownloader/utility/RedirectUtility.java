package org.dannil.httpdownloader.utility;

/**
 * Class which constructs strings for redirects.
 * 
 * @author Daniel Nilsson
 */
public final class RedirectUtility {

	private RedirectUtility() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Construct a string where to send the user.
	 * 
	 * @param destination 
	 *						where to send the user 
	 *
	 * @return a string where to send the user
	 */
	public static final String redirect(final String destination) {
		return "redirect:" + destination;
	}

}
