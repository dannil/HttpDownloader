package org.dannil.httpdownloader.validator;

public abstract class GenericValidator {
	
	protected final boolean isLettersOnly(String string) {
		return string.matches(".*[^0-9].*");
	}
	
	// CHANGE IN THE FUTURE
	protected final boolean isNumbersOnly(String string) {
		return string.matches(".*[0-9]+.*");
	}
	
	protected final boolean isValidEmail(String email) {
		return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

}
