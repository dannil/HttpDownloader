// Author: 	Daniel Nilsson
// Date: 	2014-08-18
// Changed: 2014-08-18

package org.dannil.httpdownloader.service;

import org.dannil.httpdownloader.model.User;

public interface ILoginService {

	//Others, defined in LoginRepository
	public User findByEmail(String email);
	public User findByPassword(String password);
	
	//Delegated to LoginService
	public boolean isLoginCorrect(String email, String password);
	
}
