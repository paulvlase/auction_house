package webServer.messages;

import data.UserProfile;

public class GetProfileResponse {
	private UserProfile profile;

	public GetProfileResponse(UserProfile profile) {
		this.profile = profile;
	}

	public UserProfile getProfile() {
		return profile;
	}
}
