package interfaces;

/**
 * WebServiceClient interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface WebServiceClient {
	public boolean signIn(String username, String password);
	
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
