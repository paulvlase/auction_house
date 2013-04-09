package network;

import data.Service;
import interfaces.Command;
import interfaces.MediatorNetwork;

public class NetworkAdapter implements Command {
	private Service service;

	public NetworkAdapter(Service service) {
		this.service = service;
	}
	
	public void execute() {
		service.executeNet();
	}
}
