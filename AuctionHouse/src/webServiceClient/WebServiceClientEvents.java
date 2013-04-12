package webServiceClient;

import interfaces.Command;
import interfaces.MediatorWeb;

import java.util.ArrayList;

import data.Service;

/**
 * WebServiceClient module implementation.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public class WebServiceClientEvents extends Thread {
	private WebServiceClientMockup	web;
	private Object					monitor;

	private ArrayList<Command>		events;

	public WebServiceClientEvents(WebServiceClientMockup web) {
		this.web = web;

		events = new ArrayList<Command>();
		monitor = new Object();
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (monitor) {
					monitor.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process();
		}
	}

	private void process() {
		System.out.println("[WebServiceClientEvents:process()] Begin");

		for (Command event : events) {
			event.execute();
		}
		
		System.out.println("[WebServiceClientEvents:process()] Begin");

	}

	public synchronized void publishService(Service service) {
		System.out.println("[WebServiceClientEvents:publishService()] Begin");

		events.add(new WebAdapter(service));

		synchronized (monitor) {
			monitor.notify();
		}
		
		System.out.println("[WebServiceClientEvents:publishService()] End");
	}

	public synchronized void publishServices(ArrayList<Service> services) {
		System.out.println("[WebServiceClientEvents:publishServices()] Begin");
		for (Service service : services) {
			events.add(new WebAdapter(service));
		}

		synchronized (monitor) {
			monitor.notify();
		}
		
		System.out.println("[WebServiceClientEvents:publishServices()] End");
	}
}