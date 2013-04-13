package network;

import interfaces.Command;

import java.nio.channels.SelectionKey;
import java.util.List;

import data.QueueThread;
import data.Service;

/**
 * 
 * @author Ghennadi Procopciuc
 */
public class NetworkSendEvents extends QueueThread<SelectionKey, Service> {

	private NetworkImpl	network;

	public NetworkSendEvents(NetworkImpl network) {
		this.network = network;
	}

	protected void process(List<Command> events) {
	}

	@Override
	protected void process() {
		// TODO
	}
}
