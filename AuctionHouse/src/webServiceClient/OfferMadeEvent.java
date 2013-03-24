package webServiceClient;

import java.util.ArrayList;
import java.util.Random;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;
import interfaces.Command;
import interfaces.MediatorWeb;

public class OfferMadeEvent implements Command {
	private MediatorWeb		med;
	private Service			service;
	private static Random	random	= new Random();

	public OfferMadeEvent(MediatorWeb med, Service service) {
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

		if (users.get(userIndex).getOffer() == Offer.OFFER_MADE
				|| users.get(userIndex).getOffer() == Offer.OFFER_REFUSED)
			return;

		UserEntry user = users.get(userIndex);
		Double price = user.getPrice();
		if (price > 1) {
			user.setPrice(price - 1);
			user.setOffer(Offer.OFFER_MADE);
		}

		med.putOffer(service);
		med.changeServiceNotify(service);
	}
}
