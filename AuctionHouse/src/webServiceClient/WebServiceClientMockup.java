package webServiceClient;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import data.LoginCred;
import data.Pair;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.UserEntry.Offer;
import data.UserProfile.UserRole;
import interfaces.MediatorWeb;
import interfaces.WebServiceClient;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientMockup extends Thread implements WebServiceClient {
	private MediatorWeb						med;
	private WebServiceClientTask			task;

	private Hashtable<String, UserProfile>	users;

	public WebServiceClientMockup(MediatorWeb med) {
		this.med = med;

		med.registerWebServiceClient(this);

		users = new Hashtable<String, UserProfile>();

		users.put("pvlase", new UserProfile("pvlase", "Paul", "Vlase",
				UserRole.BUYER, "parola"));
		users.put("unix140", new UserProfile("unix140", "Ghennadi",
				"Procopciuc", UserRole.SELLER, "marmota"));
	}

	public UserProfile logIn(LoginCred cred) {
		System.out.println("[WebServiceClientMockup:logIn()] Begin");

		UserProfile profile = getUserProfile(cred.getUsername());
		if (profile == null) {
			return null;
		}

		if (!profile.getPassword().equals(cred.getPassword())) {
			return null;
		}

		profile.setRole(cred.getRole());

		if (profile != null) {
			task = new WebServiceClientTask(med);
			task.execute();
		}

		System.out.println("[WebServiceClientMockup:logIn()] End");
		return profile;
	}

	public void logOut() {
		System.out.println("[WebServiceClientMockup:logOut()] Bye bye");

		try {
			task.cancel(false);
			task = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UserProfile getUserProfile(String username) {
		return users.get(username);
	}

	public boolean setUserProfile(UserProfile profile) {
		users.put(profile.getUsername(), profile);
		med.changeProfileNotify(profile);
		return true;
	}
	
	public boolean registerUser(UserProfile profile) {
		users.put(profile.getUsername(), profile);
		return true;
	}
	
	public boolean verifyUsername(String username) {
		if (users.get(username) != null)
			return true;
		return false;
	}

	/* Common */
	public synchronized boolean launchOffer(Service service) {
		return task.launchOffer(service);
	}

	public synchronized boolean launchOffers(ArrayList<Service> services) {
		return task.launchOffers(services);
	}

	public synchronized boolean dropOffer(Service service) {
		return task.dropOffer(service);
	}

	public synchronized boolean dropOffers(ArrayList<Service> services) {
		return task.dropOffers(services);
	}

	/* Buyer */
	@Override
	public boolean acceptOffer(Pair<Service, Integer> pair) {
		return task.acceptOffer(pair);
	}

	@Override
	public boolean refuseOffer(Pair<Service, Integer> pair) {
		return task.refuseOffer(pair);
	}

	/* Seller */
	@Override
	public boolean makeOffer(Pair<Service, Integer> pair) {
		// TODO Auto-generated method stub
		return task.makeOffer(pair);
	}

	@Override
	public boolean dropAuction(Pair<Service, Integer> pair) {
		return task.dropAuction(pair);
	}
}
