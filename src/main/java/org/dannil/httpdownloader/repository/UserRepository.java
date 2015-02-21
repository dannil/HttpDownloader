package org.dannil.httpdownloader.repository;

import org.dannil.httpdownloader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting users and other operations on these.
 * 
 * @author      Daniel Nilsson (daniel.nilsson @ dannils.se)
 * @version     1.0.0
 * @since       0.0.1-SNAPSHOT
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Find a user for the specified email.
	 * 
	 * @param email
	 * 				the email for the user
	 * 
	 * @return a user with the specified email
	 */
	public User findByEmail(final String email);

	/**
	 * Find a user for the specified password. This could theoretically return more than one 
	 * user as two users having the same password is permitted. Considering the extremely small 
	 * chance that a password collision occurs, it's unnecessary complexity to consider this constraint.
	 * 
	 * @param password
	 * 					the password for the user
	 * 
	 * @return a user with the specified password
	 */
	public User findByPassword(final String password);

}