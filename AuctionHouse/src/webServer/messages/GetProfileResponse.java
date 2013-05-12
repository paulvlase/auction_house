package webServer.messages;

import java.io.Serializable;

import data.UserProfile;

public class GetProfileResponse implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private UserProfile			profile;

	public GetProfileResponse(UserProfile profile) {
		this.profile = profile;
	}

	public UserProfile getProfile() {
		return profile;
	}
}
