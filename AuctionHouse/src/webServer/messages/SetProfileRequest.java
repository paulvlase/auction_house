package webServer.messages;

import java.io.Serializable;

import data.UserProfile;

public class SetProfileRequest  implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private UserProfile profile;
	
	public SetProfileRequest(UserProfile profile) {
		this.profile = profile;
	}
	
	public UserProfile getUserProfile() {
		return profile;
	}
}
