package org.menta.action;

import org.menta.model.User;
import org.menta.service.UserService;
import org.mentawai.core.BaseAction;
import org.mentawai.rule.EmailRule;
import org.mentawai.rule.EqualRule;
import org.mentawai.rule.MethodRule;
import org.mentawai.rule.RegexRule;
import org.mentawai.validation.Validatable;
import org.mentawai.validation.Validator;

public class UserAction extends BaseAction implements Validatable {
	
	private final UserService userService;
	
	public UserAction(UserService userService) {
		this.userService = userService;
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

			// declare cripted fields to validate and convert them
			val.criptedFields("decript_error", "id", "groupId", "sexo", "languageId");
			
			val.requiredFields("required_field", "username", "email", "groupId", "languageId");
			
			val.requiredLists("required_field", "groupId");
			
			val.add("username", RegexRule.getInstance(username_regex), "bad_username");
			
			val.add("username", MethodRule.getInstance(this, "checkUsernameEdit"), "username_already_exists");
			
			val.add("email", EmailRule.getInstance(), "bad_email");
		}
		
	}
	
	boolean checkUsernameAdd(String username) {
		
		return userService.findByUsername(username) == null;
	}
	
	boolean checkUsernameEdit(String username) {
		
		User currentUser = getSessionObj();
		
		// first check if he is actually changing his username...
		
		if (!currentUser.getUsername().equals(username)) {
			
			return userService.findByUsername(username) == null;
		}
		
		return true;
	}
	
	public String check(String username) {
		
		if (!isPost()) return ERROR;
		
		User sessionUser = getSessionObj();
		
		String sessionUsername = null;
		
		if (sessionUser != null) sessionUsername = sessionUser.getUsername();
		
		if (isEmpty(username)) return ERROR;
		
		User u = userService.findByUsername(username);
		
		if (u == null) return SUCCESS; // username does not exist
		
		if (sessionUsername != null && u.getUsername().equals(sessionUsername)) return SUCCESS;
		
		return ALREADY;
	}
	
	public String add(User u) {
		
		if (!isPost()) {
			
			// we only want to allow post to add an user...
			
			return ERROR;
			
		} else {
			
			userService.save(u);

			setSessionObj(u);
			setSessionGroup(u.getGroup());
			setSessionLocale(u.getLocale());
			
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
			
			int id = input.getInt("id");
			
			User newUser = userService.load(id);
			
			input.inject(newUser);
			
			userService.save(newUser);
			
			replaceSessionObj(newUser);
			
			setSessionGroup(newUser.getGroup());
			
			setSessionLocale(newUser.getLocale());
			
			addMessage("edit_ok");
			
			return UPDATED;
		}
	}
}