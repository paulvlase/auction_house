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
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import data.Message;
import data.Message.MessageType;
import data.Service;
import data.Service.Status;
import data.UserEntry;
import data.UserProfile;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkImpl implements NetworkMediator, NetworkTransfer, NetworkService {
	private static Logger									logger	= Logger.getLogger(NetworkImpl.class);

	private MediatorNetwork									mediator;

	private NetworkReceiveEvents							receiveEvents;
	private NetworkSendEvents								sendEvents;

	private ConcurrentHashMap<String, SocketChannel>		userChanelMap;
	private ConcurrentHashMap<String, ArrayList<Message>>	userUnsentMessages;

	private NetworkDriver									driver;

	public NetworkImpl(MediatorNetwork med) {
		// TODO: logger.setLevel(Level.OFF);

		this.mediator = med;

		med.registerNetwork(this);

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

	public MediatorNetwork getMediator() {
		return mediator;
	}

	public void setMediator(MediatorNetwork mediator) {
		this.mediator = mediator;
	}

	public void changeServiceNotify(Service service) {
		mediator.changeServiceNotify(service);
	}

	public void logIn() {
		logger.debug("Begin");
		receiveEvents = new NetworkReceiveEvents(this);
		sendEvents = new NetworkSendEvents(this.getDriver());

		driver.startRunning();
		receiveEvents.start();
		sendEvents.start();

		logger.debug("End");
	}

	/**
	 * Stop receiving or sending messages
	 */
	public void logOut() {
		logger.debug("Begin");

		UserProfile userProfile = getUserProfile();

		logger.debug("Clear all messages");
		/* Clear queue of send messages */
		userUnsentMessages.clear();
		sendEvents.clear();
		receiveEvents.clear();

		logger.debug("Create and send REMOVE messages");
		for (Service service : mediator.getOffers().values()) {
			if (service.getUsers() == null) {
				continue;
			}

			for (UserEntry user : service.getUsers()) {
				Message message = new Message();

				message.setType(data.Message.MessageType.REMOVE);
				message.setServiceName(service.getName());
				message.setUsername(new String(user.getUsername()));
				message.setPayload(userProfile.getUsername());
				message.setDestination(user.getUsername());
				message.setSource(userProfile.getUsername());

				sendEvents.enqueue(userChanelMap.get(user.getUsername()), message);
			}
		}

		for (Map.Entry<String, SocketChannel> entry : userChanelMap.entrySet()) {
			Message message = new Message();
			message.setType(MessageType.LOGOUT);
			message.setDestination(entry.getKey());
			message.setSource(userProfile.getUsername());

			sendEvents.enqueue(entry.getValue(), message);
		}

		logger.debug("Wait until messages will be sent");
		/* Busy waiting until all message are unsent, ugly solution but it works */
		while (sendEvents.haveToProcess())
			;
		while (driver.haveToProcess())
			;

		for (Map.Entry<String, SocketChannel> entry : userChanelMap.entrySet()) {
			logger.debug("Remove dependencies for : " + entry.getKey());
			removeAllDependencies(entry.getKey());
		}

		logger.debug("All messages was been sent");

		try {
			receiveEvents.stopRunning();
			sendEvents.stopRunning();
			driver.stopRunning();

			// receiveEvents = null;
			// sendEvents = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Clear all informations */
		receiveEvents.clear();
		sendEvents.clear();
		userChanelMap.clear();
		userUnsentMessages.clear();
		driver = new NetworkDriver(this);

		logger.debug("End");
	}

	@Override
	public void publishService(Service service) {
		logger.debug("Begin");
		logger.debug("Service : " + service);

		service.executeNet(this);

		ArrayList<Message> messages = service.asMessages(this);
		logger.debug("Messages : " + messages);

		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);
			driver.sendData(message, message.getDestination(), service.getUsers().get(i).getAddress());
		}

		logger.debug("End");
	}

	public void removeAllDependencies(String username) {
		SocketChannel chanel = userChanelMap.get(username);
		SelectionKey key = chanel.keyFor(driver.getSelector());

		// Receive
		receiveEvents.removeKey(key);
		// Send
		sendEvents.removeKey(chanel);
		// Driver
		driver.removeAllDependencies(chanel);
		// Unsent messages
		userUnsentMessages.remove(username);
		// User chanel
		userChanelMap.remove(username);
	}

	public SocketChannel getSocketChannel(String username) {
		return userChanelMap.get(username);
	}

	public SelectionKey getSelectionKey(String username) {
		return userChanelMap.get(username).keyFor(driver.getSelector());
	}

	@Override
	public void publishServices(ArrayList<Service> services) {
		// TODO : Process send messages
	}
}
