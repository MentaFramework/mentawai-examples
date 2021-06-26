package org.menta.service.impl;

import org.menta.dao.UserDAO;
import org.menta.exception.LoginException;
import org.menta.exception.LoginException.Type;
import org.menta.model.User;
import org.menta.service.UserService;
import org.mentawai.mail.Letter;
import org.mentawai.mail.SimpleEmail;
import org.mentawai.mail.TextLetter;

/**
 * 
 * Service that do some business logic before access DAO layer.
 * 
 */
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

	public UserServiceImpl(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User findByUsername(final String username) {
		if (username == null) {
			throw new IllegalArgumentException("Username cannot be null");
		}

		return userDAO.findByUsername(username);
	}

	@Override
	public User load(final int id) {
		if (id < 1) {
			return null;
		}

		return userDAO.load(id);
	}

	@Override
	public void save(final User u) {

		if (u.getUsername() == null || u.getUsername().isEmpty()) {
			throw new IllegalStateException("Username cannot be null");
		}

		if (u.getId() > 0) {
			userDAO.update(u);

		}
		else {
			userDAO.insert(u);

			// Send email to any new user with password
			sendWelcomeMail(u);
		}

	}

	private void sendWelcomeMail(final User u) {

		final Letter welcome = new TextLetter("welcome.txt");
		welcome.setAttribute("username", u.getUsername());
		welcome.setAttribute("password", u.getPassword());

		try {

			final String subject = welcome.getSubject(u.getLocale());
			final String body = welcome.getText(u.getLocale());

			SimpleEmail.sendLater(u.getUsername(), u.getEmail(), subject, body);

		}
		catch (final Exception e) {

			System.err.println("Error sending email to: " + u.getEmail());

			e.printStackTrace();
		}

	}

	@Override
	public User login(final String username, String password) throws LoginException {

		final User userFound = userDAO.findByUsername(username);

		if (userFound == null) {
			throw new LoginException(Type.USERNAME_NOTFOUND);
		}

		if (!userFound.getPassword().equals(password)) {
			throw new LoginException(Type.WRONG_PASSWORD);
		}

		return userFound;
	}
}
