package network;

import data.Service;
import data.Service.Status;
import interfaces.MediatorNetwork;
import interfaces.Network;
import interfaces.NetworkTransfer;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkMockup implements Network, NetworkTransfer {
	private MediatorNetwork med;
	private NetworkJoinThread joinThread;

	public NetworkMockup(MediatorNetwork med) {
		this.med = med;
		
		med.registerNetwork(this);
		
		joinThread = new NetworkJoinThread(med);
		joinThread.start();
	}
	
	@Override
	public boolean startTransfer(Service service) {
		service.setStatus(Status.TRANSFER_STARTED);
		med.changeServiceNotify(service);

		NetworkTask task = new NetworkTask(med, joinThread, service);
		task.execute();
		
		return true;
	}
}
