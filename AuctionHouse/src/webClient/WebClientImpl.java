package webClient;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import webServer.messages.ErrorResponse;
import webServer.messages.GetProfileResponse;
import webServer.messages.LoadOffersRequest;
import webServer.messages.LoadOffersResponse;
import webServer.messages.LoginRequest;
import webServer.messages.LoginResponse;
import webServer.messages.LogoutRequest;
import webServer.messages.GetProfileRequest;
import webServer.messages.OkResponse;
import webServer.messages.RegisterProfileRequest;
import webServer.messages.SetProfileRequest;

import data.LoginCred;
import data.Pair;
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

	private WebClientEvents	thread;

	public WebClientImpl(MediatorWeb med) {
		//TODO: logger.setLevel(Level.OFF);

		this.med = med;
		med.registerWebClient(this);
	}

	public Pair<LoginCred, UserProfile> logIn(LoginCred cred) {
		logger.debug("Begin");

		Object requestObj = new LoginRequest(cred);
		Object responseObj = Util.askWebServer(requestObj);

		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;
			logger.warn("Failed: " + res.getMsg());
			return null;
		} else if (responseObj instanceof LoginResponse) {
			LoginResponse res = (LoginResponse) responseObj;
			logger.info("Success");
			
			thread = new WebClientEvents(this);
			thread.start();
			
			return new Pair<LoginCred, UserProfile>(res.getCred(), res.getUserProfile());
		} else {
			logger.error("Unexpected response message " + responseObj.getClass());
			return null;
		}
	}

	public void logOut() {
		logger.debug("Begin");

		LogoutRequest requestMsg = new LogoutRequest(med.getLoginCred());

		Object responseObj = Util.askWebServer(requestMsg);

		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;
			logger.warn("Failed: " + res.getMsg());
		} 
		
		thread.stopRunning();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread = null;

		logger.debug("End success");
	}
	
	public ArrayList<Service> loadOffers(LoginCred cred){
		Object responseObj = Util.askWebServer(new LoadOffersRequest(cred));

		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;

			logger.warn("Failed: " + res.getMsg());
			return null;
		} else if (responseObj instanceof LoadOffersResponse) {
			LoadOffersResponse res = (LoadOffersResponse) responseObj;
			
			return res.getServices();
		} else {
			logger.error("Unexpected response message");
			return null;
		}		
	}

	public UserProfile getUserProfile(String username) {
		Object requestObj = new GetProfileRequest(med.getLoginCred(), username);
		Object responseObj = Util.askWebServer(requestObj);
		
		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;

			logger.warn("Failed: " + res.getMsg());
			return null;
		} else if (responseObj instanceof GetProfileResponse) {
			GetProfileResponse res = (GetProfileResponse) responseObj;
			
			return res.getProfile();
		} else {
			logger.error("Unexpected response message");
			return null;
		}
	}

	public boolean setUserProfile(UserProfile profile) {
		Object requestObj = new SetProfileRequest(med.getLoginCred(), profile);
		Object responseObj = Util.askWebServer(requestObj);
		
		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;

			logger.warn("Failed: " + res.getMsg());
			//return false;
		} else if (responseObj instanceof OkResponse) {			
			//return true;
		} else {
			logger.error("Unexpected response message");
			//return false;
		}
		
		med.changeProfileNotify(profile);
		return true;
	}

	public boolean registerUser(UserProfile profile) {
		if (verifyUsername(profile.getUsername()))
			return false;

		Object requestObj = new RegisterProfileRequest(profile);
		Object responseObj = Util.askWebServer(requestObj);

		if (responseObj instanceof ErrorResponse) {
			ErrorResponse res = (ErrorResponse) responseObj;

			logger.warn("Failed: " + res.getMsg());
			//return false;
		} else if (responseObj instanceof OkResponse) {			
			//return true;
		} else {
			logger.error("Unexpected response message");
			//return false;
		}
		
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
