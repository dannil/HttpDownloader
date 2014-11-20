package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

public interface ILoginService {

	// Others, defined in UserRepository
	public User findById(final long id);

	public User findByEmail(final String email);

	// Delegated to LoginService
	public boolean isLoginCorrect(final String email, final String password);

}
