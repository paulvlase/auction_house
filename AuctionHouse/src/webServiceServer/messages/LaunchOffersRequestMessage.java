package webServiceServer.messages;

import java.util.ArrayList;

import data.Service;

public class LaunchOffersRequestMessage {
	private ArrayList<String> services;
	
	public LaunchOffersRequestMessage(ArrayList<String> services) {
		this.services = services;
	}
	
	public ArrayList<String> getServices() {
		return services;
	}
}
