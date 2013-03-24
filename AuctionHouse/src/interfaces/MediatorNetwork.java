package interfaces;

import java.util.List;

import data.Service;

/**
 * Mediator interface for Network module.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorNetwork {
	public void registerNetwork(Network net);

	public void changeServiceNotify(Service service);
}
