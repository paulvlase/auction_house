package webServer.messages;

import data.UserProfile;

public class SetProfileRequest {
	private UserProfile profile;
	
	public SetProfileRequest(UserProfile profile) {
		this.profile = profile;
	}
	
	public UserProfile getUserProfile() {
		return profile;
	}
}
