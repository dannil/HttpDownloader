package org.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles back end operations for login.
 * 
 * @author Daniel Nilsson
 */
@Service(value = "LoginService")
public final class LoginService implements ILoginService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Find a user by it's id.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
	 */
	@Override
	public final User findById(final long id) {
		return this.userRepository.findOne(id);
	}

	/**
	 * Find a user by it's email.
	 * 
	 * @see org.dannil.httpdownloader.repository.UserRepository#findByEmail(String)
	 */
	@Override
	public final User findByEmail(final String email) {
		return this.userRepository.findByEmail(email);
	}

	/**
	 * Find a user with the specified email and password.
	 * 
	 * @see org.dannil.httpdownloader.service.ILoginService#login(String, String)
	 */
	@Override
	public final User login(final String email, final String password) {
		final User user = this.findByEmail(email);
		if (user != null) {
			try {
				if (user.getEmail().equals(email) && PasswordUtility.validateHashedPassword(password, user.getPassword())) {
					return new User(user);
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}
