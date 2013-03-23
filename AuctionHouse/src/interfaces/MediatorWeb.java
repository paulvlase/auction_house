package interfaces;

import java.util.ArrayList;

import data.Service;

/**
 * Mediator interface for WebServiceClient module.
 *  
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorWeb {
	public void registerWebServiceClient(WebServiceClient web);
	

	public void launchOfferNotify(Service service);
	public void launchOffersNotify(ArrayList<Service> services);
	public void dropOfferNotify(Service service);

	public void newUserNotify(Service service);
}
