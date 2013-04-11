package webServiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import config.WebServiceServerConfig;

/**
 * WebServiceServer module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceServerMockup implements Runnable {
	private int				port;
	private ServerSocket	serverSocket;
	
	public static ExecutorService pool = Executors.newCachedThreadPool();

	public WebServiceServerMockup() {
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
