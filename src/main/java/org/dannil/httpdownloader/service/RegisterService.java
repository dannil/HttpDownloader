package org.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class which handles back end operations for registering.
 * 
 * @author      Daniel Nilsson <daniel.nilsson @ dannils.se>
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
@Service(value = "RegisterService")
public final class RegisterService implements IRegisterService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Persist the specified user.
	 * 
	 * @see org.springframework.data.repository.CrudRepository#save(Object)
	 */
	@Override
	public final User save(final User user) {
		final User temp = new User(user);
		try {
			temp.setPassword(PasswordUtility.getHashedPassword(temp.getPassword()));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
		return this.userRepository.save(temp);
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

}
