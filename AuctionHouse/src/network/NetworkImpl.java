package network;

import interfaces.MediatorNetwork;
import interfaces.Network;

/**
 * Network module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class NetworkImpl implements Network {
	private MediatorNetwork med = null;
	
	public NetworkImpl(MediatorNetwork med) {
		this.med = med;
		
		med.registerNetwork(this);
	}
}
