package webServer.messages;

import java.io.Serializable;

import data.LoginCred;
import data.UserProfile;

public class RegisterProfileRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private UserProfile			profile;

	public RegisterProfileRequest(UserProfile profile) {
		this.profile = profile;
	}

	public UserProfile getUserProfile() {
		return profile;
	}
}
