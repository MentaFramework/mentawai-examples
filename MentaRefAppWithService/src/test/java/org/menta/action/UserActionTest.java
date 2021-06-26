package org.menta.action;

import static org.mentawai.core.Action.CREATED;
import static org.mentawai.core.Action.ERROR;
import static org.mentawai.core.Action.SHOW;
import static org.mentawai.core.Action.UPDATED;
import junit.framework.Assert;

import org.junit.Test;
import org.menta.AbstractBaseTest;
import org.menta.model.Group;
import org.menta.model.Language;
import org.menta.model.User;
import org.mentawai.core.MapInput;

public class UserActionTest extends AbstractBaseTest {
	
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
		
		u = new User();
		u.setUsername("testUser2");
		u.setPassword("abc123");
		u.setEmail("testUser1@test.com");
		u.setGroupId(Group.MASTER.getCode());
		u.setLanguageId(Language.PORTUGUESE.getCode());
		
		userDAO.insertOrUpdate(u);
	}
	
	@Test
	public void add() throws Exception {
		
		UserAction userAction = getAction(UserAction.class);
		
		MapInput input = (MapInput) userAction.getInput();
		
		input.setValue("userDAO", userDAO); // necessary due to checkUsernameAdd method

		String result;
		
		// test only post...
		User user = new User();
		result = userAction.add(user);
		
		Assert.assertEquals(ERROR, result);
		
		input.setProperty("method", "post");
		
		// testing adding same username...
		
		input.setValue("username", "testUser");
		input.setValue("password", "abc123");
		input.setValue("email", "testuser@test.com");
		input.setValue("groupId", Group.ADMIN.getCode());
		input.setValue("languageId", Language.ENGLISH.getCode());
		
		boolean check = userAction.checkUsernameAdd(input.getString("username"));
		
		Assert.assertEquals(false, check);
		
		// test good username...
		input.setValue("username", "testUser2010");
		
		check = userAction.checkUsernameAdd(input.getString("username"));
		
		Assert.assertEquals(true, check);
		
		input.inject(user);
		result = userAction.add(user);
		
		Assert.assertEquals(CREATED, result);
	}
	
	@Test
	public void edit() throws Exception {
		
		UserAction userAction = getAction(UserAction.class);
		
		MapInput input = (MapInput) userAction.getInput();
		
		input.setValue("userDAO", userDAO); // necessary due to checkUsernameEdit method
		
		String result;
		
		// test showing...
		
		input.setProperty("method", "get");
		
		result = userAction.edit();
		
		Assert.assertEquals(SHOW, result);
		
		// test updating...
		
		input.setProperty("method", "post");
		
		User sessionUser = userDAO.findByUsername("testUser");
		
		userAction.setSessionObj(sessionUser);
		
		// try to change to an username that already exists...

		input.setValue("id", sessionUser.getId());
		input.setValue("username", "testUser2");
		input.setValue("email", "testuser@test.com");
		input.setValue("groupId", Group.ADMIN.getCode());
		input.setValue("languageId", Language.ENGLISH.getCode());
		
		boolean check = userAction.checkUsernameEdit(input.getString("username"));
		
		Assert.assertEquals(false, check);
		
		// now save a good username
		
		input.setValue("username", "testUser2011");
		
		check = userAction.checkUsernameEdit(input.getString("username"));
		
		Assert.assertEquals(true, check);
		
		result = userAction.edit();
		
		Assert.assertEquals(UPDATED, result);
		
		sessionUser = (User) userAction.getSessionObj();
		
		Assert.assertEquals("testUser2011", sessionUser.getUsername());
	}
	
}