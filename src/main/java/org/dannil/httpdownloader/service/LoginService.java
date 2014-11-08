package org.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.LoginRepository;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles backend operations for login.
 * 
 * @author Daniel
 */
@Service(value = "LoginService")
public final class LoginService implements ILoginService {

	@Autowired
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
		final User user = findByEmail(email);
		if (user != null) {
			try {
				if (user.getEmail().equals(email) && PasswordUtility.validateHashedPassword(password, user.getPassword())) {
					return true;
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}
}
