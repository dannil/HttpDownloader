package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

/**
 * Interface for LoginService.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     0.0.1-SNAPSHOT
 * @since       0.0.1-SNAPSHOT
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
	 * 					the user's email
	 * @param password
	 * 					the user's password
	 * 
	 * @return a user with the specified email and password
	 */
	public User login(final String email, final String password);

}
