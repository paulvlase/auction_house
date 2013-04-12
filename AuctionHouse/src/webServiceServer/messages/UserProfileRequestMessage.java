package webServiceServer.messages;

public class UserProfileRequestMessage {
	private String username;

	public UserProfileRequestMessage(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
