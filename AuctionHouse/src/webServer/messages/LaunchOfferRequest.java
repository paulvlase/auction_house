package webServer.messages;

import data.Service;
import data.UserProfile.UserRole;

public class LaunchOfferRequest {
	public String username;
	public UserRole role;
	public Service service;
	
	public LaunchOfferRequest(String username, UserRole role, Service service) {
		this.username = username;
		this.role = role;
		this.service = service;
	}
	
	public String getUsername() {
		return username;
	}
	
	public UserRole getUserRole() {
		return role;
	}
	
	public Service getService() {
		return service;
	}
}
