package webServer.messages;

import java.io.Serializable;

import data.LoginCred;

public class LoginRequest  implements Serializable {
	private static final long	serialVersionUID	= 1L;
	public LoginCred cred;
	
	public LoginRequest(LoginCred cred) {
		this.cred = cred;
	}
	
	public LoginCred getLoginCred() {
		return cred;
	}
}
