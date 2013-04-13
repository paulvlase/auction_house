package network;

import interfaces.Command;

import java.nio.channels.SelectionKey;
import java.util.List;

import data.Message;
import data.QueueThread;
import data.Service;

/**
 * 
 * @author Ghennadi Procopciuc
 */
public class NetworkSendEvents extends QueueThread<SelectionKey, Message> {

	private NetworkImpl	network;

	public NetworkSendEvents(NetworkImpl network) {
		this.network = network;
	}

	protected void process(List<Command> events) {
	}
	
	public void enqueue(SelectionKey key, Service service) {
		super.enqueue(key, service.asMessages());
	}

	@Override
	protected void process() {
		// TODO
	}
}
