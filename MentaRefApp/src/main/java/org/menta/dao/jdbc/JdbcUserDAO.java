package org.menta.dao.jdbc;

import java.sql.Connection;
import java.util.List;

import org.menta.dao.UserDAO;
import org.menta.model.User;
import org.mentabean.BeanSession;

public class JdbcUserDAO implements UserDAO {

	///////////////////////////////////////////////////////////
	// WILL BE INJECTED THROUGH IOC + AUTO-WIRING
	//////////////////////////////////////////////////////////
	private BeanSession session;
	private Connection conn;
	
	public JdbcUserDAO(BeanSession session) {
		this.session = session;
		this.conn = session.getConnection();
	}
	
	@Override
	public User load(int id) {
		
		User u = new User();
		u.setId(id);
		
		if (session.load(u)) {
			
			return u;
		}
		
		return null;
	}
	
	@Override
	public void update(User u) {
		
		session.update(u);
			
	}
	
	@Override
	public void insert(User u) {
		
		session.insert(u);
	}
	
	private List<User> find(User u) {
		
		return session.loadList(u);
	}
	
	@Override
	public boolean insertOrUpdate(User u) {
		
		String username = u.getUsername();
		
		User user = findByUsername(username);
		
		if (user != null) {
			
			u.setId(user.getId());
			
			update(u);
			
			return false;
			
		} else {
			
			insert(u);
			
			return true;
		}
	}
	
	@Override
	public User findByUsername(String username) {
		
		User u = new User();
		u.setUsername(username);
		
		List<User> list = find(u);
			
		if (list == null || list.isEmpty()) {
			
			return null;
			
		} else if (list.size() > 1) {
			
			throw new IllegalStateException("More than one user with the same username!");
			
		} else {
			
			return list.get(0);
		}
	}
	
}