package webServiceClient;

import interfaces.MediatorWeb;

import java.util.ArrayList;
import java.util.Random;

import data.Service;

public class WebServiceMockup extends Thread {
	private boolean running;
	private Random random;

	private MediatorWeb med;
	private ArrayList<Service> offers;

	
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
				System.out.println(sleepTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stopThread() {
		running = false;
	}
	
	public synchronized boolean addOffers(ArrayList<Service> services) {
		offers.addAll(services);
		
		return true;
	}
	public synchronized boolean addOffer(Service service) {
		offers.add(service);
		return true;
	}
}
