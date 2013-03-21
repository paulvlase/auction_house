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
	
	public void addService(Service service);
	public void addServices(ArrayList<Service> services);
	public void removeService(Service service);
	public void removeServices(ArrayList<Service> services);
	public ArrayList<Service> loadOffers();
}
