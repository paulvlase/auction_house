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
	
	public boolean signIn(String username, String password) {
		System.out.println("[WebServiceClientImpl : signIn]" + username + " " + password);
		if (username.equals("pvlase") && password.equals("parola"))
			return true;
		
		return false;
	}
}
