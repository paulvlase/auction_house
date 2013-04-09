package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import data.Service;
import data.Service.Status;

public class DropOfferState implements State {
	private Service service;

	public DropOfferState() {
		
	}
	
	public void executeNet(MediatorNetwork mednet) {
		service.setStatus(Status.INACTIVE);
		service.setUsers(null);

		mednet.removeOffer(service.getName());
		System.out.println("[DropOfferState:executeNet()] "
				+ service.getName());

		service.setInactiveState();
		mednet.stopTransfer(service);
		mednet.changeServiceNotify(service);
	}
	
	public void executeWeb(MediatorWeb medweb) {
		
	}
	
	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "Drop Offer";
	}
}
