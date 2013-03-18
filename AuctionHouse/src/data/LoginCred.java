package data;

import config.GlobalConfig.UserType;

/**
 * Login credentials class.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class LoginCred {
	private String username;
	private String password;
	private UserType type;
	
	public LoginCred(String username, String password, UserType type) {
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
