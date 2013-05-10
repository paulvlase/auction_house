package webServer.messages;

import java.io.Serializable;

import data.LoginCred;
import data.Service;
import data.UserProfile.UserRole;

public class LaunchOfferRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	public String username;
	public Service service;
	public LoginCred cred;
	
	public LaunchOfferRequest(String username, LoginCred cred, Service service) {
		this.username = username;
		this.cred = cred;
		this.service = service;
	}
	
	public String getUsername() {
		return username;
	}
	
	public LoginCred getCred() {
		return cred;
	}
	
	public Service getService() {
		return service;
	}
}
