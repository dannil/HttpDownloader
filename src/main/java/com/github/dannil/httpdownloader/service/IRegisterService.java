package com.github.dannil.httpdownloader.service;

import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for RegisterService.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface IRegisterService {

	// Others, defined in UserRepository
	User save(User user);

	User findByEmail(String email);

}
