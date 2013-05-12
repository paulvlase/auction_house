package network;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import data.Message;
import data.Service;
import interfaces.Command;
import interfaces.NetworkService;

public class NetworkAdapter implements Command {
	private static Logger	logger	= Logger.getLogger(NetworkAdapter.class);

	private Service			service;
	private NetworkService	net;

	public NetworkAdapter(Service service, NetworkService net) {
		this.service = service;
		this.net = net;
	}

	public void execute() {
		logger.debug("Begin");
		service.executeNet(net);
		logger.debug("End");
	}

	public ArrayList<Message> asMessages() {
		logger.debug("Begin");
		logger.debug("End");
		return service.asMessages(net);
	}
}
