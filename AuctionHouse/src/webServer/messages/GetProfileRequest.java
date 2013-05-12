package webServer.messages;

import java.io.Serializable;

import data.LoginCred;

public class GetProfileRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private LoginCred			fromCred;
	private String				username;

	public GetProfileRequest(LoginCred fromCred, String username) {
		this.fromCred = fromCred;
		this.username = username;
	}

	public LoginCred getFromCred() {
		return fromCred;
	}

	public String getUsername() {
		return username;
	}
}
