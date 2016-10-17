package com.github.dannil.httpdownloader.service;

import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for LoginService.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface ILoginService {

	// Others, defined in UserRepository
	User findById(final long id);

	User findByEmail(final String email);

	// Delegated to LoginService
	/**
	 * Find a user with the specified email and password.
	 *
	 * @param email
	 *            the user's email
	 * @param password
	 *            the user's password
	 * 
	 * @return a user with the specified email and password
	 */
	User login(final String email, final String password);

}
