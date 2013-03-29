package network;

import interfaces.Command;
import interfaces.MediatorNetwork;

import java.util.ArrayList;
import java.util.Random;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;

public class OfferAcceptedEvent implements Command {
	private MediatorNetwork	med;
	private Service			service;
	private static Random	random	= new Random();

	public OfferAcceptedEvent(MediatorNetwork med, Service service) {
		this.med = med;
		this.service = service;
	}

	@Override
	public void execute() {
		ArrayList<UserEntry> users = service.getUsers();

		if (users == null || users.size() == 0) {
			return;
		}

		Integer userIndex = random.nextInt(users.size());
		UserEntry user = users.get(userIndex);


		user.setOffer(Offer.OFFER_ACCEPTED);
		/* TODO stabilesc comunicatia cu cumparatorul. */

		users.clear();
		
		user.setOffer(Offer.TRANSFER_STARTED);
		users.add(user);

		med.startTransfer(service);
		med.changeServiceNotify(service);
	}
}
