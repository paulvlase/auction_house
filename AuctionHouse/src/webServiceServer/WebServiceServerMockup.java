package webServiceServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServiceServer module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceServerMockup implements Runnable {
	private int				port;
	private ServerSocket	serverSocket;
	
	public static ExecutorService pool = Executors.newCachedThreadPool();

	public WebServiceServerMockup(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println("Nu pot asculta pe portul: " + this.port + ".");
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
		if (args.length != 1) {
			System.err.println("Usage: java WebServiceServerMockup port");
			return;
		}

		int port = Integer.parseInt(args[0]);

		WebServiceServerMockup server = new WebServiceServerMockup(port);

		(new Thread(server)).start();
	}
}
