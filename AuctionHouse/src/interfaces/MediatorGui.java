package interfaces;

import data.LoginCred;

/**
 * Mediator interface for Gui module.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface MediatorGui {
	public void registerGui(Gui gui);

	public void login();
	public boolean signIn(LoginCred cred);
	public void signOut();
	public String getName();
	
	/* Common */
	public int addOffer(String service);
	public int removeOffer(String service);
	
	public int launchOffer(String service);
	public int dropOffer(String service);
	
	/* Buyer */
	public int acceptOffer();
	public int refuseOffer();
	
	/* Seller */
	public int makeOffer();
	public int dropAction();
}
