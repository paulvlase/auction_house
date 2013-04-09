package network;

import interfaces.Command;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;

import java.util.Date;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.SwingWorker;

import network.NetworkJoinThread;

import data.LoginCred;
import data.Pair;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.Service.Status;
import data.UserEntry.Offer;
import data.UserProfile.UserRole;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkEvents extends SwingWorker<Command, Command> {
	private Random			random;

	private MediatorNetwork	med;

	public NetworkEvents(MediatorNetwork med) {
		this.med = med;

		random = new Random();
	}

	@Override
	protected Command doInBackground() throws Exception {
		System.out.println("[NetworkEvents:doInBackground()] Begin");

		int timeLimit = 2500;

		try {
			while (true) {
				int sleepTime = 1000 + random.nextInt(timeLimit);

				Thread.sleep(sleepTime);
				
				if (med.getUserProfile().getRole() == UserRole.BUYER) {
					publishBuyerEvents();
				} else {
					publishSellerEvents();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected void process(List<Command> events) {
		for (Command event : events) {
			event.execute();
		}
	}

	@Override
	protected void done() {
		System.out.println(Thread.currentThread());
		if (isCancelled()) {
			System.out.println("[NetworkEvents:done()] Cancelled !");
		} else {
			System.out.println("[NetworkEvents:done()] Done !");
		}
	}

	public void publishBuyerEvents() {
		System.out.println("[NetworkEvents:generateBuyerEvents()] Begin");

		for (Map.Entry<String, Service> offer : med.getOffers().entrySet()) {
			Service service = offer.getValue();
			ArrayList<UserEntry> users = service.getUsers();

			Integer countLimit;
			if (users == null) {
				countLimit = 2;
			} else {
				countLimit = users.size() / 2;
				if (countLimit < 2) {
					countLimit = 2;
				}
			}

			Integer count = random.nextInt(countLimit);

			for (Integer i = 0; i < count; i++) {
				Integer eventLimit = 1000;
				Integer n = random.nextInt(eventLimit);

				System.out.println("n = " + n);
				if (n < 400) {
					publish(new OfferMadeEvent(med, service));
				} else if (n < 600) {
					publish(new OfferRefusedEvent(med, service));
				}
			}
		}

		System.out.println("[NetworkEvents:generateBuyerEvents()] End");
	}

	public void publishSellerEvents() {
		System.out.println("[NetworkEvents:generateSellerEvents()] Begin");
		System.out.println("[NetworkEvents:generateSellerEvents()] med.getOffers.size() = " + med.getOffers().size());

		for (Map.Entry<String, Service> offer : med.getOffers().entrySet()) {
			Service service = offer.getValue();
			ArrayList<UserEntry> users = service.getUsers();

			Integer countLimit;
			if (users == null) {
				countLimit = 2;
			} else {
				countLimit = users.size() / 2;
				if (countLimit < 2) {
					countLimit = 2;
				}
			}

			Integer count = random.nextInt(countLimit);
			
			for (Integer i = 0; i < count; i++) {
				Integer eventLimit = 1500;
				Integer n = random.nextInt(eventLimit);

				if (n < 400) {
					publish(new OfferAcceptedEvent(med, service));
				} else if (n < 600) {
					publish(new OfferRefusedEvent(med, service));
				} else if (n < 800) {
					publish(new OfferExceededEvent(med, service));
				} else if (n < 1000) {
					publish(new TransferFailedEvent(med, service));
				}
			}
		}

		System.out.println("[NetworkEvents:generateSellerEvents()] End");
	}

	public void publishService(Service service) {
		publish(new NetworkAdapter(service));
	}
	
	public void publishServices(ArrayList<Service> services) {
		for (Service service: services) {
			publish(new NetworkAdapter(service));
		}
	}
}
