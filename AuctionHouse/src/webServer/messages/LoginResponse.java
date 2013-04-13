package webServer.messages;

import data.UserProfile;

public class LoginResponse {
	private UserProfile profile;
	
	private LoginResponse(UserProfile profile) {
		this.profile = profile;
	}
	
	public UserProfile getUserProfile() {
		return profile;
	}
}
