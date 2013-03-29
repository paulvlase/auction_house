package interfaces;

import java.util.ArrayList;
import java.util.Hashtable;

import data.Service;

/**
 * Network interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface NetworkMediator {
	public void logIn();
	public void logOut();
	
	public boolean startTransfer(Service service);
	public void stopTransfer(Service service);
	
	public void  publishService(Service service);
	public void  publishServices(ArrayList<Service> services);
}
