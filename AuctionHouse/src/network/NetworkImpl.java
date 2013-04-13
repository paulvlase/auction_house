package network;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import data.Service;
import data.Service.Status;
import data.UserProfile;
import interfaces.MediatorNetwork;
import interfaces.NetworkMediator;
import interfaces.NetworkService;
import interfaces.NetworkTransfer;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkImpl implements NetworkMediator, NetworkTransfer, NetworkService {
	private MediatorNetwork mediator;
	private NetworkJoinThread joinThread;

	private NetworkReceiveEvents receiveEvents;
	private NetworkSendEvents sendEvents;

	private Hashtable<String, NetworkTransferTask> tasks;
	
	private ConcurrentHashMap<String, SelectionKey> userKeyMap;

	private Server driver;

	public NetworkImpl(MediatorNetwork med) {
		this.mediator = med;

		med.registerNetwork(this);

		joinThread = new NetworkJoinThread(med);
		joinThread.start();

		tasks = new Hashtable<String, NetworkTransferTask>();

		userKeyMap = new ConcurrentHashMap<String, SelectionKey>();

		driver = new Server(this);
	}
	
	public InetSocketAddress getAddress() {
		return driver.getAddress();
	}

	public ConcurrentHashMap<String, SelectionKey> getUserKeyMap() {
		return userKeyMap;
	}

	public void setUserKeyMap(ConcurrentHashMap<String, SelectionKey> userKeyMap) {
		this.userKeyMap = userKeyMap;
	}

	public NetworkReceiveEvents getEventsTask() {
		return receiveEvents;
	}

	public Server getDriver() {
		return driver;
	}

	public void setDriver(Server driver) {
		this.driver = driver;
	}

	@Override
	public boolean startTransfer(Service service) {
		System.out.println("startTransfer");
		service.setStatus(Status.TRANSFER_STARTED);
		mediator.changeServiceNotify(service);

		NetworkTransferTask task = new NetworkTransferTask(mediator, joinThread,
				service);
		task.execute();

		tasks.put(service.getName(), task);

		return true;
	}
	
	public UserProfile getUserProfile(){
		return mediator.getUserProfile();
	}
	
	public void registerConnection(String username, SelectionKey key){
		userKeyMap.put(username, key);
	}
	
	public Service getService(String serviceName){
		return mediator.getOffer(serviceName);
	}
	
	public void changeServiceNotify(Service service){
		mediator.changeServiceNotify(service);
	}

	@Override
	public void stopTransfer(Service service) {
		joinThread.stopTask(service);
	}

	public void logIn() {
		receiveEvents = new NetworkReceiveEvents(this);
		sendEvents = new NetworkSendEvents(this);
		
		receiveEvents.start();
		sendEvents.start();
	}

	/**
	 * Stop receiving or sending messages
	 */
	public void logOut() {
		try {
			receiveEvents.stopRunning();
			sendEvents.stopRunning();
			sendEvents = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publishService(Service service) {
		//TODO : Process send messages
	}

	@Override
	public void publishServices(ArrayList<Service> services) {
		//TODO : Process send messages
	}
}
