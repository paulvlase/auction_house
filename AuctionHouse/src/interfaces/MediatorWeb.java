package interfaces;

import java.util.ArrayList;

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
	
	public void changeServiceNotify(Service service);
	public void changeServicesNotify(ArrayList<Service> services);
	public void changeProfileNotify(UserProfile profile);
}
