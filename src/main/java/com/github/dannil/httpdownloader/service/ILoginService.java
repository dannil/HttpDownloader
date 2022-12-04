package com.github.dannil.httpdownloader.service;

import com.github.dannil.httpdownloader.model.User;

/**
 * Interface for LoginService.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public interface ILoginService {

    /**
     * Find a user by it's ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    User findById(long id);

    /**
     * Find a user by it's email.
     *
     * @param email the email of the user
     * @return the user with the specified email
     */
    User findByEmail(String email);

    /**
     * Find a user with the specified email and password.
     *
     * @param email
     *            the user's email
     * @param password
     *            the user's password
     *
     * @return a user with the specified email and password
     */
    User login(String email, String password);

}
