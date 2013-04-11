package webServiceServer;

import java.io.IOException;
import java.net.Socket;

/**
 * WebServiceServer module implementation.
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println("Connection closed");
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
