package interfaces;

import java.util.ArrayList;

import data.LoginCred;
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
	
	public void newUserNotify(Service service);
	public void dropRequestNotify(Service service);

}
