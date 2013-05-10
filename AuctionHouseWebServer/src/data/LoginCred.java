package data;

import java.io.Serializable;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import data.UserProfile.UserRole;

/**
 * Login credentials class.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class LoginCred implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private static Logger		logger				= Logger.getLogger(LoginCred.class);

	private String username;
	private String password;
	private UserRole role;
	private InetSocketAddress address;
	private Integer id;
	
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

	public InetSocketAddress getAddress() {
		return address;
	}

	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
