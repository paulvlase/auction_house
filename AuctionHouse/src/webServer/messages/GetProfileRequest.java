package webServer.messages;

public class GetProfileRequest {
	private String username;

	public GetProfileRequest(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
