package webServiceServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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

	@Override
	public void run() {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		
		try {
			dis = new DataInputStream(clientSocket.getInputStream());
			dos = new DataOutputStream(clientSocket.getOutputStream());

			String request = dis.readUTF();
			System.out.println("request: " + request);
			
			dos.writeUTF("OK");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null)
					dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (dis != null)
					dis.close();
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
