package webServer.messages;

import java.io.Serializable;

import data.Service;

public class LaunchOfferResponse implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private Service				service;

	public LaunchOfferResponse(Service service) {
		this.service = service;
	}

	public Service getService() {
		return service;
	}
}
