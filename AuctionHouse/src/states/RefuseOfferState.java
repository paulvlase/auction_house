package states;

import java.util.ArrayList;

import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class RefuseOfferState implements State {
	private Service service;
	private Integer userIndex;

	public RefuseOfferState() {
		
	}
	
	@Override
	public void executeNet(MediatorNetwork mednet) {
		ArrayList<UserEntry> users = service.getUsers();

		System.out.println("[RefuseOfferState:executeNet()]");
		if (users != null) {
			/* TODO Will be implemented */
			UserEntry user = users.get(userIndex);

			user.setOffer(Offer.OFFER_REFUSED);
			users.remove(userIndex);

			if (users.size() == 0) {
				service.setUsers(null);
			}
			mednet.changeServiceNotify(service);
		}
	}
	
	public void executeWeb(MediatorWeb medweb) {
		
	}
	
	public void setState(Service service, Integer userIndex) {
		this.service = service;
		this.userIndex = userIndex;
	}

	public String getName() {
		return "Inactive";
	}
}