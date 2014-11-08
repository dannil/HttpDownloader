package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

public interface ILoginService {

	// Others, defined in UserRepository
	public User findByEmail(final String email);

	public User findByPassword(final String password);

	// Delegated to LoginService
	public boolean isLoginCorrect(final String email, final String password);

}
