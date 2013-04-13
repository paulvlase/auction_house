package webServer.messages;

public class ProfileRequest {
	private String username;

	public ProfileRequest(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
