package org.menta.dao;

import org.menta.model.User;

public interface UserDAO {
	
	public User findByUsername(String username);
	
	public void insert(User u);
	
	public void update(User u);
	
	public boolean insertOrUpdate(User u);
	
	public User load(int id);
}