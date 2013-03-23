package webServiceClient;

import interfaces.MediatorWeb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import data.Service;

public class WebServiceMockup extends Thread {
	private boolean running;
	private Random random;

	private MediatorWeb med;
	private Hashtable<String, Service> offers;
	
	public WebServiceMockup(MediatorWeb med) {
		this.med = med;
		
		random = new Random();
	}
	
	public void run() {
		int timeLimit = 2500;
		running = true;
		
		try {
			while (running) {
				int sleepTime = 100 + random.nextInt(timeLimit);
				
				Thread.sleep(sleepTime);
				//System.out.println(sleepTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stopThread() {
		running = false;
	}
	
	public synchronized boolean launchOffer(Service service) {
		offers.put(service.getName(), service);
		System.out.println("[WebServiceMockup:addOffer] " + service.getName());

		return true;
	}
	
	public synchronized boolean launchOffers(ArrayList<Service> services) {
		for (Service service: services) {
			offers.put(service.getName(), service);
			System.out.println("[WebServiceMockup:addOffers] " + service.getName());
		}
		
		return true;
	}
	
	public synchronized boolean dropOffer(Service service) {
		offers.remove(service.getName());
		System.out.println("[WebServiceMockup:dropOffer] " + service.getName());
		return true;
	}
	
	public synchronized boolean dropOffers(ArrayList<Service> services) {
		for (Service service: services) {
			offers.remove(service.getName());
			System.out.println("[WebServiceMockup:addOffers] " + service.getName());
		}
		
		return true;
	}
}
