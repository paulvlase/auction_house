package data;

import data.UserProfile.UserRole;

/**
 * Login credentials class.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class LoginCred {
	private String username;
	private String password;
	private UserRole role;
	
	public LoginCred(String username, String password, UserRole role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}
}
