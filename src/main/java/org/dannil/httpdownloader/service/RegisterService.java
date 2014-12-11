package org.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.dannil.httpdownloader.model.User;
import org.dannil.httpdownloader.repository.UserRepository;
import org.dannil.httpdownloader.utility.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "RegisterService")
public final class RegisterService implements IRegisterService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public final User save(final User user) {
		try {
			user.setPassword(PasswordUtility.getHashedPassword(user.getPassword()));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
		return this.userRepository.save(user);
	}

	@Override
	public final User findByEmail(final String email) {
		return this.userRepository.findByEmail(email);
	}

}
