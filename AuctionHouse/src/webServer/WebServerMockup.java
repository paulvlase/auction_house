package webServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
public class WebServerMockup implements Runnable {
	private ServerSocket	serverSocket;

	private ConcurrentHashMap<String, UserProfile>	users;
	private ConcurrentHashMap<String, InetSocketAddress> onlineUsers;
	private ConcurrentHashMap<String, ArrayList<InetSocketAddress>> seller;
	private ConcurrentHashMap<String, ArrayList<InetSocketAddress>> buyers;
	
	private static ExecutorService pool = Executors.newCachedThreadPool();

	public WebServerMockup() {
		users = new ConcurrentHashMap<String, UserProfile>();
		onlineUsers = new ConcurrentHashMap<String, InetSocketAddress>();
		
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
	
	public void putOnlineUser(UserProfile user) {
		onlineUsers.put(user.getUsername(), user);
	}

	public UserProfile getOnlineUser(String username) {
		return onlineUsers.get(username);
	}

	public void removeOnlineUser(String username) {
		onlineUsers.remove(username);
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

				System.out.println("[WebServiceServerMockup:askWebServer()] Before accept");
				Socket clientSocket = serverSocket.accept();
				System.out.println("[WebServiceServerMockup:askWebServer()] Connection accepted");
				
				pool.execute(new WebWorkerMockup(this, clientSocket));
			} catch (IOException e) {
				System.err.println("EROARE: Conectare client");
			}
		}
	}

	public static void main(String args[]) {
		WebServerMockup server = new WebServerMockup();

		(new Thread(server)).start();
	}
}
