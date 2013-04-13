package interfaces;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import data.Service;
import data.UserProfile;

/**
 * Mediator interface for WebServiceClient module.
 *  
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorWeb {
	public void registerWebClient(WebClient web);
	
	public UserProfile getUserProfile();

	/* Doar pentru mockup. */
	public void putOffer(Service service);
	public Service getOffer(String serviceName);
	public Hashtable<String, Service> getOffers();
	public void removeOffer(String serviceName);
	
	/* Doar pentru mockup. */
	public void putUser(UserProfile user);
	public UserProfile getUser(String username);
	public Hashtable<String, UserProfile> getUsers();
	public void removeUser(String username);

	public void changeServiceNotify(Service service);
	public void changeServicesNotify(ArrayList<Service> service);
	public void changeProfileNotify(UserProfile profile);
	public void notifyNetwork(Service service);

	public void stopTransfer(Service service);
	public InetSocketAddress getNetworkAddress();
}