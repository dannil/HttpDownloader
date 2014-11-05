package org.dannil.httpdownloader.repository;

import org.dannil.httpdownloader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Long> {

	public User findByPassword(String password);

	public User findByEmail(String email);

}