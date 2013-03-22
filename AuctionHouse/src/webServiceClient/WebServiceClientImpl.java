package webServiceClient;

import data.LoginCred;
import data.UserProfile;
import data.UserProfile.UserRole;
import interfaces.MediatorWeb;
import interfaces.WebServiceClient;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientImpl implements WebServiceClient {
	private MediatorWeb med;
	private WebServiceMockup webService;
	
	public WebServiceClientImpl(MediatorWeb med) {
		this.med = med;
		
		med.registerWebServiceClient(this);
	}
	
	@Override
	public UserProfile logIn(LoginCred cred) {
		UserProfile profile = null;

		System.out.println("[WebServiceClientImpl : logIn] " +
				cred.getUsername() + " " + cred.getPassword());
		if (cred.getUsername().equals("pvlase") &&
				cred.getPassword().equals("parola"))
			profile =  getUserProfile("pvlase");
		if (cred.getUsername().equals("unix140") &&
				cred.getPassword().equals("marmota"))
			profile = getUserProfile("unix140");
		
		if (profile != null) {
			WebServiceMockup webService = new WebServiceMockup(med);
			webService.start();
		}
		return profile;
	}
	
	@Override
	public void logOut() {
		System.out.println("[WebServiceClient:logOut()] Bye bye");
		
		/* This will be deleted.
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
		if (username.equals("pvlase")) {
			return new UserProfile("pvlase","Paul Vlase", UserRole.BUYER, null);
		}
		if (username.equals("unix140")) {
			return new UserProfile("unix140","Ghennadi Procopciuc", UserRole.SELLER, null);
		}
		return null;
	}
	
	@Override
	public boolean setUserProfile(UserProfile profile) {
		return false;
	}

	/* Common */
	@Override
	public int addOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int launchOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dropOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
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
