package webServer.messages;

import java.io.Serializable;

import data.LoginCred;
import data.UserProfile;

public class SetProfileRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private LoginCred			fromCred;
	private UserProfile			profile;

	public SetProfileRequest(LoginCred fromCred, UserProfile profile) {
		this.fromCred = fromCred;
		this.profile = profile;
	}

	public LoginCred getFromCred() {
		return fromCred;
	}

	public UserProfile getUserProfile() {
		return profile;
	}
}
