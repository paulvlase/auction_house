package webServiceServer.messages;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				msg;

	public ErrorMessage(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
	public String toString() {
		return getMsg();
	}
}
