package network;

import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import data.Service;
import data.Service.Status;
import interfaces.MediatorNetwork;
import interfaces.NetworkMediator;
import interfaces.NetworkTransfer;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkImpl implements NetworkMediator, NetworkTransfer {
	private MediatorNetwork med;
	private NetworkJoinThread joinThread;

	private NetworkEvents eventsTask;

	private Hashtable<String, NetworkTransferTask> tasks;
	private ConcurrentHashMap<String, ServerSocketChannel> userSocketMap;
	private ConcurrentHashMap<ServerSocketChannel, ArrayList<Message>> socketMessageMap;

	public NetworkImpl(MediatorNetwork med) {
		this.med = med;

		med.registerNetwork(this);

		joinThread = new NetworkJoinThread(med);
		joinThread.start();

		tasks = new Hashtable<String, NetworkTransferTask>();

		userSocketMap = new ConcurrentHashMap<String, ServerSocketChannel>();
		socketMessageMap = new ConcurrentHashMap<ServerSocketChannel, ArrayList<Message>>();
	}

	public ConcurrentHashMap<String, ServerSocketChannel> getUserSocketMap() {
		return userSocketMap;
	}

	public void setUserSocketMap(
			ConcurrentHashMap<String, ServerSocketChannel> userSocketMap) {
		this.userSocketMap = userSocketMap;
	}

	public ConcurrentHashMap<ServerSocketChannel, ArrayList<Message>> getSocketMessageMap() {
		return socketMessageMap;
	}

	public void setSocketMessageMap(
			ConcurrentHashMap<ServerSocketChannel, ArrayList<Message>> socketMessageMap) {
		this.socketMessageMap = socketMessageMap;
	}

	@Override
	public boolean startTransfer(Service service) {
		System.out.println("startTransfer");
		service.setStatus(Status.TRANSFER_STARTED);
		med.changeServiceNotify(service);

		NetworkTransferTask task = new NetworkTransferTask(med, joinThread,
				service);
		task.execute();

		tasks.put(service.getName(), task);

		return true;
	}

	@Override
	public void stopTransfer(Service service) {
		joinThread.stopTask(service);
	}

	public void logIn() {
		eventsTask = new NetworkEvents(med);
		eventsTask.execute();
	}

	public void logOut() {
		try {
			eventsTask.cancel(false);
			eventsTask = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void publishService(Service service) {
		eventsTask.publishService(service);

	}

	@Override
	public void publishServices(ArrayList<Service> services) {
		eventsTask.publishServices(services);
	}
}
