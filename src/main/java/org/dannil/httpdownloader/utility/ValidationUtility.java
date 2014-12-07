package org.dannil.httpdownloader.utility;

/**
 * Utility class for performing generic validations on objects.
 * 
 * @author Daniel Nilsson
 *
 */
public class ValidationUtility {

	private ValidationUtility() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Perform a null check on the given object.
	 * 
	 * @param obj
	 * 				the object to be validated
	 * 
	 * @return true if the object is null, false if not
	 */
	public static final boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

}
