package org.menta.action;

import static org.mentawai.core.Action.ERROR;
import static org.mentawai.core.Action.SUCCESS;
import junit.framework.Assert;

import org.junit.Test;
import org.menta.AbstractBaseTest;
import org.menta.exception.LoginException.Type;
import org.menta.model.Group;
import org.menta.model.Language;
import org.menta.model.User;
import org.mentawai.core.MapInput;

public class LoginActionTest extends AbstractBaseTest {
	
	@Override
	public void before() throws Exception {
		
		super.before();
		
		User u = new User();
		u.setUsername("testUser");
		u.setPassword("abc123");
		u.setEmail("testUser@test.com");
		u.setGroupId(Group.ADMIN.getCode());
		u.setLanguageId(Language.ENGLISH.getCode());
		
		userDAO.insertOrUpdate(u);
	}
	
	@Test
	public void login() throws Exception {
		
		LoginAction login = getAction(LoginAction.class);
		
		MapInput input = (MapInput) login.getInput();

		String result, errorId;
		
		// test wrong password...
		result = login.execute("testUser", "wrong_pass");
		
		errorId = login.getError("password");
		
		Assert.assertEquals(ERROR, result);
		Assert.assertEquals(Type.WRONG_PASSWORD.toString(), errorId);
		
		// test success...
		result = login.execute("testUser", "abc123");
		
		Assert.assertEquals(SUCCESS, result);
	}
	
}