package network;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;

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
public class NetworkMockup implements NetworkMediator, NetworkTransfer {
	private MediatorNetwork		med;
	private NetworkJoinThread	joinThread;

	private NetworkEvents		eventsTask;
	
	private Hashtable<String, NetworkTransferTask> tasks;

	public NetworkMockup(MediatorNetwork med) {
		this.med = med;

		med.registerNetwork(this);

		joinThread = new NetworkJoinThread(med);
		joinThread.start();
		
		tasks = new Hashtable<String, NetworkTransferTask>();
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

	@Override
	public InetSocketAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}
}
