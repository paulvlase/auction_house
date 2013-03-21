package mediator;

import data.LoginCred;
import interfaces.Gui;
import interfaces.MediatorGui;
import interfaces.MediatorNetwork;
import interfaces.MediatorWeb;
import interfaces.Network;
import interfaces.WebServiceClient;

/**
 * Mediator module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class MockupMediator implements MediatorGui, MediatorNetwork, MediatorWeb {
	private Gui					gui;
	private Network				net;
	private WebServiceClient	web;

	private String				name;

	public MockupMediator() {

	}

	@Override
	public void registerGui(Gui gui) {
		this.gui = gui;
	}

	@Override
	public void registerNetwork(Network net) {
		this.net = net;
	}

	@Override
	public void registerWebServiceClient(WebServiceClient web) {
		this.web = web;
	}
	
	@Override
	public void login() {
		gui.login();
	}
	
	@Override
	public boolean signIn(LoginCred cred) {
		return web.signIn(cred);
	}
	
	@Override
	public void signOut() {
		System.out.println("Signed out");
		return web.signOut();
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	/* Common */
	@Override
	public int addOffer(String service) {
		return web.addOffer(service);
	}
	
	@Override
	public int removeOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int launchOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dropOffer(String service) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/* Buyer */
	@Override
	public int acceptOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int refuseOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* Seller */
	@Override
	public int makeOffer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dropAction() {
		// TODO Auto-generated method stub
		return 0;
	}
}
