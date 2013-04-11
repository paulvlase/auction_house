package webServiceServer;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.LoginCred;
import data.UserProfile;

import webServiceServer.messages.ErrorMessage;
import webServiceServer.messages.LoginRequestMessage;
import webServiceServer.messages.LoginResponseMessage;
import webServiceServer.messages.LogoutRequestMessage;
import webServiceServer.messages.LogoutResponseMessage;

/**
 * WebServiceWorker mockup implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceWorkerMockup implements Runnable {
	public WebServiceServerMockup webServer;
	public Socket clientSocket;

	public WebServiceWorkerMockup(WebServiceServerMockup webServer, Socket clientSocket) {
		this.webServer = webServer;
		this.clientSocket = clientSocket;
	}
	
	private void log(String str) {
		System.out.println("[" + clientSocket.getInetAddress() + "]" + str );
	}
	
	private Object login(LoginRequestMessage msg) {
		LoginCred cred = msg.getCred();

		UserProfile profile = webServer.getUser(cred.getUsername());
		if (profile == null) {
			return new ErrorMessage("Wrong username or password");
		}

		if (!profile.getPassword().equals(cred.getPassword())) {
			return new ErrorMessage("Wrong username or password");
		}

		profile.setRole(cred.getRole());
		return new LoginResponseMessage(profile);
	}
	
	private LogoutResponseMessage logout(LogoutRequestMessage msg) {
		return null;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());


			Object requestObj = ois.readObject();
			ois.close();
			Object responseObj;
			System.out.println("New conn");
			
			if (requestObj instanceof LoginRequestMessage) {
				log("Login message");
				responseObj = login((LoginRequestMessage) requestObj);
			} else if (requestObj instanceof LogoutResponseMessage) {
				log("Logout message");
				responseObj = logout((LogoutRequestMessage) requestObj);
			} else {
				log("Unknow command");
				responseObj = null;
			}
			
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(responseObj);
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
				if (clientSocket != null)
					clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
