package interfaces;

import data.Service;

/**
 * Mediator interface for WebServiceClient module.
 *  
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorWeb {
	public void registerWebServiceClient(WebServiceClient web);
	
	public void newUserNotify(Service service);
	public void dropRequestNotify(Service service);
}
