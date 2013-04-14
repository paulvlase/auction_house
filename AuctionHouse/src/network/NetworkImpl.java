package network;

import interfaces.MediatorNetwork;
import interfaces.NetworkMediator;
import interfaces.NetworkService;
import interfaces.NetworkTransfer;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import data.Message;
import data.Service;
import data.Service.Status;
import data.UserProfile;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkImpl implements NetworkMediator, NetworkTransfer, NetworkService {
	private MediatorNetwork									mediator;
	private NetworkJoinThread								joinThread;

	private NetworkReceiveEvents							receiveEvents;
	private NetworkSendEvents								sendEvents;

	private Hashtable<String, NetworkTransferTask>			tasks;

	private ConcurrentHashMap<String, SocketChannel>			userChanelMap;
	private ConcurrentHashMap<String, ArrayList<Message>>	userUnsentMessages;

	private NetworkDriver											driver;

	public NetworkImpl(MediatorNetwork med) {
		this.mediator = med;

		med.registerNetwork(this);

		joinThread = new NetworkJoinThread(med);
		joinThread.start();

		tasks = new Hashtable<String, NetworkTransferTask>();

		userChanelMap = new ConcurrentHashMap<String, SocketChannel>();
		userUnsentMessages = new ConcurrentHashMap<String, ArrayList<Message>>();

		driver = new NetworkDriver(this);
	}

	public InetSocketAddress getAddress() {
		return driver.getAddress();
	}

	public NetworkReceiveEvents getEventsTask() {
		return receiveEvents;
	}

	public NetworkDriver getDriver() {
		return driver;
	}

	public void setDriver(NetworkDriver driver) {
		this.driver = driver;
	}

	@Override
	public boolean startTransfer(Service service) {
		System.out.println("startTransfer");
		service.setStatus(Status.TRANSFER_STARTED);
		mediator.changeServiceNotify(service);

		NetworkTransferTask task = new NetworkTransferTask(mediator, joinThread, service);
		task.execute();

		tasks.put(service.getName(), task);

		return true;
	}

	public UserProfile getUserProfile() {
		return mediator.getUserProfile();
	}

	public void registerConnection(String username, SocketChannel chanel) {
		userChanelMap.put(username, chanel);
	}

	public Service getService(String serviceName) {
		return mediator.getOffer(serviceName);
	}

	public ConcurrentHashMap<String, ArrayList<Message>> getUserUnsentMessages() {
		return userUnsentMessages;
	}

	public void setUserUnsentMessages(ConcurrentHashMap<String, ArrayList<Message>> userUnsentMessages) {
		this.userUnsentMessages = userUnsentMessages;
	}

	public NetworkReceiveEvents getReceiveEvents() {
		return receiveEvents;
	}

	public void setReceiveEvents(NetworkReceiveEvents receiveEvents) {
		this.receiveEvents = receiveEvents;
	}

	public NetworkSendEvents getSendEvents() {
		return sendEvents;
	}

	public void setSendEvents(NetworkSendEvents sendEvents) {
		this.sendEvents = sendEvents;
	}

	public ConcurrentHashMap<String, SocketChannel> getUserChanelMap() {
		return userChanelMap;
	}

	public void setUserChanelMap(ConcurrentHashMap<String, SocketChannel> userChanelMap) {
		this.userChanelMap = userChanelMap;
	}

	public void changeServiceNotify(Service service) {
		mediator.changeServiceNotify(service);
	}

	@Override
	public void stopTransfer(Service service) {
		joinThread.stopTask(service);
	}

	public void logIn() {
		System.out.println("[NetworkImpl: logIn] Begin");
		receiveEvents = new NetworkReceiveEvents(this);
		sendEvents = new NetworkSendEvents(this);

		driver.startRunning();
		receiveEvents.start();
		sendEvents.start();
		
		System.out.println("[NetworkImpl: logIn] End");
	}

	/**
	 * Stop receiving or sending messages
	 */
	public void logOut() {
		System.out.println("[NetworkImpl: logOut] Begin");
		try {
			driver.stopRunning();
			receiveEvents.stopRunning();
			sendEvents.stopRunning();
			
			driver = new NetworkDriver(this);
			receiveEvents = null;
			sendEvents = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("[NetworkImpl: logOut] End");
	}

	@Override
	public void publishService(Service service) {
		System.out.println("[NetworkImpl, publishService] Service : " + service);
		ArrayList<Message> messages = service.asMessages();
		System.out.println("[NetworkImpl, publishService] Messages : " + messages);

		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);
			driver.sendData(message, message.getDestination(), service.getUsers().get(i).getAddress());
		}
	}

	@Override
	public void publishServices(ArrayList<Service> services) {
		// TODO : Process send messages
	}
}
