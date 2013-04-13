package webServer.messages;

import java.io.Serializable;

import data.UserProfile.UserRole;

public class DropOfferRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String username;
	private UserRole role;
	private String serviceName;
	
	public DropOfferRequest(String username, UserRole role, String serviceName) {
		this.username = username;
		this.role = role;
		this.serviceName = serviceName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public UserRole getUserRole() {
		return role;
	}
	
	public String getServiceName() {
		return serviceName;
	}
}
