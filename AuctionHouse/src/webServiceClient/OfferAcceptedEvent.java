package webServiceClient;

import interfaces.Command;
import interfaces.MediatorWeb;

import java.util.ArrayList;
import java.util.Random;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class OfferAcceptedEvent implements Command {
	private MediatorWeb med;
	private Service service;
	private static Random random = new Random();

	public OfferAcceptedEvent(MediatorWeb med, Service service) {
		this.med = med;
		this.service = service;
	}
	
	@Override
	public void execute() {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			Integer userIndex = random.nextInt(users.size());

			UserEntry user = users.get(userIndex);

			/* TODO */
			// if (user.getOffer() != Offer.OFFER_MADE) {
			// return;
			// }

			user.setOffer(Offer.OFFER_ACCEPTED);

			med.putOffer(service);
			med.changeServiceNotify(service);
		}
	}
}
