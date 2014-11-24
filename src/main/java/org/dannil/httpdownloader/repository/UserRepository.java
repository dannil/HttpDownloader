package org.dannil.httpdownloader.repository;

import org.dannil.httpdownloader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(final String email);

	public User findByPassword(final String password);

}