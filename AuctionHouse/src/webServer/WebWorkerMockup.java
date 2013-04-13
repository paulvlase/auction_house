package webServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import webServer.messages.DropOfferRequest;
import webServer.messages.LaunchOfferRequest;
import webServer.messages.LaunchOfferResponse;
import webServer.messages.LogoutRequest;

import data.LoginCred;
import data.Service;
import data.UserProfile;
import data.UserProfile.UserRole;

/**
 * WebServiceWorker mockup implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebWorkerMockup implements Runnable {
	public WebServerMockup	webServer;
	public Socket			clientSocket;

	public WebWorkerMockup(WebServerMockup webServer, Socket clientSocket) {
		this.webServer = webServer;
		this.clientSocket = clientSocket;
	}

	private void log(String str) {
		System.out.println("[" + clientSocket.getInetAddress() + ":"
				+ clientSocket.getPort() + "] " + str);
	}

	private Object login(LoginCred cred) {
		UserProfile profile = webServer.getUser(cred.getUsername());
		if (profile == null) {
			return null;
		}

		if (!profile.getPassword().equals(cred.getPassword())) {
			return null;
		}

		profile.setRole(cred.getRole());

		webServer.putOnlineUser(cred.getUsername(), cred.getAddress());
		return profile;
	}

	private Object logout(LogoutRequest requestMsg) {
		webServer.removeOnlineUser(requestMsg.getCred().getUsername());
		return null;
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		System.out.println("[WebServiceWorkerMockup:run()] Started");
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());

			Object requestObj = ois.readObject();
			Object responseObj;
			System.out.println("New conn");

			if (requestObj instanceof LoginCred) {
				log("Login message");
				responseObj = login((LoginCred) requestObj);
			} else if (requestObj instanceof LogoutRequest) {
				log("Logout message");
				responseObj = logout((LogoutRequest) requestObj);
			} else if (requestObj instanceof LaunchOfferRequest) {
				log("Launch offer message");
				responseObj = webServer.launchOffer((LaunchOfferRequest) requestObj);
			} else if (requestObj instanceof DropOfferRequest) {
				log("Drop offermessage");
				responseObj = webServer.dropOffer((DropOfferRequest) requestObj);
			} else {
				log("Unknow command");
				responseObj = null;
			}

			oos.writeObject(responseObj);
			oos.flush();
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
