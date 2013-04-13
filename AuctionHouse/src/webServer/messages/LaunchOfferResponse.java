package webServer.messages;

import data.Service;

public class LaunchOfferResponse {
	private Service service;

	public LaunchOfferResponse(Service service) {
		this.service = service;
	}
	
	public Service getService() {
		return service;
	}
}
