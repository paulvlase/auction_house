package interfaces;

import java.util.ArrayList;

import mediator.Servie;

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

	/* Common */
	public ArrayList<Service> loadOffers();

	public boolean launchOffer(Service service);

	public boolean launchOffers(ArrayList<Service> service);

	public boolean dropOffer(Service service);

	public boolean dropOffers(ArrayList<Service> services);

	/* Buyer */
	public void acceptOffer(Pair<Service, Integer> pair);

	public int refuseOffer(Pair<Service, Integer> pair);

	/* Seller */
	public int makeOffer();

	public int dropAction();

	boolean startTransfer(Pair<Service, Integer> pair);
}
