package webServiceClient;

import java.util.Date;
import java.util.Random;

import data.Service;
import data.UserEntry;
import data.UserEntry.Offer;
import interfaces.Command;
import interfaces.MediatorWeb;

public class NoOfferEvent implements Command {
	private Service service;
	private MediatorWeb med;
	
	private static Date date = new Date();
	private static Random random = new Random();

	public NoOfferEvent(MediatorWeb med, Service service) {
		this.med = med;
		this.service = service;
	}

	@Override
	public void execute() {
		Integer delay = 1000;
		Integer timeLimit = 1000000;
		Integer priceLimit = 100000;

		String username = Util.getRandomString(5 + random.nextInt(10));

		Long time = date.getTime() + delay + random.nextInt(timeLimit);

		Double price = random.nextInt(priceLimit) / 100.0;

		UserEntry user = new UserEntry(username, Offer.NO_OFFER, time, price);

		service.addUserEntry(user);

		med.putOffer(service);
		med.changeServiceNotify(service);
	}
}
