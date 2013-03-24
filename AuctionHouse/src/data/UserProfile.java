package data;

/**
 * User profile class.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 * @author Ghennadi Procopciuc
 */
public class UserProfile {
	private String		username;
	private String		firstName;
	private String		lastName;
	private UserRole	role;
	private String		password;
	private byte[]		avatar;
	private String		location;

	public enum UserRole {
		BUYER, SELLER
	}

	public UserProfile(String username, String firstName, String lastName, UserRole role, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
