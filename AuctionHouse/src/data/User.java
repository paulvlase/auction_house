package data;

import config.GlobalConfig.UserType;

public class User {
	private String username;
	private String password;
	private UserType type;
	
	public User(String username, String password, UserType type) {
		this.username = username;
		this.password = password;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public UserType getType() {
		return type;
	}
}
