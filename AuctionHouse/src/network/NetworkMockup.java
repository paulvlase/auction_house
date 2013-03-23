package network;

import data.Service;
import interfaces.MediatorNetwork;
import interfaces.Network;
import interfaces.NetworkTransfer;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkMockup implements Network, NetworkTransfer {
	private MediatorNetwork med = null;
	
	public NetworkMockup(MediatorNetwork med) {
		this.med = med;
		
		med.registerNetwork(this);
	}
	
	@Override
	public boolean startTransfer(Service service) {
		NetworkTask task = new NetworkTask(this, service);
		task.execute();
		
		return true;
	}
	
	@Override
	public void transferProgress(Service service) {
		System.out.println("[NetworkImpl:transferProgress()] " + service.getProgress());
	}
}
