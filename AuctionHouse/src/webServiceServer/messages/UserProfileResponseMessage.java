package webServiceServer.messages;

import data.UserProfile;

public class UserProfileResponseMessage {
	private UserProfile profile;
	
	public UserProfileResponseMessage(UserProfile profile) {
		this.profile = profile;
	}
	
	public UserProfile getUserProfile() {
		return profile;
	}
}
