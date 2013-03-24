package interfaces;

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
	public void registerWebServiceClient(WebServiceClient web);
	
	public UserProfile getUserProfile();
	
	public void putOffer(Service service);
	public Service getOffer(String serviceName);
	public Hashtable<String, Service> getOffers();
	public void removeOffer(String serviceName);
	
	public void changeServiceNotify(Service service);
	public void changeServicesNotify(ArrayList<Service> services);
	public void changeProfileNotify(UserProfile profile);
}
