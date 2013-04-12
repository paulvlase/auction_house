package webServiceClient;


import java.util.ArrayList;

import webServiceServer.messages.ErrorMessage;
import webServiceServer.messages.LoginRequestMessage;
import webServiceServer.messages.LoginResponseMessage;
import webServiceServer.messages.LogoutRequestMessage;
import webServiceServer.messages.LogoutResponseMessage;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import interfaces.MediatorWeb;
import interfaces.WebServiceClient;

/**
 * WebServiceClient mockup module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientMockup extends Thread implements WebServiceClient {
	private MediatorWeb						med;
	
	private WebServiceClientEvents			thread;

	public WebServiceClientMockup(MediatorWeb med) {
		this.med = med;

		med.registerWebServiceClient(this);
		
		thread = new WebServiceClientEvents(this);
		thread.start();
	}

	public UserProfile logIn(LoginCred cred) {
		System.out.println("[WebServiceClientMockup:logIn()] Begin");
		
		LoginRequestMessage requestMsg = new LoginRequestMessage(cred);

		Object responseObj = Util.askWebServer(requestMsg);

		if (responseObj instanceof LoginResponseMessage) {
			System.out.println("[WebServiceClientMockup:logIn()] Success");
			return ((LoginResponseMessage) responseObj).getProfile();
		} else if (responseObj instanceof ErrorMessage) {
			System.out.println("[WebServiceClientMockup:logIn()] " + (ErrorMessage) responseObj);
			return null;
		} else {
			System.out.println("[WebServiceClientMockup:logIn()] Unexpected response message");
			return null;
		}
	}

	public void logOut() {
		System.out.println("[WebServiceClientMockup:logOut()] Begin");
		UserProfile profile = med.getUserProfile();
		LogoutRequestMessage requestMsg = new LogoutRequestMessage(profile.getUsername());

		Object responseObj = Util.askWebServer(requestMsg);

		if (responseObj instanceof LoginResponseMessage) {
			System.out.println("[WebServiceClientMockup:logOut()] Success");
		} else if (responseObj instanceof ErrorMessage) {
			System.out.println("[WebServiceClientMockup:logOut()] " + (ErrorMessage) responseObj);
		} else {
			System.out.println("[WebServiceClientMockup:logOut()] Unexpected response message");
		}
	}

	public UserProfile getUserProfile(String username) {
		// TODO
		return med.getUser(username);
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
	
	public void changeServiceNotify(Service service) {
		med.changeServiceNotify(service);
	}

	public void changeServicesNotify(ArrayList<Service> services) {
		med.changeServicesNotify(services);
	}
}
