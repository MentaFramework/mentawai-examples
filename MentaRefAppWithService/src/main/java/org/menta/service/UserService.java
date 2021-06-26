package org.menta.service;

import org.menta.exception.LoginException;
import org.menta.model.User;

public interface UserService {

	public User findByUsername(String username);
	
	public void save(User u);
	
	public User load(int id);

	/**
	 * @return User if username and password match
	 * @throws LoginException if username or password doesn't match
	 */
	public User login(String username, String password) throws LoginException;
	
}
