package webServer.messages;

import java.io.Serializable;

public class GetProfileRequest implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String username;

	public GetProfileRequest(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
