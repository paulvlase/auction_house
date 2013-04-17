package interfaces;

import java.net.InetSocketAddress;
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
	
	public void  publishService(Service service);
	public void  publishServices(ArrayList<Service> services);
	
	public InetSocketAddress getAddress();
}
