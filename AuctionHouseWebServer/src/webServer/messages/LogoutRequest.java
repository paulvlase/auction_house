package webServer.messages;

import java.io.Serializable;

import data.LoginCred;

public class LogoutRequest  implements Serializable {
	private static final long	serialVersionUID	= 1L;
	LoginCred cred;
	
	public LogoutRequest(LoginCred cred) {
		this.cred = cred;
	}
	
	public LoginCred getCred() {
		return cred;
	}
}
