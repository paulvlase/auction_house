package interfaces;

import java.util.ArrayList;

import data.LoginCred;
import data.Service;
import data.UserProfile;

/**
 * Mediator interface for Gui module.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorGui {
	public void registerGui(Gui gui);

	public void start();
	public boolean logIn(LoginCred cred);
	public void logOut();
	public UserProfile getUserProfile();
	public boolean setUserProfile(UserProfile profile);

	/* Common */
	public ArrayList<Service> loadOffers();
	
	public boolean launchOffer(Service service);
	public boolean launchOffers(ArrayList<Service> service);
	public boolean dropOffer(Service service);
	public boolean dropOffers(ArrayList<Service> services);
	
	/* Buyer */
	public void acceptOffer(Service service);
	public int refuseOffer(Service service);
	
	/* Seller */
	public int makeOffer();
	public int dropAction();
}
