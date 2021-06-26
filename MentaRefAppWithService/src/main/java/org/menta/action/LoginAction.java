package org.menta.action;

import org.menta.exception.LoginException;
import org.menta.exception.LoginException.Type;
import org.menta.model.User;
import org.menta.service.UserService;
import org.mentawai.action.BaseLoginAction;
import org.mentawai.validation.Validatable;
import org.mentawai.validation.Validator;


public class LoginAction extends BaseLoginAction implements Validatable {
   
   private final UserService userService;
   
   public LoginAction(UserService userService) {
	   this.userService = userService;
   }

   public String execute(String username, String password) {
	   
	   try {
		   
		   User userFound = userService.login(username, password);
			
		   setSessionObj(userFound);
		   setSessionGroups(userFound.getGroup());
		   setSessionLocale(userFound.getLocale());
		      	
		   return SUCCESS;
		      
		} catch (LoginException e) {
			Type type = e.getType();
			if (type == Type.WRONG_PASSWORD) {
				addError("password", type.toString());	
			} else {
				addError("username", type.toString());
			}
			return ERROR;
		}
   }

	@Override
	public void prepareValidator(Validator validator, String innerAction) {
		validator.requiredFields("required_field", "username", "password");
	}
   
}