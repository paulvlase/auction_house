package webClient;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import webServer.messages.ErrorResponse;
import webServer.messages.GetProfileResponse;
import webServer.messages.LoginRequest;
import webServer.messages.LoginResponse;
import webServer.messages.LogoutRequest;
import webServer.messages.GetProfileRequest;
import webServer.messages.RegisterProfileRequest;
import webServer.messages.SetProfileRequest;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import data.UserProfile.UserRole;
import interfaces.MediatorWeb;
import interfaces.WebClient;
import interfaces.WebService;

/**
 * WebClient mockup module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebClientImpl implements WebClient, WebService {
	static Logger logger = Logger.getLogger(WebClientImpl.class);

	private MediatorWeb		med;
	private LoginCred       cred;

	private WebClientEvents	thread;

	public WebClientImpl(MediatorWeb med) {
		//TODO: logger.setLevel(Level.OFF);

		this.med = med;

		med.registerWebClient(this);
	}

	public UserProfile logIn(LoginCred cred) {
		logger.debug("Begin");

		Object requestObj = new LoginRequest(cred);
		Object responseObj = Util.askWebServer(requestObj);

		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;
			logger.warn("Failed: " + res.getMsg());
			return null;
		} else if (responseObj instanceof LoginResponse) {
			logger.info("Success");
			
			this.cred = cred;
			thread = new WebClientEvents(this);
			thread.start();
			return ((LoginResponse) responseObj).getUserProfile();
		} else {
			logger.error("Unexpected response message");
			return null;
		}
	}

	public void logOut() {
		logger.debug("Begin");

		LogoutRequest requestMsg = new LogoutRequest(cred);

		Object responseObj = Util.askWebServer(requestMsg);

		thread.stopRunning();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread = null;

		logger.debug("End success");
	}

	public UserProfile getUserProfile(String username) {
		Object requestObj = new GetProfileRequest(med.getLoginCred(), username);
		Object responseObj = Util.askWebServer(requestObj);
		
		return ((GetProfileResponse) responseObj).getProfile();
	}

	public boolean setUserProfile(UserProfile profile) {
		Object requestObj = new SetProfileRequest(med.getLoginCred(), profile);
		Object responseObj = Util.askWebServer(requestObj);
		med.changeProfileNotify(profile);
		
		return true;
	}

	public boolean registerUser(UserProfile profile) {
		if (verifyUsername(profile.getUsername()))
			return false;

		Object requestObj = new RegisterProfileRequest(profile);
		Object responseObj = Util.askWebServer(requestObj);

		return true;
	}

	public boolean verifyUsername(String username) {
		if (getUserProfile(username) != null)
			return true;
		return false;
	}

	public void publishService(Service service) {
		thread.publishService(service);
	}
	
	@Override
	public void publishServices(ArrayList<Service> services) {
		thread.publishServices(services);
	}
	
	public void notifyNetwork(Service service) {
		med.notifyNetwork(service);
	}
	
	@Override
	public String getUsername() {
		return med.getUserProfile().getUsername();
	}
	
	@Override
	public UserRole getUserRole() {
		return med.getUserProfile().getRole();
	}

	@Override
	public LoginCred getLoginCred() {
		return med.getLoginCred();
	}
}
