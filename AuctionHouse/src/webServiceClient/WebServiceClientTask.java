package webServiceClient;

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
public class WebServiceClientTask extends SwingWorker<Command, Command> {
	private boolean							running;

	private Random							random;

	private MediatorWeb						med;

	public WebServiceClientTask(MediatorWeb med) {
		this.med = med;

		random = new Random();
	}
	
	@Override
	protected Command doInBackground() throws Exception {		
		int timeLimit = 2500;
		System.out.println("[WebserviceClientTask:doInBackground()] Here");
		try {
			while(true) {
				int sleepTime = 1000 + random.nextInt(timeLimit);

				Thread.sleep(sleepTime);

				if (med.getUserProfile().getRole() == UserRole.BUYER)
					publishBuyerEvents();
				else
					publishSellerEvents();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	protected void process(List<Command> events) {
		for (Command event:  events) {
			System.out.println("[WebserviceClientTask:process()] Here");
			event.execute();
		}
	}
	
	@Override
	protected void done() {
		System.out.println(Thread.currentThread());
		if (isCancelled()) {
			System.out.println("Cancelled !");
		} else {
			System.out.println("Done !");
		}
	}
	
	public void publishBuyerEvents() {
		System.out.println("[webServiceClientThread:generateBuyerEvents()] Begin");
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

				if (n < 200) {
					publish(new NoOfferEvent(med, service));
				} else if (n < 400) {
					publish(new OfferMadeEvent(med, service));
				} else if (n < 600) {
					publish(new OfferRefusedEvent(med, service));
				}
			}
		}
		
		System.out.println("[webServiceClientThread:generateBuyerEvents()] End");
	}

	public void publishSellerEvents() {
		System.out.println("[webServiceClientThread:generateSellerEvents()] Begin");
		for (Map.Entry<String, Service> offer : med.getOffers().entrySet()) {
			Service service = offer.getValue();
			ArrayList<UserEntry> users = service.getUsers();

			Integer countLimit = users.size() / 2;

			Integer count = random.nextInt(countLimit);
			if (count < 2) {
				count = 2;
			}

			for (Integer i = 0; i < count; i++) {
				Integer eventLimit = 10000;
				Integer n = random.nextInt(eventLimit);

				if (n < 200) {
					publish(new NoOfferEvent(med, service));
				} else if (n < 400) {
					publish(new OfferMadeEvent(med, service));
				} else if (n < 600) {
					publish(new OfferAcceptedEvent(med, service));
				} else if (n < 800) {
					publish(new OfferRefusedEvent(med, service));
				} else if (n < 1000) {
					publish(new OfferExceededEvent(med, service));
				} else if (n < 1200) {
					 publish(new TransferFailedEvent(med, service));
				}
			}
		}
		System.out.println("[webServiceClientThread:generateSellerEvents()] End");
	}

	/* Common */
	public boolean launchOffer(Service service) {
		service.setStatus(Status.ACTIVE);
		// service.setUsers(new ArrayList<UserEntry>());
		med.putOffer(service);

		med.changeServiceNotify(service);
		System.out.println("[WebServiceClientMockup:addOffer] "
				+ service.getName());

		return true;
	}

	public boolean launchOffers(ArrayList<Service> services) {
		for (Service service : services) {
			service.setStatus(Status.ACTIVE);
			med.putOffer(service);
			
			System.out.println("[WebServiceClientMockup:addOffers()] "
					+ service.getName());
		}
		med.changeServicesNotify(services);

		return true;
	}

	public boolean dropOffer(Service service) {
		service.setStatus(Status.INACTIVE);
		service.setUsers(null);

		med.removeOffer(service.getName());
		System.out.println("[WebServiceClientMockup:dropOffer] "
				+ service.getName());

		med.changeServiceNotify(service);
		return true;
	}

	public boolean dropOffers(ArrayList<Service> services) {
		for (Service service : services) {
			service.setStatus(Status.INACTIVE);
			service.setStatus(null);
			med.removeOffer(service.getName());
			System.out.println("[WebServiceClientMockup:dropOffers] "
					+ service.getName());
		}

		return true;
	}

	public boolean acceptOffer(Pair<Service, Integer> pair) {
		Service service = pair.getKey();
		ArrayList<UserEntry> users = service.getUsers();

		for (UserEntry user : users) {
			user.setOffer(Offer.OFFER_REFUSED);
		}

		UserEntry user = users.get(pair.getValue());
		user.setOffer(Offer.OFFER_ACCEPTED);

		/* TODO: communicate with server */
		users.clear();
		users.add(user);

		return true;
	}

	public synchronized boolean refuseOffer(Pair<Service, Integer> pair) {
		Service service = pair.getKey();
		int userIndex = pair.getValue();
		ArrayList<UserEntry> users = service.getUsers();

		System.out.println("[WebServiceClient:refuseOffer()] Aici");
		if (users != null) {
			/* TODO Will be implemented */
			UserEntry user = users.get(userIndex);

			user.setOffer(Offer.OFFER_REFUSED);
			users.remove(userIndex);

			if (users.size() == 0) {
				service.setUsers(null);
			}
			med.changeServiceNotify(service);
		}

		return true;
	}

	/* Seller */
	public boolean makeOffer(Pair<Service, Integer> pair) {
		Service service = pair.getKey();
		int userIndex = pair.getValue();
		ArrayList<UserEntry> users = service.getUsers();

		System.out.println("[WebServiceClient:dropAuction()] Aici");
		if (users != null) {
			UserEntry user = users.get(userIndex);
			user.setOffer(Offer.OFFER_MADE);
			med.putOffer(service);

			med.changeServiceNotify(service);
		}

		return true;
	}

	public boolean dropAuction(Pair<Service, Integer> pair) {
		Service service = pair.getKey();
		int userIndex = pair.getValue();
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			UserEntry user = users.get(userIndex);

			user.setOffer(Offer.OFFER_REFUSED);
			users.remove(userIndex);

			if (users.size() == 0) {
				service.setUsers(null);
			}

			med.putOffer(service);

			System.out.println("[WebServiceClient:dropAuction()] Aici: "
					+ users);

			med.changeServiceNotify(service);
		}

		return true;
	}
}
