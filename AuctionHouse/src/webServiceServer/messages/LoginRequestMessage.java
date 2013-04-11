package webServiceServer.messages;

import java.io.Serializable;

import data.LoginCred;

public class LoginRequestMessage implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private LoginCred			cred;

	public LoginRequestMessage(LoginCred cred) {
		this.cred = cred;
	}

	public LoginCred getCred() {
		return cred;
	}
}
