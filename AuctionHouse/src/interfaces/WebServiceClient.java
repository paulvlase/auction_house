package interfaces;

import data.LoginCred;
import data.UserProfile;

/**
 * WebServiceClient interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface WebServiceClient {
	public boolean logIn(LoginCred cred);
	public void logOut();
	
	public UserProfile getUserProfile();
	public boolean setUserProfile(UserProfile profile);
	
	/* Common */
	public int addOffer(String service);
	public int removeOffer(String service);
	
	public int launchOffer(String service);
	public int dropOffer(String service);
	
	/* Buyer */
	public int acceptOffer();
	public int refuseOffer();
	
	/* Seller */
	public int makeOffer();
	public int dropAction();
}
