package webServiceServer.messages;

import java.io.Serializable;

import data.UserProfile;

public class LoginResponseMessage implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private UserProfile			profile;

	public LoginResponseMessage(UserProfile profile) {
		this.profile = profile;
	}

	public UserProfile getProfile() {
		return profile;
	}
}
