package webClient;

import java.util.ArrayList;

import webServer.messages.LogoutRequest;
import webServer.messages.ProfileRequest;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import interfaces.MediatorWeb;
import interfaces.WebClient;
import interfaces.WebService;

/**
 * WebClient mockup module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebClientMockup extends Thread implements WebClient, WebService {
	private MediatorWeb		med;
	private LoginCred       cred;

	private WebClientEvents	thread;

	public WebClientMockup(MediatorWeb med) {
		this.med = med;

		med.registerWebClient(this);

		thread = new WebClientEvents(this);
		thread.start();
	}

	public UserProfile logIn(LoginCred cred) {
		System.out.println("[WebServiceClientMockup:logIn()] Begin");

		Object responseObj = Util.askWebServer(cred);

		if (responseObj == null) {
			System.out
			.println("[WebServiceClientMockup:logIn()] Error");
			return null;
		}
		if (responseObj instanceof UserProfile) {
			System.out.println("[WebServiceClientMockup:logIn()] Success");
			
			this.cred = cred;
			//return ((UserProfile) responseObj).getProfile();
			return ((UserProfile) responseObj);
		} else {
			System.out
					.println("[WebServiceClientMockup:logIn()] Unexpected response message");
			return null;
		}
	}

	public void logOut() {
		System.out.println("[WebServiceClientMockup:logOut()] Begin");
		LogoutRequest requestMsg = new LogoutRequest(cred);

		Object responseObj = Util.askWebServer(requestMsg);

		System.out
					.println("[WebServiceClientMockup:logOut()] Success");
	}

	public UserProfile getUserProfile(String username) {
		// TODO
		Object responseObj = Util.askWebServer(new ProfileRequest(username));
		
		return (UserProfile) responseObj;
	}

	public boolean setUserProfile(UserProfile profile) {
		// TODO
		med.putUser(profile);
		med.changeProfileNotify(profile);
		return true;
	}

	public boolean registerUser(UserProfile profile) {
		// TODO
		if (med.getUser(profile.getUsername()) != null)
			return false;

		med.putUser(profile);
		return true;
	}

	public boolean verifyUsername(String username) {
		// TODO
		if (med.getUser(username) != null)
			return true;
		return false;
	}

	public void publishService(Service service) {
		thread.publishService(service);
	}

	public void publishServices(ArrayList<Service> services) {
		thread.publishServices(services);
	}
	
	public void notifyNetwork(Service service) {
		
	}
}
