package org.dannil.httpdownloader.utility;

/**
 * Utility class for performing generic validations on objects
 * 
 * @author Daniel
 *
 */
public class ValidationUtility {

	/**
	 * Perform a null check on the given object.
	 * 
	 * @param arg0
	 * 				- The object to be validated
	 * 
	 * @return true if the object is null; otherwise false
	 */
	public static final boolean isNull(Object arg0) {
		if (arg0 == null) {
			return true;
		}
		return false;
	}

}
