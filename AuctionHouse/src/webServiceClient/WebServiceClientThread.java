package webServiceClient;

import interfaces.MediatorWeb;

import java.util.Date;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

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
public class WebServiceClientThread extends Thread {
	private boolean							running;

	private Random							random;
	private Date							date;

	private MediatorWeb						med;

	private Hashtable<String, Service>		offers;
	private Hashtable<String, UserProfile>	users;

	public WebServiceClientThread(MediatorWeb med) {
		this.med = med;

		random = new Random();
		date = new Date();

		users = new Hashtable<String, UserProfile>();
		offers = new Hashtable<String, Service>();

		users.put("pvlase", new UserProfile("pvlase", "Paul", "Vlase",
				UserRole.BUYER, "parola"));
		users.put("unix140", new UserProfile("unix140", "Ghennadi",
				"Procopciuc", UserRole.SELLER, "marmota"));
	}

	public void run() {
		int timeLimit = 2500;
		running = true;

		try {
			while (isRunning()) {
				int sleepTime = 1000 + random.nextInt(timeLimit);

				Thread.sleep(sleepTime);

				if (med.getUserProfile().getRole() == UserRole.BUYER)
					generateBuyerEvents();
				else
					generateSellerEvents();

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void generateBuyerEvents() {
		for (Map.Entry<String, Service> offer : offers.entrySet()) {
			Service service = offer.getValue();

			Integer countLimit = users.size() / 2;
			if (countLimit < 2) {
				countLimit = 2;
			}

			Integer count = random.nextInt(countLimit);

			for (Integer i = 0; i < count; i++) {
				Integer eventLimit = 10000;
				Integer event = random.nextInt(eventLimit);

				if (event < 50) {
					generateNoOffer(service);
				} else if (event < 100) {
					generateOfferMade(service);
				} else if (event < 250) {
					generateOfferRefused(service);
				}
			}
		}
	}

	public void generateSellerEvents() {
		for (Map.Entry<String, Service> offer : offers.entrySet()) {
			Service service = offer.getValue();

			Integer countLimit = users.size() / 2;
			if (countLimit < 2) {
				countLimit = 2;
			}

			Integer count = random.nextInt(countLimit);

			for (Integer i = 0; i < count; i++) {
				Integer eventLimit = 10000;
				Integer event = random.nextInt(eventLimit);

				if (event < 50) {
					generateNoOffer(service);
				} else if (event < 100) {
					// generateUserLogoutEvent();
				} else if (event < 150) {
					generateOfferMade(service);
				} else if (event < 250) {
					generateOfferAccepted(service);
				} else if (event < 300) {
					generateOfferRefused(service);
				} else if (event < 350) {
					generateOfferExceeded(service);
				}
			}
		}
	}

	/**
	 * Genereaza un eveniment pentru lansarea unei oferte de catre un furnizor.
	 */
	private void generateNoOffer(Service service) {
		Integer delay = 1000;
		Integer timeLimit = 1000000;
		Integer priceLimit = 100000;

		String username = getRandomString(5 + random.nextInt(10));

		Long time = date.getTime() + delay + random.nextInt(timeLimit);

		Double price = random.nextInt(priceLimit) / 100.0;

		UserEntry user = new UserEntry(username, Offer.NO_OFFER, time, price);

		service.addUserEntry(user);
		offers.put(service.getName(), service);

		med.changeServiceNotify(service);
	}

	private void generateOfferMade(Service service) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
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

			offers.put(service.getName(), service);

			med.changeServiceNotify(service);
		}
	}

	private void generateOfferAccepted(Service service) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			Integer userIndex = random.nextInt(users.size());

			UserEntry user = users.get(userIndex);

			/* TODO */
			// if (user.getOffer() != Offer.OFFER_MADE) {
			// return;
			// }

			user.setOffer(Offer.OFFER_ACCEPTED);

			offers.put(service.getName(), service);

			med.changeServiceNotify(service);
		}
	}

	private void generateOfferRefused(Service service) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			Integer userIndex = random.nextInt(users.size());

			UserEntry user = users.get(userIndex);

			/* TODO */
			if (user.getOffer() != Offer.OFFER_ACCEPTED) {
				return;
			}

			user.setOffer(Offer.OFFER_REFUSED);

			offers.put(service.getName(), service);

			med.changeServiceNotify(service);
		}
	}

	private void generateOfferExceeded(Service service) {
		ArrayList<UserEntry> users = service.getUsers();

		if (users != null) {
			Integer userIndex = random.nextInt(users.size());

			UserEntry user = users.get(userIndex);

			if (user.getOffer() != Offer.OFFER_MADE) {
				return;
			}

			user.setOffer(Offer.OFFER_EXCEDED);

			offers.put(service.getName(), service);

			med.changeServiceNotify(service);
		}
	}

	private String getRandomString(int len) {
		char[] str = new char[len];

		for (int i = 0; i < len; i++) {
			int c;
			do {
				c = 48 + random.nextInt(123 - 48);
			} while ((c >= 91 && c <= 96) || (c >= 58 && c <= 64));
			str[i] = (char) c;
		}
		System.out.println(new String(str));
		return new String(str);
	}

	public synchronized void stopThread() {
		running = false;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public UserProfile logIn(LoginCred cred) {
		UserProfile profile;
		System.out.println("[WebServiceClientThread:login()] Aici");

		profile = getUserProfile(cred.getUsername());
		if (profile == null) {
			return null;
		}

		if (!profile.getPassword().equals(cred.getPassword())) {
			return null;
		}

		profile.setRole(cred.getRole());
		System.out
				.println("[WebServiceClientThread:login()] profile.getRole():"
						+ profile.getRole());
		return profile;
	}

	public void logOut() {
		System.out.println("[WebServiceClientThread:logOut()] Bye bye");
	}

	public synchronized UserProfile getUserProfile(String username) {
		return users.get(username);
	}

	public synchronized boolean setUserProfile(UserProfile profile) {
		users.put(profile.getUsername(), profile);
		med.changeProfileNotify(profile);
		return true;
	}

	/* Common */
	public synchronized boolean launchOffer(Service service) {
		service.setStatus(Status.ACTIVE);
		// service.setUsers(new ArrayList<UserEntry>());
		offers.put(service.getName(), service);

		med.changeServiceNotify(service);
		System.out.println("[WebServiceClientMockup:addOffer] "
				+ service.getName());

		return true;
	}

	public synchronized boolean launchOffers(ArrayList<Service> services) {
		for (Service service : services) {
			service.setStatus(Status.ACTIVE);
			offers.put(service.getName(), service);
			
			System.out.println("[WebServiceClientMockup:addOffers()] "
					+ service.getName());
		}
		med.changeServicesNotify(services);

		return true;
	}

	public synchronized boolean dropOffer(Service service) {
		service.setStatus(Status.INACTIVE);
		service.setUsers(null);

		offers.remove(service.getName());
		System.out.println("[WebServiceClientMockup:dropOffer] "
				+ service.getName());

		med.changeServiceNotify(service);
		return true;
	}

	public synchronized boolean dropOffers(ArrayList<Service> services) {
		for (Service service : services) {
			service.setStatus(Status.INACTIVE);
			service.setStatus(null);
			offers.remove(service.getName());
			System.out.println("[WebServiceClientMockup:dropOffers] "
					+ service.getName());
		}

		return true;
	}

	public synchronized boolean acceptOffer(Pair<Service, Integer> pair) {
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
			offers.put(service.getName(), service);

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

			offers.put(service.getName(), service);

			System.out.println("[WebServiceClient:dropAuction()] Aici: "
					+ users);

			med.changeServiceNotify(service);
		}

		return true;
	}
}
