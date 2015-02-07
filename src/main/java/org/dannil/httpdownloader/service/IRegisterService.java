package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

/**
 * Interface for RegisterService.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     0.0.1-SNAPSHOT
 * @since       0.0.1-SNAPSHOT
 */
public interface IRegisterService {

	// Others, defined in UserRepository
	public User save(final User user);

	public User findByEmail(final String email);

}
