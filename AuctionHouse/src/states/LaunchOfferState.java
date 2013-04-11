package states;

import data.Service;
import data.Service.Status;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

public class LaunchOfferState implements State {
	private Service service;

	public LaunchOfferState() {
		
	}
	
	public void executeNet(MediatorNetwork mednet) {
		System.out.println("LaunchOfferState(): executeNet");
	}
	
	public void executeWeb(MediatorWeb medweb) {
		System.out.println("LaunchOfferState(): executeWeb");
		service.setStatus(Status.ACTIVE);
		
		service.setInactiveState();
		
		medweb.changeServiceNotify(service);
	}

	public void setState(Service service) {
		this.service = service;
	}
	
	public String getName() {
		return "Launch Offer xxx";
	}
}
