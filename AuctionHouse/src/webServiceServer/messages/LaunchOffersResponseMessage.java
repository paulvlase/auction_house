package webServiceServer.messages;

import java.net.InetAddress;
import java.util.ArrayList;

public class LaunchOffersResponseMessage {
	private ArrayList<InetAddress> addresses;

	public LaunchOffersResponseMessage(ArrayList<InetAddress> addresses) {
		this.addresses = addresses;
	}
	
	public ArrayList<InetAddress> getAddresses() {
		return addresses;
	}
}
