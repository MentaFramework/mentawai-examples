package org.menta.action;

import static org.mentawai.core.Action.*;
import junit.framework.Assert;

import org.junit.Test;
import org.menta.AbstractBaseTest;
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
		
		// test only post...
		
		result = login.execute();
		
		Assert.assertEquals(ERROR, result);
		
		input.setProperty("method", "post");
		
		// testing missing username or password...
		
		result = login.execute();
		
		errorId = login.getError("username");
		
		Assert.assertEquals(ERROR, result);
		Assert.assertEquals("no_username", errorId);
		
		// test wrong password...
		
		input.setValue("username", "testUser");
		input.setValue("password", "wrong_pass");
		
		result = login.execute();
		
		errorId = login.getError("password");
		
		Assert.assertEquals(ERROR, result);
		Assert.assertEquals("bad_password", errorId);
		
		// test success...
		
		input.setValue("password", "abc123");
		
		result = login.execute();
		
		Assert.assertEquals(SUCCESS, result);
	}
	
}