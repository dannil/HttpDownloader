package org.dannil.httpdownloader.utility;

/**
 * Class which constructs strings for URL operations.
 * 
 * @author Daniel Nilsson
 */
public final class URLUtility {

	private URLUtility() {
		throw new UnsupportedOperationException();
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

}
