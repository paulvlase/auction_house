package webServer.messages;

import java.io.Serializable;

import data.LoginCred;
import data.UserProfile;

public class LoginResponse  implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private UserProfile profile;
	private LoginCred cred;
	
	public LoginResponse(UserProfile profile, LoginCred cred) {
		this.profile = profile;
		this.cred = cred;
	}
	
	public LoginCred getCred() {
		return cred;
	}

	public void setCred(LoginCred cred) {
		this.cred = cred;
	}
	
	public UserProfile getUserProfile() {
		return profile;
	}
}
