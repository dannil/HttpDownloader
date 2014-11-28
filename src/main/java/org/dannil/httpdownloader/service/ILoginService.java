package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

/**
 * Interface for LoginService
 * 
 * @author Daniel Nilsson
 */
public interface ILoginService {

	// Others, defined in UserRepository
	public User findById(final long id);

	public User findByEmail(final String email);

	// Delegated to LoginService
	/**
	 * Find a user with the specified email and password.
	 *
	 * @param email
	 * 					- The user's email
	 * @param password
	 * 					- The user's password
	 * 
	 * @return A user with the specified email and password
	 */
	public boolean isLoginCorrect(final String email, final String password);

}
