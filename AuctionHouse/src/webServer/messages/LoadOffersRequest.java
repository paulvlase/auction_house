package webServer.messages;

import java.io.Serializable;

import data.LoginCred;

public class LoadOffersRequest implements Serializable{
	private static final long	serialVersionUID	= 1L;
	private LoginCred cred;

	public LoadOffersRequest(LoginCred cred) {
		this.cred = cred;
	}

	public LoginCred getCred() {
		return cred;
	}

	public void setCred(LoginCred cred) {
		this.cred = cred;
	}
}
