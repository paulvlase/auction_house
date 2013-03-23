package interfaces;

import java.util.ArrayList;

import data.LoginCred;
import data.Service;
import data.UserProfile;

/**
 * WebServiceClient interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface WebServiceClient {
	public UserProfile logIn(LoginCred cred);
	public void logOut();
	
	public UserProfile getUserProfile(String username);
	public boolean setUserProfile(UserProfile profile);
	
	/* Common */
	public boolean launchOffer(Service service);
	public boolean launchOffers(ArrayList<Service> service);
	
	public boolean dropOffer(Service service);
	public boolean dropOffers(ArrayList<Service> service);
	
	/* Buyer */
	public int acceptOffer();
	public int refuseOffer();
	
	/* Seller */
	public int makeOffer();
	public int dropAction();
}
