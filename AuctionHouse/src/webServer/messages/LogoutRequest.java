package webServer.messages;

import data.LoginCred;

public class LogoutRequest {
	LoginCred cred;
	
	public LogoutRequest(LoginCred cred) {
		this.cred = cred;
	}
	
	public LoginCred getCred() {
		return cred;
	}
}
