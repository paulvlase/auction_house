package webServiceClient;

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
	public boolean signIn(String username, String password) {
		System.out.println("[WebServiceClientImpl : signIn] " + username + " " + password);
		if (username.equals("pvlase") && password.equals("parola"))
			return true;
		if (username.equals("unix140") && password.equals("marmota"))
			return true;
		
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
