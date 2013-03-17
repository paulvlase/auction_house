package mediator;

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
public class MediatorImpl implements MediatorGui, MediatorNetwork, MediatorWeb {
	private Gui					gui;
	private Network				net;
	private WebServiceClient	web;

	private String				name;

	public MediatorImpl() {

	}

	public void registerGui(Gui gui) {
		this.gui = gui;
	}

	public void registerNetwork(Network net) {
		this.net = net;
	}

	public void registerWebServiceClient(WebServiceClient web) {
		this.web = web;
	}
	
	public boolean signIn(String username, String password) {
		return web.signIn(username, password);
	}

	public String getName() {
		return this.name;
	}
}
