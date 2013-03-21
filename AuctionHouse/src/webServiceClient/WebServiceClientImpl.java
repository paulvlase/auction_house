package webServiceClient;

import data.LoginCred;
import interfaces.MediatorWeb;
import interfaces.WebServiceClient;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientImpl implements WebServiceClient {
	private MediatorWeb med = null;
	
	public WebServiceClientImpl(MediatorWeb med) {
		this.med = med;
		
		med.registerWebServiceClient(this);
	}
	
	@Override
	public boolean logIn(LoginCred cred) {
		System.out.println("[WebServiceClientImpl : logIn] " +
				cred.getUsername() + " " + cred.getPassword());
		if (cred.getUsername().equals("pvlase") &&
				cred.getPassword().equals("parola"))
			return true;
		if (cred.getUsername().equals("unix140") &&
				cred.getPassword().equals("marmota"))
			return true;
		
		return false;
	}
	
	@Override
	public void logOut() {
		System.out.println("[WebServiceClient:logOut()] Bye bye");
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
