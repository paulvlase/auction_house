package interfaces;

import java.util.ArrayList;

import data.LoginCred;
import data.Service;
import data.UserProfile;

/**
 * WebServiceClient interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface WebClient {
	public UserProfile logIn(LoginCred cred);
	public void logOut();
	
	public UserProfile getUserProfile(String username);
	public boolean setUserProfile(UserProfile profile);
	public boolean registerUser(UserProfile profile);
	public boolean verifyUsername(String username);
	
	public void publishService(Service service);
	public void publishServices(ArrayList<Service> services);
	public ArrayList<Service> loadOffers(LoginCred cred);
}
