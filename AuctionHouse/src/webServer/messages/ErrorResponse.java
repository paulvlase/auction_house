package webServer.messages;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private String				msg;

	public ErrorResponse(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
