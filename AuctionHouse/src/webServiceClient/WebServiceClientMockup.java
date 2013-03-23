package webServiceClient;

import java.util.ArrayList;
import java.util.Hashtable;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import data.UserProfile.UserRole;
import interfaces.MediatorWeb;
import interfaces.WebServiceClient;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientMockup implements WebServiceClient {
	private MediatorWeb med;
	private WebServiceMockup webService;
	
	private Hashtable<String, UserProfile> users;
	
	public WebServiceClientMockup(MediatorWeb med) {
		this.med = med;
		
		med.registerWebServiceClient(this);

		/* TODO: This should be deleted.
		 * Used only for mockup test.
		 */
		users = new Hashtable<String, UserProfile>();
		users.put("pvlase", new UserProfile("pvlase","Paul",  "Vlase", UserRole.BUYER, "parola"));
		users.put("unix140", new UserProfile("unix140","Ghennadi",  "Procopciuc", UserRole.BUYER, "marmota"));
	}
	
	@Override
	public UserProfile logIn(LoginCred cred) {
		UserProfile profile;
		
		profile = getUserProfile(cred.getUsername());
		if (profile == null) {
			return null;
		}
		
		if (!profile.getPassword().equals(cred.getPassword())) {
			return null;
		}
		
		return profile;
	}
	
	@Override
	public void logOut() {
		System.out.println("[WebServiceClient:logOut()] Bye bye");
		
		/* TODO: This should be deleted.
		 * Used only for mockup test.
		 */
		try {
			webService.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public UserProfile getUserProfile(String username) {
		return users.get(username);
	}
	
	@Override
	public boolean setUserProfile(UserProfile profile) {
		users.put(profile.getUsername(), profile);
		return true;
	}

	/* Common */
	@Override
	public boolean launchOffer(Service service) {
		webService.launchOffer(service);
		
		return true;
	}
	
	@Override
	public boolean launchOffers(ArrayList<Service> services) {
		webService.launchOffers(services);
		
		return true;
	}

	@Override
	public boolean dropOffer(Service service) {
		webService.dropOffer(service);
		return false;
	}
	
	@Override
	public boolean dropOffers(ArrayList<Service> services) {
		webService.dropOffers(services);
		return false;
	}

	/* Buyer */
	@Override
	public int acceptOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int refuseOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* Seller */
	@Override
	public int makeOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dropAction() {
		// TODO Auto-generated method stub
		return 0;
	}
}
