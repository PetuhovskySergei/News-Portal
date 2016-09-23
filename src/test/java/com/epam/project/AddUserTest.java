package test.java.test.java;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import project.java.entity.User;

public class AddUserTest {
	
	final static Logger logger = Logger.getLogger(AddTagTest.class);

	@Test
	public void addUserNameTest() {
		User user = new User();
		String s = "Will Smith";
		user.setUserName(s);
		assertEquals(s, user.getUserName());
	}
	
	@Test
	public void addLoginTest() {
		User user = new User();
		String s = "nagibator";
		user.setLogin(s);
		assertEquals(s, user.getLogin());
	}
	
	@Test
	public void addPasswordTest() {
		User user = new User();
		String s = "AB228";
		user.setPassword(s);
		assertEquals(s, user.getPassword());
	}
}
