// Author: 	Daniel Nilsson
// Date: 	2014-08-18
// Changed: 2014-08-18

package org.dannil.httpdownloader.repository;

import org.dannil.httpdownloader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Integer> {

	public User findByPassword(String password);
	public User findByEmail(String email);
	
}