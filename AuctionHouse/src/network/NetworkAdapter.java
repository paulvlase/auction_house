package network;

import data.Service;
import interfaces.Command;
import interfaces.NetworkService;

public class NetworkAdapter implements Command {
	private Service service;
	private NetworkService net;

	public NetworkAdapter(Service service, NetworkService net) {
		this.service = service;
		this.net = net;
	}
	
	public void execute() {
		service.executeNet(net);
	}
}
