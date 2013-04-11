package webServiceServer.messages;

import java.io.Serializable;

public class LogoutRequestMessage implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				username;

	public LogoutRequestMessage(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
