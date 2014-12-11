package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

/**
 * Interface for RegisterService.
 * 
 * @author Daniel Nilsson
 */
public interface IRegisterService {

	// Others, defined in UserRepository
	public User save(final User user);

	public User findByEmail(final String email);

}
