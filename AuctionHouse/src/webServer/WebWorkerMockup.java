package webServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import webServer.messages.DropOfferRequest;
import webServer.messages.GetProfileRequest;
import webServer.messages.LaunchOfferRequest;
import webServer.messages.LoginRequest;
import webServer.messages.LogoutRequest;
import webServer.messages.RegisterProfileRequest;
import webServer.messages.SetProfileRequest;

/**
 * WebServiceWorker mockup implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebWorkerMockup implements Runnable {
	static Logger logger = Logger.getLogger(WebWorkerMockup.class);

	public WebServerMockup	webServer;
	public Socket			clientSocket;

	public WebWorkerMockup(WebServerMockup webServer, Socket clientSocket) {
		// TODO: logger.setLevel(Level.OFF);

		this.webServer = webServer;
		this.clientSocket = clientSocket;
	}

	private String getWorkerName(String str) {
		return clientSocket.getInetAddress() + ":" + clientSocket.getPort();
	}

	@Override
	public void run() {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		logger.info("Begin");
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());

			Object requestObj = ois.readObject();
			Object responseObj;

			if (requestObj instanceof LoginRequest) {
				logger.debug("Login request");
				responseObj = webServer.login((LoginRequest) requestObj);
			} else if (requestObj instanceof LogoutRequest) {
				logger.debug("Logout request");
				responseObj = webServer.logout((LogoutRequest) requestObj);
			} else if (requestObj instanceof LaunchOfferRequest) {
				logger.debug("Launch offer request");
				responseObj = webServer.launchOffer((LaunchOfferRequest) requestObj);
			} else if (requestObj instanceof DropOfferRequest) {
				logger.debug("Drop offer request");
				responseObj = webServer.dropOffer((DropOfferRequest) requestObj);
			}else if (requestObj instanceof GetProfileRequest) {
				logger.debug("Get profile offer request");
				responseObj = webServer.getProfile((GetProfileRequest) requestObj);
			} else if (requestObj instanceof SetProfileRequest) {
				logger.debug("Set profile offer request");
				responseObj = webServer.setProfile((SetProfileRequest) requestObj);
			} else if (requestObj instanceof RegisterProfileRequest) {
				logger.debug("Register profile offer request");
				responseObj = webServer.registerProfile((RegisterProfileRequest) requestObj);
			} else {
				logger.error("Unknow request " + requestObj);
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
