package webServiceClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import config.WebServiceClientConfig;

import webServiceServer.messages.ErrorMessage;
import webServiceServer.messages.LoginRequestMessage;
import webServiceServer.messages.LoginResponseMessage;

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

	public WebServiceClientMockup(MediatorWeb med) {
		this.med = med;

		med.registerWebServiceClient(this);
	}
	
	private Object askWebServer(Object requestObj) {
		System.out.println("[WebServiceClientMockup:askWebServer()] Begin");
		Object responseObj = null;
		
		Socket socket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		try {
			System.out.println("[WebServiceClientMockup:askWebServer()] Try block");
			socket = new Socket(WebServiceClientConfig.IP, WebServiceClientConfig.PORT);

			System.out.println("[WebServiceClientMockup:askWebServer()] Before getting streams");
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("[WebServiceClientMockup:askWebServer()] After getting streams");
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("[WebServiceClientMockup:askWebServer()] Got Input stream");

			System.out.println("Trimit");

			oos.writeObject(requestObj);
			oos.flush();

			responseObj = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return responseObj;
	}

	public UserProfile logIn(LoginCred cred) {
		System.out.println("[WebServiceClientMockup:logIn()] Begin");
		
		LoginRequestMessage requestMsg = new LoginRequestMessage(cred);
		System.out.println("[WebServiceClientMockup:logIn()] Before asking");
		Object responseObj = askWebServer(requestMsg);

		if (responseObj instanceof LoginResponseMessage) {
			System.out.println("[WebServiceClientMockup:logIn()] Success");
			return ((LoginResponseMessage) responseObj).getProfile();
		} else if (responseObj instanceof ErrorMessage) {
			System.out.println("[WebServiceClientMockup:logIn()] Exit");
			return null;
		} else {
			System.out.println("[WebServiceClientMockup:logIn()] Unexpected response message");
			return null;
		}
		
	}

	public void logOut() {
		System.out.println("[WebServiceClientMockup:logOut()] Begin");

		try {
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
	}
	
	public void publishServices(ArrayList<Service> services) {
	}
}
