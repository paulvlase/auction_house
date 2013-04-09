package states;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class RemoveOfferState implements State {
	private Service service;

	public RemoveOfferState() {
		
	}
	
	@Override
	public void executeNet(MediatorNetwork mednet) {
		if (service.getUsers() == null) {
			return;
		}
		
		for (UserEntry userEntry: service.getUsers()) {
			if (userEntry.getOffer() == Offer.TRANSFER_IN_PROGRESS ||
					userEntry.getOffer() == Offer.OFFER_ACCEPTED) {
				/* Cauta in lista de thread-uri ca sa stergi */
				userEntry.setOffer(Offer.OFFER_DROP);
			}
		}
	}
	
	public void executeWeb(MediatorWeb medweb) {
		System.out.println("[RemoveOfferState:executeWeb()] begin");
		medweb.removeOffer(service.getName());
		System.out.println("[RemoveOfferState:executeWeb()] end");
	}
	
	public void setState(Service service) {
		this.service = service;
	}

	public String getName() {
		return "Inactive";
	}
}