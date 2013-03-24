package interfaces;

import java.util.ArrayList;

import data.LoginCred;
import data.Pair;
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
	
	public boolean verifyUsername(String username);

	
	/* Common */
	public boolean launchOffer(Service service);
	
	public boolean launchOffers(ArrayList<Service> service);
	
	public boolean dropOffer(Service service);
	
	public boolean dropOffers(ArrayList<Service> service);
	
	
	/* Buyer */
	public boolean acceptOffer(Pair<Service, Integer> pair);
	
	public boolean refuseOffer(Pair<Service, Integer> pair);
	
	
	/* Seller */
	public boolean makeOffer(Pair<Service, Integer> pair);
	
	public boolean dropAuction(Pair<Service, Integer> pair);
}
