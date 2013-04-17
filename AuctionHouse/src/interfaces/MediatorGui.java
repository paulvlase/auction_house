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

	public void    start();
	public boolean logIn(LoginCred cred);
	public void    logOut();

	
	public UserProfile getUserProfile();
	public boolean setUserProfile(UserProfile profile);
	public boolean registerUser(UserProfile profile);
	public boolean verifyUsername(String username);

	/* Common */
	public ArrayList<Service> loadOffers();
	public void launchOffers(ArrayList<Service> services);

	public void publishService(Service service);
	public void publishServices(ArrayList<Service> services);
}
