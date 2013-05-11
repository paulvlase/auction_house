package webServer.messages;

import java.io.Serializable;
import java.util.ArrayList;

import data.Service;

public class LoadOffersResponse implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private ArrayList<Service> services;

	public LoadOffersResponse(ArrayList<Service> services) {
		super();
		this.services = services;
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}
}
