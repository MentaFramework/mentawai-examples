package org.menta.model;

import java.util.Locale;

public class User {
	
	private int id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private int languageId;
	
	private int groupId;
	
	public User() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLanguageId() {
    	return languageId;
    }

	public void setLanguageId(int languageId) {
    	this.languageId = languageId;
    }
	
	public Language getLanguage() {
		return Language.fromCode(languageId);
	}
	
	public Locale getLocale() {
		Language lang = getLanguage();
		return lang != null ? lang.getLocale() : null;
	}

	public int getGroupId() {
    	return groupId;
    }
	
	public Group getGroup() {
		return Group.fromCode(groupId);
	}

	public void setGroupId(int groupId) {
    	this.groupId = groupId;
    }
}