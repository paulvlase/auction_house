package interfaces;

import java.util.ArrayList;

import data.LoginCred;
import data.Pair;
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
	
	public boolean registerUser(UserProfile profile);
	
	public boolean verifyUsername(String username);

	/* Common */
	public ArrayList<Service> loadOffers();

	public boolean launchOffer(Service service);

	public boolean launchOffers(ArrayList<Service> service);

	public boolean dropOffer(Service service);

	public boolean dropOffers(ArrayList<Service> services);

	/* Buyer */
	public void acceptOffer(Pair<Service, Integer> pair);

	public void refuseOffer(Pair<Service, Integer> pair);

	/* Seller */
	public boolean makeOffer(Pair<Service, Integer> pair, Double price);
	
	public boolean dropAuction(Pair<Service, Integer> pair);
}
