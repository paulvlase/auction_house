package webServiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import config.WebServiceServerConfig;
import data.UserProfile;
import data.UserProfile.UserRole;

/**
 * WebServiceServer module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceServerMockup implements Runnable {
	private ServerSocket	serverSocket;

	private ConcurrentHashMap<String, UserProfile>	users;	
	
	private static ExecutorService pool = Executors.newCachedThreadPool();

	public WebServiceServerMockup() {
		users = new ConcurrentHashMap<String, UserProfile>();

		users.put("pvlase", new UserProfile("pvlase", "Paul", "Vlase",
				UserRole.BUYER, "parola"));
		users.put("unix140", new UserProfile("unix140", "Ghennadi",
				"Procopciuc", UserRole.SELLER, "marmota"));
	}
	
	public void putUser(UserProfile user) {
		users.put(user.getUsername(), user);
	}

	public UserProfile getUser(String username) {
		return users.get(username);
	}

	public void removeUser(String username) {
		users.remove(username);
	}

	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(WebServiceServerConfig.PORT);
		} catch (IOException e) {
			System.err.println("Nu pot asculta pe portul: " + WebServiceServerConfig.PORT + ".");
			return;
		}

		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				
				pool.execute(new WebServiceWorkerMockup(this, clientSocket));
			} catch (IOException e) {
				System.err.println("EROARE: Conectare client");
			}
		}
	}

	public static void main(String args[]) {
		WebServiceServerMockup server = new WebServiceServerMockup();

		(new Thread(server)).start();
	}
}
