package network;

import interfaces.MediatorNetwork;
import interfaces.Network;
import interfaces.NetworkTransfer;

import java.util.Iterator;
import java.util.List;

import javax.swing.SwingWorker;

import data.Service;
import data.Service.Status;

public class NetworkTask extends SwingWorker<Service, Service> {
	private MediatorNetwork med;
	private Service service;
	
	private final int DELAY = 100;
	private final int COUNT = 100;

	public NetworkTask(MediatorNetwork med, Service service) {
		this.med = med;
		this.service = service;
		
		service.setStatus(Status.TRANSFER_IN_PROGRESS);
	}

	@Override
	protected Service doInBackground() throws Exception {
		System.out.println(Thread.currentThread());
		
		int i = 0;
		try {
			while (i < COUNT) {
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
			med.transferProgressNotify(service);
			med.transferProgressNotify(service);
		}
	}

	@Override
	protected void done() {
		System.out.println(Thread.currentThread());
		if (isCancelled()) {
			System.out.println("Cancelled !");
			
			service.setProgress(-1);
			service.setStatus(Status.TRANSFER_FAILED);
			med.transferProgressNotify(service);
		} else {
			System.out.println("Done !");
			
			service.setProgress(COUNT + 1);
			service.setStatus(Status.TRANSFER_COMPLETE);
			med.transferProgressNotify(service);
		}
	}
}