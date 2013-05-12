package webServer.messages;

import java.io.Serializable;

import data.LoginCred;

public class DropOfferRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private LoginCred			cred;
	private String				serviceName;

	public DropOfferRequest(LoginCred cred, String serviceName) {
		this.cred = cred;
		this.serviceName = serviceName;
	}

	public LoginCred getLoginCred() {
		return cred;
	}

	public String getServiceName() {
		return serviceName;
	}
}
