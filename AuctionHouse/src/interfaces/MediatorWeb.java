package interfaces;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import data.LoginCred;
import data.Service;
import data.UserProfile;

/**
 * Mediator interface for WebServiceClient module.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorWeb {
	public void registerWebClient(WebClient web);

	public LoginCred getLoginCred();

	public UserProfile getUserProfile();

	/* Doar pentru mockup. */
	public void putOffer(Service service);

	public Service getOffer(String serviceName);

	public ConcurrentHashMap<String, Service> getOffers();

	public void removeOffer(String serviceName);

	public void changeServiceNotify(Service service);

	public void changeProfileNotify(UserProfile profile);

	public void notifyNetwork(Service service);

	public InetSocketAddress getNetworkAddress();
}