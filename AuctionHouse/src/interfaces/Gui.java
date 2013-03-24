package interfaces;

import java.util.ArrayList;
import java.util.List;

import data.LoginCred;
import data.Pair;
import data.Service;
import data.UserProfile;

/**
 * Gui interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface Gui {
	public void start();
	public void logIn(LoginCred cred);
	public void logOut();
	public UserProfile getUserProfile();
	public boolean setUserProfile(UserProfile profile);
	
	public ArrayList<Service> loadOffers();
	
	public boolean launchOffer(Service service);
	public boolean launchOffers(ArrayList<Service> services);
	public boolean dropOffer(Service service);
	public boolean dropOffers(ArrayList<Service> services);

	public void acceptOffer(Pair<Service, Integer> pair);
	public void refuseOffer(Pair<Service, Integer> pair);

	public void changeServiceNotify(Service service);
	public void changeServicesNotify(List<Service> services);
	public void changeProfileNotify(UserProfile profile);
}
