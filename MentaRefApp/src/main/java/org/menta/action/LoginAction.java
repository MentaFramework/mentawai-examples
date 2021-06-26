package org.menta.action;

import org.menta.dao.UserDAO;
import org.menta.model.User;
import org.mentawai.action.BaseLoginAction;


public class LoginAction extends BaseLoginAction {
   
   private final UserDAO userDAO;
   
   public LoginAction(UserDAO userDAO) {
	   this.userDAO = userDAO;
   }

   @Override
   public String execute() {
      
      if (!isPost()) return ERROR;
      
      String username = input.getString("username");
      
      String password = input.getString("password");
      
      boolean isOk = true;
      
      if (username == null || username.trim().equals("")) {
         
         addError("username", "no_username");
         
         isOk = false;
      }
      
      if (password == null || password.trim().equals("")) {
         
         addError("password", "no_password");
         
         isOk = false;
      }
      
      if (!isOk) return ERROR;
      
      User u = userDAO.findByUsername(username);
      
      if (u == null) {
         
         addError("username", "unknown_user");
         
         return ERROR;
      }
      
      if (u.getPassword().equalsIgnoreCase(password)) {
         
         setSessionObj(u);
         
         setSessionGroups(u.getGroup());
         
         setSessionLocale(u.getLocale());
         	
         return SUCCESS;
         
      } else {
         
         addError("password", "bad_password");
         
         return ERROR;
      }
   }
   
}