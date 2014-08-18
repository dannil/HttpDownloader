// Author: 	Daniel Nilsson
// Date: 	2014-08-18
// Changed: 2014-08-18

package org.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.inject.Inject;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.LoginRepository;
import org.springframework.stereotype.Service;

@Service(value = "LoginService")
public final class LoginService implements ILoginService {
	
	@Inject
	LoginRepository repository;

	@Override
	public final User findByEmail(String email) {
		return this.repository.findByEmail(email);
	}
	
	@Override
	public final User findByPassword(String password) {
		return this.repository.findByPassword(password);
	}

	@Override
	public final boolean isLoginCorrect(String email, String password) {
		final boolean userExists = (findByEmail(email) != null) ? true : false;
		if (userExists) {
			final User user = findByEmail(email);
			try {
				if (user.getEmail().equals(email) && PasswordService.validateHashedPassword(password, user.getPassword())) {
					return true;
				}
			}
			catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

}
