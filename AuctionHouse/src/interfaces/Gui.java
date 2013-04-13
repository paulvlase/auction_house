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

	public void registerUserStep1();
	public boolean registerUser(UserProfile profile);
	public void registerUserStep3();
	public boolean verifyUsername(String username);
	
	public ArrayList<Service> loadOffers();
	
	public void publishService(Service service);

	public void changeServiceNotify(Service service);	
	public void changeProfileNotify(UserProfile profile);
	
	public Service createService(String name, Long time, Double price);
}
