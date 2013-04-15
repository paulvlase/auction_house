package webServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import webServer.messages.DropOfferRequest;
import webServer.messages.GetProfileRequest;
import webServer.messages.GetProfileResponse;
import webServer.messages.LaunchOfferRequest;
import webServer.messages.LaunchOfferResponse;
import webServer.messages.LoginRequest;
import webServer.messages.LoginResponse;
import webServer.messages.LogoutRequest;
import webServer.messages.OkResponse;
import webServer.messages.RegisterProfileRequest;
import webServer.messages.SetProfileRequest;
import config.WebServiceServerConfig;
import data.LoginCred;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.UserProfile.UserRole;

/**
 * WebServiceServer module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServerMockup implements Runnable {
	static Logger logger = Logger.getLogger(WebServerMockup.class);

	private ServerSocket									serverSocket;

	private ConcurrentHashMap<String, UserProfile>			users;
	private ConcurrentHashMap<String, InetSocketAddress>	onlineUsers;
	private ConcurrentHashMap<String, ArrayList<UserEntry>>	sellers;
	private ConcurrentHashMap<String, ArrayList<UserEntry>>	buyers;

	private static ExecutorService							pool	= Executors.newCachedThreadPool();

	public WebServerMockup() {
		// TODO: logger.setLevel(Level.OFF);

		users = new ConcurrentHashMap<String, UserProfile>();
		onlineUsers = new ConcurrentHashMap<String, InetSocketAddress>();

		sellers = new ConcurrentHashMap<String, ArrayList<UserEntry>>();
		buyers = new ConcurrentHashMap<String, ArrayList<UserEntry>>();

		users.put("pvlase", new UserProfile("pvlase", "Paul", "Vlase", UserRole.BUYER, "parola"));
		users.put("unix140", new UserProfile("unix140", "Ghennadi", "Procopciuc", UserRole.SELLER, "marmota"));
		users.put("s1", new UserProfile("s1", "s1", "s1", UserRole.SELLER, "s1"));
	}

	public Object login(LoginRequest req) {
		logger.debug("Begin");
		LoginCred cred = req.getLoginCred();

		UserProfile profile = users.get(cred.getUsername());
		if (profile == null) {
			logger.warn("Username not found");
			return null;
		}

		if (!profile.getPassword().equals(cred.getPassword())) {
			logger.warn("Wrong username or password");
			return null;
		}
		
		if (onlineUsers.get(cred.getUsername()) != null) {
			logger.warn("Already logged");
			return null;
		}

		profile.setRole(cred.getRole());

		onlineUsers.put(cred.getUsername(), cred.getAddress());
		
		logger.info("End success");
		return new LoginResponse(profile);
	}

	public Object logout(LogoutRequest requestMsg) {
		onlineUsers.remove(requestMsg.getCred().getUsername());
		return new OkResponse();
	}

	public Object launchOffer(LaunchOfferRequest req) {
		logger.debug("Begin");

		Service service = req.getService();
		ArrayList<UserEntry> userEntries;

		UserEntry userEntry = new UserEntry();
		userEntry.setUsername(req.getUsername());
		UserProfile profile = users.get(req.getUsername());
		userEntry.setName(profile.getFirstName() + " " + profile.getLastName());
		userEntry.setAddress(onlineUsers.get(req.getUsername()));
	
		if (req.getUserRole() == UserRole.BUYER) {
			sellers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
			userEntries = sellers.get(service.getName());

			buyers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());		
			ArrayList<UserEntry> buyersUserEntries = buyers.get(service.getName());
			buyersUserEntries.add(userEntry);

			buyers.put(service.getName(), buyersUserEntries);
		} else {
			userEntry.setTime(service.getTime());
			buyers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
			userEntries = buyers.get(service.getName());

			sellers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
			ArrayList<UserEntry> sellersUserEntries = sellers.get(service.getName());
			sellersUserEntries.add(userEntry);

			sellers.put(service.getName(), sellersUserEntries);
		}

		service.setUsers(userEntries);

		logger.debug("End");
		return new LaunchOfferResponse(service);
	}

	public Object dropOffer(DropOfferRequest req) {
		logger.debug("Begin");

		UserEntry userEntry = new UserEntry();
		userEntry.setName(req.getUsername());
		userEntry.setAddress(onlineUsers.get(req.getUsername()));

		if (req.getUserRole() == UserRole.BUYER) {
			ArrayList<UserEntry> buyersUserEntries = buyers.get(req.getServiceName());
			buyersUserEntries.remove(userEntry);

			buyers.put(req.getServiceName(), buyersUserEntries);
		} else {
			ArrayList<UserEntry> sellersUserEntries = sellers.get(req.getServiceName());
			sellersUserEntries.remove(userEntry);

			sellers.put(req.getServiceName(), sellersUserEntries);
		}

		logger.debug("End");
		return new OkResponse();
	}

	public Object getProfile(GetProfileRequest req) {
		UserProfile profile = users.get(req.getUsername());
		return new GetProfileResponse(profile);
	}

	public Object setProfile(SetProfileRequest req) {
		UserProfile profile = req.getUserProfile();

		users.put(profile.getUsername(), profile);
		return new OkResponse();
	}
	
	public Object registerProfile(RegisterProfileRequest req) {
		UserProfile profile = req.getUserProfile();

		users.put(profile.getUsername(), profile);
		return new OkResponse();
	}

	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(WebServiceServerConfig.PORT);
		} catch (IOException e) {
			// TODO: translate this
			logger.fatal("Nu pot asculta pe portul: " + WebServiceServerConfig.PORT + ".");
			return;
		}

		while (true) {
			try {

				logger.debug("Before accept");
				Socket clientSocket = serverSocket.accept();
				logger.debug("Connection accepted");

				pool.execute(new WebWorkerMockup(this, clientSocket));
			} catch (IOException e) {
				// TODO: translate this
				logger.error("Conectare client");
			}
		}
	}

	public static void main(String args[]) {
		BasicConfigurator.configure();
		WebServerMockup server = new WebServerMockup();

		(new Thread(server)).start();
	}
}
