package data;

/**
 * User profile class.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 * @author Ghennadi Procopciuc
 */
public class UserProfile {
	private String username;
	private String name;
	private UserRole role;
	private String password;
	
	// Add Image here, maybe later

	enum UserRole {
		BUYER,
		SELLER
	}
	
	public UserProfile(String username, String name, UserRole role, String password) {
		this.username = username;
		this.name = name;
		this.role = role;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
