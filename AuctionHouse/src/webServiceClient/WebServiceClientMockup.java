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
		Object responseObj = null;
		
		Socket socket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		try {
			socket = new Socket(WebServiceClientConfig.IP, WebServiceClientConfig.PORT);

			ois = new ObjectInputStream(socket.getInputStream());
			ois.close();

			System.out.println("Trimit");
			
			oos.writeObject(requestObj);
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			
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
		Object responseObj = askWebServer(requestMsg);

		if (responseObj instanceof LoginResponseMessage) {
			return ((LoginResponseMessage) responseObj).getProfile();
		} else if (responseObj instanceof ErrorMessage) {
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
