package org.menta.action;

import org.menta.dao.UserDAO;
import org.menta.model.User;
import org.mentawai.core.BaseAction;
import org.mentawai.mail.Letter;
import org.mentawai.mail.SimpleEmail;
import org.mentawai.mail.TextLetter;
import org.mentawai.rule.EmailRule;
import org.mentawai.rule.EqualRule;
import org.mentawai.rule.MethodRule;
import org.mentawai.rule.RegexRule;
import org.mentawai.validation.Validatable;
import org.mentawai.validation.Validator;

public class UserAction extends BaseAction implements Validatable {
	
	private final UserDAO userDAO;
	
	public UserAction(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	// Validate the fields for the user form...
	@Override
	public void prepareValidator(Validator val, String method) {
		
		String username_regex = "^[A-Za-z][A-Za-z0-9\\-\\_\\.]*[A-Za-z0-9]$";
		
		if (method != null && method.equals("add") && isPost()) {
			
			val.requiredFields("required_field", "username", "password", "email", "groupId", "languageId");
			
			val.requiredLists("required_field", "groupId", "languageId");
			
			val.add("username", RegexRule.getInstance(username_regex), "bad_username");
			
			val.add("username", MethodRule.getInstance(this, "checkUsernameAdd"), "username_already_exists");
			
			val.add("email", EmailRule.getInstance(), "bad_email");
			
			val.add("password", EqualRule.getInstance("password", "passconf"), "pass_no_match");
			
		} else if (method != null && method.equals("edit") && isPost()) {

			val.requiredFields("required_field", "username", "email", "groupId");
			
			val.requiredLists("required_field", "groupId");
			
			val.add("username", RegexRule.getInstance(username_regex), "bad_username");
			
			val.add("username", MethodRule.getInstance(this, "checkUsernameEdit"), "username_already_exists");
			
			val.add("email", EmailRule.getInstance(), "bad_email");
		}
		
	}
	
	boolean checkUsernameAdd(String username) {
		
		return userDAO.findByUsername(username) == null;
	}
	
	boolean checkUsernameEdit(String username) {
		
		User currentUser = getSessionObj();
		
		// first check if he is actually changing his username...
		
		if (!currentUser.getUsername().equals(username)) {
			
			return userDAO.findByUsername(username) == null;
		}
		
		return true;
	}
	
	public String check(String username) {
		
		if (!isPost()) return ERROR;
		
		User sessionUser = getSessionObj();
		
		String sessionUsername = null;
		
		if (sessionUser != null) sessionUsername = sessionUser.getUsername();
		
		if (isEmpty(username)) return ERROR;
		
		User u = userDAO.findByUsername(username);
		
		if (u == null) return SUCCESS; // username does not exist
		
		if (sessionUsername != null && u.getUsername().equals(sessionUsername)) return SUCCESS;
		
		return ALREADY;
	}
	
	public String add() {
		
		if (!isPost()) {
			
			// we only want to allow post to add an user...
			
			return ERROR;
			
		} else {
			
			User u = input.getObject(User.class);
			
			userDAO.insert(u);

			setSessionObj(u);
			
			setSessionGroup(u.getGroup());
			
			setSessionLocale(u.getLocale());
			
			// send email asynchronous to user with password
			
			Letter welcome = new TextLetter("welcome.txt");
			welcome.setAttribute("username", u.getUsername());
			welcome.setAttribute("password", u.getPassword());
			
			try {
			
				String subject = welcome.getSubject(getSessionLocale());
				String body = welcome.getText(getSessionLocale());
			
				SimpleEmail.sendLater(u.getUsername(), u.getEmail(), subject, body);
				
			} catch(Exception e) {
				
				System.err.println("Error sending email to: " + u.getEmail());
				
				e.printStackTrace();
			}
			
			addMessage("registration_ok");
			
			return CREATED;
		}
	}
	
	public String edit() {
		
		if (!isPost()) {
			
			// display user for update...
			
			User u = getSessionObj();
			
			output.setValue("user", u);
			
			return SHOW;
			
		} else {
			
			User sessionUser = getSessionObj();
			
			int id = sessionUser.getId();
			
			User newUser = userDAO.load(id); // load a fresh new one
			
			input.inject(newUser); // BlacklistParamFilter should be used here to project against malicious param injection (see AppManager)
			
			userDAO.update(newUser);
			
			replaceSessionObj(newUser);
			
			setSessionGroup(newUser.getGroup());
			
			setSessionLocale(newUser.getLocale());
			
			addMessage("edit_ok");
			
			return UPDATED;
		}
	}
}