package com.github.dannil.httpdownloader.service;

import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for RegisterService.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface IRegisterService {

    /**
     * Persist a user.
     *
     * @param user the user
     * @return the user
     */
    User save(User user);

    /**
     * Find a user by it's email.
     *
     * @param email the email of the user
     * @return the user with the specified email
     */
    User findByEmail(String email);

}
