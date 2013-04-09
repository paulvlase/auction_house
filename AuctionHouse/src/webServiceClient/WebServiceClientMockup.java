package webServiceClient;

import java.util.ArrayList;
import java.util.Hashtable;

import network.NetworkAdapter;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import data.UserProfile.UserRole;
import interfaces.MediatorWeb;
import interfaces.WebServiceClient;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientMockup extends Thread implements WebServiceClient {
	private MediatorWeb						med;
	private WebServiceClientEvents			task;

	public WebServiceClientMockup(MediatorWeb med) {
		this.med = med;

		med.registerWebServiceClient(this);
	}

	public UserProfile logIn(LoginCred cred) {
		System.out.println("[WebServiceClientMockup:logIn()] Begin");

		UserProfile profile = getUserProfile(cred.getUsername());
		if (profile == null) {
			return null;
		}

		if (!profile.getPassword().equals(cred.getPassword())) {
			return null;
		}

		profile.setRole(cred.getRole());

		if (profile != null) {
			task = new WebServiceClientEvents(med);
			task.execute();
		}

		System.out.println("[WebServiceClientMockup:logIn()] End");
		return profile;
	}

	public void logOut() {
		System.out.println("[WebServiceClientMockup:logOut()] Begin");

		try {
			task.cancel(false);
			task = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[WebServiceClientMockup:logOut()] End");
	}

	public UserProfile getUserProfile(String username) {
		return med.getUser(username);
	}

	public boolean setUserProfile(UserProfile profile) {
		med.putUser(profile);
		med.changeProfileNotify(profile);
		return true;
	}
	
	public boolean registerUser(UserProfile profile) {
		if (med.getUser(profile.getUsername()) != null)
			return false;
	
		med.putUser(profile);
		return true;
	}
	
	public boolean verifyUsername(String username) {
		if (med.getUser(username) != null)
			return true;
		return false;
	}
	
	public void publishService(Service service) {
		task.publishService(service);
	}
	
	public void publishServices(ArrayList<Service> services) {
		task.publishServices(services);
	}
}
