package network;

import interfaces.Command;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;

import data.Message;
import data.QueueThread;
import data.Service;

/**
 * 
 * @author Ghennadi Procopciuc
 */
public class NetworkSendEvents extends QueueThread<SocketChannel, Message> {

	private NetworkDriver driver;

	public NetworkSendEvents(NetworkDriver driver) {
		super("NetworkSendEvents");
		this.driver = driver;
	}

	protected synchronized void process(List<Command> events) {
	}
	
//	public void enqueue(SocketChannel chanel, Service service) {
//		super.enqueue(chanel, service.asMessages(driver.));
//	}

	@Override
	protected synchronized void process() {
		Map.Entry<SocketChannel, Message> job = getJob();
		if(job == null){
			return;
		}
		
		System.out.println("[NetworkSendEvents: process] Send message : " + job.getValue());
		driver.sendData(job.getValue(), job.getKey());
	}
}
