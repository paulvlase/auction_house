package network;

import interfaces.MediatorNetwork;
import interfaces.Network;
import interfaces.NetworkTransfer;

import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import data.Service;

public class NetworkTask extends SwingWorker<Service, Service> {
	private NetworkTransfer net;
	private Service service;

	public NetworkTask(NetworkTransfer net, Service service) {
		this.net = net;
		this.service = service;
	}

	@Override
	protected Service doInBackground() throws Exception {
		System.out.println(Thread.currentThread());
		
		int DELAY = 1000;
		int count = 10;
		int i = 0;
		try {
			while (i < count) {
				i++;
				Thread.sleep(DELAY);
				service.setProgress(i);
				
				publish(service);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return service;
	}

	protected void process(List<Service> services) {
		System.out.println(Thread.currentThread());

		for (Service service:  services) {
			net.transferProgress(service);
		}
	}

	@Override
	protected void done() {
		System.out.println(Thread.currentThread());
		if (isCancelled())
			System.out.println("Cancelled !");
		else
			System.out.println("Done !");
	}
}