package com.github.dannil.httpdownloader.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dannil.httpdownloader.exception.LoginException;
import com.github.dannil.httpdownloader.model.User;
import com.github.dannil.httpdownloader.repository.UserRepository;
import com.github.dannil.httpdownloader.utility.PasswordUtility;

/**
 * Class which handles back end operations for login.
 * 
 * @author Daniel Nilsson (daniel.nilsson94 @ outlook.com)
 * @version 1.0.1-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
@Service(value = "LoginService")
public class LoginService implements ILoginService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Find a user by it's id.
     * 
     * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
     */
    @Override
    public User findById(long id) {
        return this.userRepository.findOne(id);
    }

    /**
     * Find a user by it's email.
     * 
     * @see com.github.dannil.httpdownloader.repository.UserRepository#findByEmail(String)
     */
    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    /**
     * Find a user with the specified email and password.
     * 
     * @see com.github.dannil.httpdownloader.service.ILoginService#login(String, String)
     */
    @Override
    public User login(String email, String password) {
        User user = findByEmail(email);
        if (user != null) {
            try {
                if (PasswordUtility.compareHashedPasswords(password, user.getPassword())) {
                    return user;
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new LoginException(e);
            }
        }
        return null;
    }

}
