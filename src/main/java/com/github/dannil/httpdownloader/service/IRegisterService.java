package com.github.dannil.httpdownloader.service;

import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for RegisterService.
 * 
 * @author Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version 1.0.0
 * @since 0.0.1-SNAPSHOT
 */
public interface IRegisterService {

	// Others, defined in UserRepository
	User save(final User user);

	User findByEmail(final String email);

}
