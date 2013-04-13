package webServer.messages;

import data.LoginCred;

public class LoginRequest {
	public LoginCred cred;
	
	public LoginRequest(LoginCred cred) {
		this.cred = cred;
	}
	
	public LoginCred getLoginCred() {
		return cred;
	}
}
